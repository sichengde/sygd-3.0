/**
 * Created by Administrator on 2016-07-18.
 */
App.controller('companyDepositController', ['$scope', 'webService', 'dataService', 'popUpService', 'util',
    function ($scope, webService, dataService, popUpService, util) {
        dataService.initData(['refreshCompanyList', 'refreshCurrencyList'], [{condition: 'if_debt=\'y\''}, {condition: 'check_in=1'}])
            .then(function () {
                $scope.companyList = util.objectListToString(dataService.getCompanyList(), 'name');
                $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
                $scope.currency = '人民币';
            });
        $scope.submit = function () {
            if (!$scope.deposit) {
                $scope.deposit = 0;
            }
            webService.post('companyDeposit', [$scope.company, $scope.deposit,$scope.currency])
                .then(function () {
                    popUpService.close('companyPay');
                })
        }
    }]);