/**
 * Created by Administrator on 2016-04-24.
 * 主页面控制器，初始化常用数据
 */

App.controller('IndexController', ['$scope', '$location', 'LoginService', 'webService', 'popUpService', 'util', 'dataService', 'host', 'messageService', 'hostJquery','$cacheFactory', function ($scope, $location, LoginService, webService, popUpService, util, dataService, host, messageService, hostJquery,$cacheFactory) {
    /**
     * 加载就执行的部分，包括数据初始化，登录验证
     */
    /*初始化基础模块*/
    /*webService.post('getDataBaseName',{})
     .then(function (r) {
     webService.cloudPost('setDataBaseName',r.data)
     });*/
    /*阻止空格事件冒泡*/
    /*$scope.prevent=function (event) {
        if(event.keyCode==32 || event.keyCode==13) {
            event.preventDefault();
            event.returnValue = false;
        }
    };*/
    /*校验时间*/
    var time;
    var cloudTime;
    webService.post('time')
        .then(function (r) {
            time = r;
            if (cloudTime) {
                parseTime();
            }
        });
    /*webService.cloudPost('time')
        .then(function (r) {
            cloudTime = r;
            if (time) {
                parseTime();
            }
        });*/
    function parseTime() {
        if (Math.abs(time - cloudTime) > 30 * 60 * 1000) {//误差30分钟
            messageService.setMessage({type: 'error', content: '服务器时间与北京时间误差过大，程序无法使用'});
            popUpService.pop('message');
            $scope.timeError = true;
        }
    }

    dataService.initData(['refreshAvailableModuleList', 'refreshOtherParamList','refreshInterfaceDoorList','refreshProtocolList', 'refreshVipList'])
        .then(function () {
            $scope.nightDate=dataService.getOtherParamMapValue("账务日期");
            if(dataService.getAvailableModule().length==0){
                webService.post('getRegisterNumber')
                    .then(function (s) {
                        messageService.setMessage({type:'error',content:'请将注册序列号发给厂商:'+Math.random().toString().substring(2,10)+s.data+Math.random().toString().substring(2,10)});
                        popUpService.pop('message');
                    });
            }
        });
    /*创建webSocket*/
    var stompClient = null;

    function connect() {
        var socket = new SockJS('/hotelBoot');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/beginNight', function (greeting) {
                if (greeting.body == "true") {
                    $("#ysing").show();
                    $(".bgblack").show();
                } else {
                    $("#ysing").hide();
                    $("#ysEnd").show();
                    $(".ysButton").click(function () {
                        $("#ysEnd").hide();
                        $(".bgblack").hide();
                    })
                }
            });
        });
    }

    /*选择主房态样式*/
    $scope.mainStateCss=localStorage.getItem('mainStateCss');
    if(!$scope.mainStateCss){
        $scope.mainStateCss='../css/mainState/mainState.css';
    }

    //connect();//正式运行的时候再使用，平时都先注释掉
    /*判断是否登录了*/
    var userId = sessionStorage.getItem('user');
    var pointOfSale = sessionStorage.getItem('pointOfSale');
    if (!userId || userId == 'undefined') {
        webService.redirect('login');
    } else {
        LoginService.setUser(userId);
        $scope.currentUser = userId;
        LoginService.setModuleLink(sessionStorage.getItem('moduleLink'));
        LoginService.setPointOfSale(sessionStorage.getItem('pointOfSale'));
        LoginService.setModule(sessionStorage.getItem('module'));
        $scope.title=LoginService.getTitle();
        LoginService.setOwnPermissionList(sessionStorage.getItem('permission').split(' '));
        var p = {};
        p.condition = 'user_id=' + util.wrapWithBrackets(userId);
        dataService.refreshUserList(p)
            .then(function (d) {
                LoginService.setUserObj(d[0]);
                LoginService.setOwnModuleList(d[0].moduleArray.split(' '));
                LoginService.setOwnPointOfSaleList(d[0].pointOfSaleArray.split(' '));
                $scope.ownModuleList = LoginService.getOwnModuleList();
                $scope.ownPointOfSaleList = LoginService.getOwnPointOfSaleList();
                $scope.moduleButtons = LoginService[LoginService.getModuleLink()];
                clearHover();
                util.getValueByField($scope.moduleButtons, 'href', $location.path().substring(1)).hover = 'hover';
            });
        webService.post('refreshCheck', sessionStorage.getItem('userLogIndex'));
    }
    /**
     * 与界面交互的部分
     */
    /*联系我们*/
    $scope.contactUs = function () {
        webService.post('contactUs')
            .then(function (r) {
                messageService.setMessage({type: 'alert', content:r.data});
                popUpService.pop('message');
            })
    };
    /*关掉联系我们*/
    $scope.noShowContactUs=function () {
        popUpService.close('message');
    };
    /*点击上方按钮*/
    $scope.toPath = function (r) {
        /*刷新，点亮点中的图标*/
        webService.redirect(r.href);
        angular.forEach($scope.moduleButtons, function (button) {
            button.hover = null;
        });
        r.hover = 'hover';
    };
    /*显示可以选择的模块*/
    $scope.showOwnModuleList = function () {
        $scope.ifSwitchModule = !$scope.ifSwitchModule;
    };
    /*登录后切换模块*/
    $scope.switchModule = function (r) {
        LoginService.setModule(r);
        LoginService.setModuleLink(util.getValueByField(dataService.getAvailableModule(), 'name', r).link);
        LoginService.setPointOfSale(null);
        sessionStorage.removeItem('pointOfSale');
        clearHover();
        util.getValueByField($scope.moduleButtons, 'href', LoginService.getModuleLink()).hover = 'hover';
        $scope.ownPointOfSaleList = LoginService.getOwnPointOfSaleList();
        if (r == '餐饮' && $scope.ownPointOfSaleList.length > 1) {
            $scope.ifSwitchPos = true;
            $(this).addClass("jtli");
        }
        else {
            if (r == '餐饮') {
                LoginService.setPointOfSale($scope.ownPointOfSaleList[0]);
                sessionStorage.setItem('pointOfSale', LoginService.getPointOfSale());
            }
            loginSuccess();
            $scope.ifSwitchModule = false;
            $scope.ifSwitchPos = false;
            $scope.moduleButtons = LoginService[LoginService.getModuleLink()];
        }
    };
    /*选择营业部门*/
    $scope.switchPos = function (r) {
        LoginService.setPointOfSale(r);
        sessionStorage.setItem('pointOfSale', LoginService.getPointOfSale());
        loginSuccess();
        $scope.ifSwitchModule = false;
        $scope.ifSwitchPos = false;
        $scope.moduleButtons = LoginService[LoginService.getModuleLink()];
    };
    /*退出*/
    $scope.logout = function () {
        $scope.moduleButtons = null;
        sessionStorage.clear();
        logOut();
        LoginService.clearLogin();
        webService.redirect('login');
    };
    /*修改密码*/
    $scope.changePassword = function () {
        popUpService.pop('modify');
        angular.element(".ulUser").css("display", "none")
    };

    /*用户*/
    $scope.userChoose = function () {/*刘丹*/
        angular.element(".ulUser").toggle();
    };

    /*用户关闭/刷新浏览器模块*/
    window.onbeforeunload = function () {
        //webService.post('userOut',{user: LoginService.getUser(), module: LoginService.getModule()});
        logOut();
    };
    function logOut() {
        $.ajax({
            url: hostJquery+'/userOut',
            type: 'post',
            data: {user: LoginService.getUser(), module: LoginService.getModule()},
            success: function (data) {
                sessionStorage.setItem('userLogIndex', data);
            },
            async: false
        });
    }

    /**
     * 广播接收
     */
    $scope.$on('moduleButtons', function (event, data) {
        $scope.moduleButtons = LoginService[data];
    });
    $scope.$on('ownModuleList', function (event, data) {
        $scope.ownModuleList = data;
    });
    $scope.$on('currentUser', function (event, data) {
        $scope.currentUser = data;
        $scope.title=LoginService.getTitle();//本来应该再写一个$on的，但其实那样是不对的
    });
    /**
     * 公用方法,所有子页面都会使用的方法，并且不允许被重写
     */
    $scope.closePop = function (name) {
        popUpService.close(name);
    };
    /**
     * 私有方法
     */
    function clearHover() {
        angular.forEach($scope.moduleButtons, function (button) {
            button.hover = null;
        });
    }

    /**
     * 右上角切换模块和营业部门时用到
     */
    function loginSuccess() {
        sessionStorage.setItem('moduleLink', LoginService.getModuleLink());
        sessionStorage.setItem('module', LoginService.getModule());
        $scope.title=LoginService.getTitle();
        var permission = LoginService.getOwnPermissionList();//操作员拥有的权限
        var moduleList = LoginService[LoginService.getModuleLink()];
        for (var i = 0; i < moduleList.length; i++) {
            var module = moduleList[i];
            if (permission.indexOf(module.name) > -1) {
                module.hover='hover';
                webService.redirect(module.href);
                break;
            }//判断该操作员是否有该模块主页的权限，有的话就跳转，没有的话依次查找知道最近的权限
        }
    }
}
]);
