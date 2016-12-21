/**
 * Created by Administrator on 2016/9/25 0025.
 */
App.controller('roomShopDetailController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.roomShopDetailFields=[
        {name:'商品',id:'item'},
        {name:'类别',id:'category',filter:'list'},
        {name:'单价',id:'price'},
        {name:'数量',id:'num',sum:'true'},
        {name:'合计',id:'totalMoney',sum:'true'},
        {name:'房间',id:'room'},
        {name:'操作时间',id:'doTime',filter:'date'},
        {name:'结算时间',id:'doneTime',filter:'date',desc:'0'}
    ];
    $scope.roomShopDetailList=popUpService.getParam();
}]);