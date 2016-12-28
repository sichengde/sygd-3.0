/**
 * Created by Administrator on 2016/10/22 0022.
 */
App.controller('buffetController',['$scope','dataService','LoginService','webService','messageService','util',function ($scope,dataService,LoginService,webService,messageService,util) {
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.num=1;
    var pointOfSale=LoginService.getPointOfSale();
    dataService.initData(['refreshMenuList'],[{condition:'category=\'自助餐\' and point_of_sale='+util.wrapWithBrackets(pointOfSale)}])
        .then(function () {
            $scope.menuList=dataService.getMenuList();
        });
    webService.post('getTodayBuffetPeople',{pointOfSale:pointOfSale})
        .then(function (r) {
            $scope.todayTotalNum=r;
        });
    $scope.buffetPay=function (menu,num) {
        var post={};
        post.menu=menu;
        post.num=num;
        post.pointOfSale=pointOfSale;
        post.currencyPostList=$scope.currencyPayList;
        webService.post('buffetPay',post)
            .then(function () {
                messageService.actionSuccess();
                /*数据归零*/
                $scope.todayTotalNum+=num;
                $scope.num=1;
                $scope.currencyPayList[0] = {};
                $scope.currencyPayList[0].currency = $scope.currencyList[0];
            });
    };
}]);