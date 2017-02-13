/**
 * 用户登录
 * 一个用户对应很多个角色，每个角色对应一个模块，任意个营业部门，任意个权限，登录成功后，通过选择模块确定用户拥有的角色，再通过角色查找其营业部门
 */
'use strict';

App.controller('LoginController', ['$scope', 'LoginService', 'dataService', 'util', 'webService', 'popUpService', 'messageService', 'dateFilter', function ($scope, LoginService, dataService, util, webService, popUpService, messageService, dateFilter) {
    /*初始化数据*/
    $scope.user = {};//清空操作员密码输入框
    $scope.memory = false;//是否记住此次选择
    $scope.chooseModule = false;//是否需要选择模块
    $scope.choosePos = false;//是否需要选择营业部门
    /*获取可用操作员*/
    dataService.refreshUserList()
        .then(function (r) {
            $scope.userList = util.objectListToString(r, 'userId');
        });
    /*向上广播用户，模块*/
    function emitThree() {
        $scope.$emit('moduleButtons', LoginService.getModuleLink());
        $scope.$emit('ownModuleList', LoginService.getOwnModuleList());
        $scope.$emit('ownPointOfSaleList', LoginService.getOwnPointOfSaleList());
        $scope.$emit('currentUser', LoginService.getUser());
    }

    /*登录验证*/
    $scope.login = function () {
        var p = {};
        p.condition = 'user_id=' + util.wrapWithBrackets($scope.user.userId) + ' and ' + 'password=' + util.wrapWithBrackets($scope.user.password);
        dataService.refreshUserList(p)
            .then(function (data) {
                if (data.length == 0) {
                    messageService.setMessage({content: '用户名或密码错误！', type: 'error'});
                    popUpService.pop('message');
                } else {
                    var d = data[0];
                    LoginService.setUser(d.userId);
                    LoginService.setUserObj(d);
                    LoginService.setOwnModuleList(d.moduleArray.split(' '));
                    LoginService.setOwnPermissionList(d.permissionArray.split(' '));
                    LoginService.setOwnPointOfSaleList(d.pointOfSaleArray.split(' '));
                    /*如果操作员记住了之前的选择,就不需要重新选择,直接登录*/
                    var memoryInfo = JSON.parse(localStorage.getItem(d.userId));
                    if (memoryInfo != null) {
                        LoginService.setModuleLink(memoryInfo.moduleLink);
                        LoginService.setPointOfSale(memoryInfo.pointOfSale);
                        LoginService.setModule(memoryInfo.module);
                        loginSuccess();
                    } else {
                        $scope.ownModules = LoginService.getOwnModuleList();
                        $scope.ownPointOfSaleList = LoginService.getOwnPointOfSaleList();
                        $scope.chooseModule = true;
                    }
                }
            });

    };
    /*选择对应的模块*/
    $scope.onChooseModule = function (r) {
        /*有两个以上营业部门,进行二次选择*/
        LoginService.setModule(r);
        LoginService.setModuleLink(util.getValueByField(dataService.getAvailableModule(), 'name', r).link);
        if (r == '餐饮' && $scope.ownPointOfSaleList.length > 1) {
            $scope.choosePos = true;
        }
        else {
            if (r == '餐饮') {
                LoginService.setPointOfSale($scope.ownPointOfSaleList[0]);
            }
            if ($scope.memory) {//记录登录信息
                localStorage.setItem(LoginService.getUser(), JSON.stringify({
                    moduleLink: LoginService.getModuleLink(),
                    pointOfSale: LoginService.getPointOfSale(),
                    module: r
                }));
            }
            loginSuccess();
        }
    };
    /*选择对应的营业部门*/
    $scope.onChoosePos = function (r) {
        LoginService.setPointOfSale(r);
        if ($scope.memory) {
            localStorage.setItem(LoginService.getUser(), JSON.stringify({
                moduleLink: LoginService.getModuleLink(),
                pointOfSale: r
            }));
        }
        loginSuccess();
    };
    /*发送注册号密码，酒店信息等*/
    /*function sendRegisterMessage() {
     webService.get('getNumber')
     .then(function (r) {
     var hotel="https://wild-cock-71704.wilddogio.com/"+dataService.getOtherParamMapValue("酒店名称");
     var ref = new Wilddog(hotel);
     var address=dataService.getOtherParamMapValue("酒店地址")
     if(!address){
     address='';
     }
     var phone=dataService.getOtherParamMapValue("酒店电话");
     if(!phone){
     phone='';
     }
     ref.set({
     serial:r.data,
     address:address,
     phone:phone
     });
     })
     }*/
    function loginSuccess() {
        sessionStorage.setItem('user', LoginService.getUser());
        sessionStorage.setItem('module', LoginService.getModule());
        sessionStorage.setItem('moduleLink', LoginService.getModuleLink());
        sessionStorage.setItem('pointOfSale', LoginService.getPointOfSale());
        sessionStorage.setItem('permission', LoginService.getOwnPermissionList().join(' '));
        //sendRegisterMessage();
        /*回写session*/
        webService.post('userSet', [LoginService.getUser(), LoginService.getModule()])
            .then(function (r) {
                var permission = LoginService.getOwnPermissionList();//操作员拥有的权限
                var moduleList = LoginService[LoginService.getModuleLink()];
                for (var i = 0; i < moduleList.length; i++) {
                    var module = moduleList[i];
                    if (permission.indexOf(module.name) > -1) {
                        module.hover = 'hover';
                        webService.redirect(module.href);
                        break;
                    }//判断该操作员是否有该模块主页的权限，有的话就跳转，没有的话依次查找知道最近的权限
                }
                emitThree();
                localStorage.setItem('ip',r.data);
            });
        dataService.refreshTimeNow()
            .then(function (r) {
                /*这里需要验证一下上次夜审做没做*/
                var lastNight = dataService.getOtherParamMapValue('上次夜审');
                var debtDay = dataService.getOtherParamMapValue('账务日期');
                var nightTime = dataService.getOtherParamMapValue('夜审时间');
                var time = dateFilter(r, 'HH:mm:ss');
                var day = dateFilter(r, 'yyyy-MM-dd');
                if (day != debtDay && time > nightTime) {
                    messageService.setMessage({type:'alert',content: '当前服务器时间:' + day + ' ' + time + ' ,上次夜审时间：' + lastNight + ' ,自动夜审似乎没有完成，请核对房费和日期后在账务工具中手动补做'});
                    popUpService.pop('message');
                }
            });
    }
}]);