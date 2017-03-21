App.controller('exchangeUserCKPopController',['$scope','popUpService','dateFilter',function ($scope,popUpService,dateFilter) {
    var r=popUpService.getParam().r;
    $scope.reportJson=popUpService.getParam().reportJson;
    $scope.exchangeUserCkReportList = r;
    /*生成交班审核表的查询信息，用来传递给jasper*/
    $scope.queryMessage = dateFilter($scope.reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter($scope.reportJson.endTime, 'yyyy-MM-dd HH:mm:ss') + '  操作员:' + ($scope.reportJson.userId ? $scope.reportJson.userId : '全部');
    $scope.closePop=function () {
        popUpService.close('exchangeUserCKPop');
    }
}]);