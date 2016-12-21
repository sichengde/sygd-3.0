/**
 * Created by Administrator on 2016/8/2 0002.
 */
App.controller('userAddController', ['$scope', 'dataService', 'webService', 'popUpService', 'userService','messageService', function ($scope, dataService, webService, popUpService, userService,messageService) {
    var param = popUpService.getParam();//新增还是修改
    $scope.mode = param.mode;
    if ($scope.mode == 'update') {
        $scope.userList = dataService.getUserList();
    }
    dataService.initData(['refreshPointOfSaleList', 'refreshAvailableModuleList'])
        .then(function () {
            $scope.pointOfSaleList = dataService.getPointOfSale();
            for (var i = 0; i < $scope.pointOfSaleList.length; i++) {
                var obj2 = $scope.pointOfSaleList[i];
                obj2.check = false;
            }
            $scope.moduleList = dataService.getAvailableModule();
            /*为每个模块增加check属性（是否选择）*/
            for (i = 0; i < $scope.moduleList.length; i++) {
                var obj = $scope.moduleList[i];
                obj.check = false;
                /*为每个权限增加permissionList数组对象，每个对象包括text内容和check属性，内容就是之前的字符串，check代表是否选择*/
                obj.permissionList = [];
                for (var j = 0; j < obj.permission.length; j++) {
                    var obj1 = obj.permission[j];
                    obj.permissionList.push({text: obj1, check: false});
                }
            }
        });
    $scope.chooseUser = function (user) {
        var moduleArray = user.moduleArray;
        var permissionArray = user.permissionArray;
        var pointOfSaleArray = user.pointOfSaleArray;
        /*检查营业部门有没有*/
        for (var i = 0; i < $scope.pointOfSaleList.length; i++) {
            var obj2 = $scope.pointOfSaleList[i];
            if (pointOfSaleArray.indexOf(obj2.firstPointOfSale) > -1) {
                obj2.check = true;
            }
        }
        /*检查模块和全县有没有*/
        for (i = 0; i < $scope.moduleList.length; i++) {
            var obj = $scope.moduleList[i];
            if (moduleArray.indexOf(obj.name) > -1) {
                obj.check = true;
            }
            for (var j = 0; j < obj.permissionList.length; j++) {
                var obj1 = obj.permissionList[j];
                if (permissionArray.indexOf(obj1.text) > -1) {
                    obj1.check = true;
                }
            }
        }
    };
    $scope.submitUserAdd = function () {
        if ($scope.mode == 'add') {
            var user = {};
            user.userId = $scope.userId;
            user.password = $scope.password;
            user.moduleArray = userService.getCheckedString($scope.moduleList, 'name');
            user.moduleArray = user.moduleArray.substring(0, user.moduleArray.length - 1);
            user.permissionArray = '';
            for (var i = 0; i < $scope.moduleList.length; i++) {
                var obj = $scope.moduleList[i];
                if (obj.check) {
                    user.permissionArray += userService.getCheckedString(obj.permissionList, 'text');
                }
            }
            user.permissionArray = user.permissionArray.substring(0, user.permissionArray.length - 1);
            user.pointOfSaleArray = userService.getCheckedString($scope.pointOfSaleList, 'firstPointOfSale');
            user.pointOfSaleArray = user.pointOfSaleArray.substring(0, user.pointOfSaleArray.length - 1);
            webService.post('userAdd', user)
                .then(function () {
                    popUpService.close('userAdd');
                    messageService.actionSuccess();
                })
        } else {
            $scope.user.moduleArray = userService.getCheckedString($scope.moduleList, 'name');
            $scope.user.moduleArray = $scope.user.moduleArray.substring(0, $scope.user.moduleArray.length - 1);
            $scope.user.permissionArray = '';
            for (var i = 0; i < $scope.moduleList.length; i++) {
                var obj = $scope.moduleList[i];
                if (obj.check) {
                    $scope.user.permissionArray += userService.getCheckedString(obj.permissionList, 'text');
                }
            }
            $scope.user.permissionArray = $scope.user.permissionArray.substring(0, $scope.user.permissionArray.length - 1);
            $scope.user.pointOfSaleArray = userService.getCheckedString($scope.pointOfSaleList, 'firstPointOfSale');
            $scope.user.pointOfSaleArray = $scope.user.pointOfSaleArray.substring(0, $scope.user.pointOfSaleArray.length - 1);
            webService.post('userUpdate', [$scope.user])
                .then(function () {
                    popUpService.close('userAdd');
                    messageService.actionSuccess();
                })
        }
    }
}]);