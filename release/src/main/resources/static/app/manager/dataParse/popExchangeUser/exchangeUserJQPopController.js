App.controller('exchangeUserJQPopController',['$scope','popUpService','dateFilter',function ($scope,popUpService,dateFilter) {
    var r=popUpService.getParam().r;
    $scope.reportJson=popUpService.getParam().reportJson;
    $scope.exchangeUserReportList = r.exchangeUserRowList;
    $scope.payTotal = r.payTotal;
    $scope.moneyIn = r.moneyIn;
    $scope.moneyOut = r.moneyOut;
    $scope.depositAll = r.depositAll;
    $scope.remarkExchangeUser = '参与统计结算款:' + $scope.payTotal + ',杂单:' + $scope.moneyIn + ',冲账:' + $scope.moneyOut + ',在店押金:' + $scope.depositAll;
    $scope.queryMessageExchangeUser = dateFilter($scope.reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter($scope.reportJson.endTime, 'yyyy-MM-dd HH:mm:ss') + '  操作员:' + ($scope.reportJson.userId ? $scope.reportJson.userId : '全部');
    $scope.closePop=function () {
        popUpService.close('exchangeUserJQPop');
    }
}]);