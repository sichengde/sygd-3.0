/**
 * Created by Administrator on 2016/10/20 0020.
 */
App.controller('rightClickMenuController',['$scope','popUpService','webService',function ($scope,popUpService,webService) {
    $scope.menu=popUpService.getParam();
    $scope.sellOut=function () {
        $scope.menu.sellOut=true;
        $scope.menu.remain=$scope.num;
        webService.post('menuUpdate',[$scope.menu])
            .then(function () {
                popUpService.close('rightClickMenu');
            })
    };
    $scope.enough=function () {
        $scope.menu.sellOut=false;
        webService.post('menuUpdate',[$scope.menu])
            .then(function () {
                popUpService.close('rightClickMenu');
            })
    }
}]);