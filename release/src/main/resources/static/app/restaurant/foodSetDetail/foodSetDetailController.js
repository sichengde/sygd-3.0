/**
 * Created by Administrator on 2016/11/3 0003.
 */
App.controller('foodSetDetailController',['$scope','popUpService','dataService','LoginService','util',function ($scope,popUpService,dataService,LoginService,util) {
    $scope.setName=popUpService.getParam();
    var pointOfSale=LoginService.getPointOfSale();
    dataService.refreshFoodSetList({condition:'point_of_sale='+util.wrapWithBrackets(pointOfSale)+' and set_name='+util.wrapWithBrackets($scope.setName)})
        .then(function (r) {
            $scope.foodSetList=r;
        });
    $scope.foodSetFields=[
        {name:'菜品',id:'foodName'},
        {name:'数量',id:'num'}
    ]
}]);