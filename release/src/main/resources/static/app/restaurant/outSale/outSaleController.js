/**
 * Created by Administrator on 2016-11-02.
 */
App.controller('outSaleController',['$scope','dataService','LoginService','util',function ($scope,dataService,LoginService,util) {
    var pointOfSale=LoginService.getPointOfSale();
    $scope.mode='category';//初始化是类别界面
    dataService.refreshSaleCountList({condition:'first_point_of_sale='+util.wrapWithBrackets(pointOfSale)})
        .then(function (r) {
            $scope.foodCategory=util.objectListToString(r,'name');        
        });
    $scope.chooseFoodCategory=function (foodCategory) {
        $scope.mode='food';//进入到菜品模式
    }
}]);