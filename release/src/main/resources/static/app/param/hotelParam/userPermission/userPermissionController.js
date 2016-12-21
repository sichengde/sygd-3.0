/**
 * Created by 舒展 on 2016/7/23 0023.
 * 权限定义
 * 每个操作员新增一个moduleList对象a，a对象为属性数组，每个属性为模块，属性对应的值为一个对象b，b对象是一个数组，数组中每个对象只有两个属性check--是否被选中，text--权限名称
 * a{模块1:b[{text:'权限1',check:'true'}]}
 */
App.controller('userPermissionController', ['$scope', 'dataService','util','webService','popUpService','messageService', function ($scope, dataService,util,webService,popUpService,messageService) {
    $scope.userList = dataService.getUserList();
    var availableModule=dataService.getAvailableModule();
    /*把模块字符串转化成模块数组*/
    angular.forEach($scope.userList,function (value) {
        var module=value.moduleArray.split(' ');//每个操作员的模块字符串数组
        value.moduleList={};//对象a
        angular.forEach(module,function (value2) {
            value.moduleList[value2]=[];//对象b
            var permissionList=util.getValueByField(availableModule,'name',value2).permission;//权限数组，下面为他赋值对象属性
            for (var i = 0; i < permissionList.length; i++) {
                var permission=permissionList[i];
                var checked=(value.permissionArray.indexOf(permission) > -1);
                /*创建一个对象*/
                value.moduleList[value2].push({text:permission,check:checked});
            }
        })
    });
    /*确认修改*/
    $scope.updatePermission=function () {
        /*把权限转换成字符串*/
        var out='';//输出的权限字符串
        angular.forEach($scope.user.moduleList,function (value, key) {
            angular.forEach(value,function (value2) {
                if(value2.check){
                    out+=value2.text+' ';
                }
            })
        });
        /*字符串有变动，说明修改了，所以提交，否则弹出提示*/
        if(out!=$scope.user.permissionArray){
            $scope.user.permissionArray=out.substring(0,out.length-1);
            webService.post('userUpdate',[$scope.user])
                .then(function () {
                    popUpService.close('userPermission');
                })
        }else {
            messageService.setMessage({type:'alert',content:'您好像并没有做出修改'});
            popUpService.pop('message');
        }
    }
}]);