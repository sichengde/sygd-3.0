/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('rechargeVipController',['$scope','webService','popUpService','dataService','util',function ($scope,webService,popUpService,dataService,util) {
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.vipRecharge={};//用于提交的对向
    var vip=popUpService.getParam();
    $scope.vipRecharge.vipNumber=vip.vipNumber;
    var p1={condition:'check_in=1'};
    dataService.refreshCurrencyList(p1)
        .then(function () {
            /*押金币种*/
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currencyList.push('转单位');
            $scope.currency = '人民币';
        });
    $scope.recharge=function () {
        $scope.vipRecharge.currencyPost=$scope.currencyPayList[0];
        return webService.post('vipRecharge',$scope.vipRecharge)
            .then(function (r) {
                webService.openReport(r);
                popUpService.close('recharge');
            })
    }
}]);