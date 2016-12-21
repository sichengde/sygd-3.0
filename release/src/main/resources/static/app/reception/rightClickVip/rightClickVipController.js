App.controller('rightClickVipController',['$scope','popUpService',function ($scope,popUpService) {
    var vip=popUpService.getParam();
    $scope.clickPop=function (name) {
        popUpService.close('rightClickVip',true);
        popUpService.pop(name,null,null,vip);
    }
}]);