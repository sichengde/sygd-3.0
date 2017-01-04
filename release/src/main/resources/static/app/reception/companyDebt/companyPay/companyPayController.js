/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('companyPayController', ['$scope', 'webService', 'dataService', 'popUpService', 'util', 'messageService', function ($scope, webService, dataService, popUpService, util, messageService) {
    var company = popUpService.getParam();
    $scope.company = company.name;
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    dataService.initData(['refreshCompanyList', 'refreshCurrencyList'], [{condition: 'if_debt=\'y\''}, {condition: 'check_in=1'}])
        .then(function () {
            $scope.companyList = util.objectListToString(dataService.getCompanyList(), 'name');
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currencyList.push('转单位');
            $scope.currency = '人民币';
        });
    $scope.submit = function () {
        if (!$scope.total) {
            $scope.total = 0;
        }
        /*看看支付金额是不是超过了单位的挂账款*/
        if (company.debt < $scope.total) {
            messageService.setMessage({type: 'error', content: '结算的金额超过了单位挂账款'});
            popUpService.pop('message');
            return;
        }
        /*if ((parseFloat($scope.deposit) + $scope.pay*1) != $scope.total) {
         messageService.setMessage({content: '结账金额不等于支付金额加上预存款，确认结算?'})
         messageService.actionChoose()
         .then(function () {
         pay();
         })
         }else {*/
        pay();
        //}这里是判断定量使用预存款时的方式
    };
    function pay() {
        var companyPost = {};
        companyPost.companyName = $scope.company;
        companyPost.total = $scope.total;
        companyPost.useDeposit = $scope.useDeposit;
        companyPost.currencyPost=$scope.currencyPayList[0];
        webService.post('companyPay', companyPost)
            .then(function (r) {
                popUpService.close('companyPay');
                /*弹出打印预览界面*/
                window.open(host + "/receipt/" + r);
            })
    }
}]);