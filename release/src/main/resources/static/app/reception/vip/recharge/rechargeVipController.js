/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('rechargeVipController',['$scope','webService','popUpService','dataService','util',function ($scope,webService,popUpService,dataService,util) {
    var vip=popUpService.getParam();
    $scope.vipNumber=vip.vipNumber;
    var p1={condition:'check_in=1'};
    dataService.refreshCurrencyList(p1)
        .then(function () {
            /*押金币种*/
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currency = '人民币';
        });
    $scope.recharge=function () {
        var p=[$scope.vipNumber,$scope.money,$scope.deserve,$scope.currency];
        webService.post('vipRecharge',p)
            .then(function (r) {
                webService.openReport(r);
                popUpService.close('recharge');
            })
    }
}]);