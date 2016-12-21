/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('companyPayController', ['$scope', 'webService', 'dataService', 'popUpService', 'util', 'messageService', function ($scope, webService, dataService, popUpService, util, messageService) {
    dataService.initData(['refreshCompanyList', 'refreshCurrencyList'], [{condition: 'if_debt=\'y\''}, {condition: 'check_in=1'}])
        .then(function () {
            $scope.companyList = util.objectListToString(dataService.getCompanyList(), 'name');
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currency = '人民币';
        });
    $scope.submit = function () {
        if (!$scope.total) {
            $scope.total = 0;
        }
        if (!$scope.deposit) {
            $scope.deposit = 0;
        }
        /*看看支付金额是不是超过了单位的挂账款*/
        var company=util.getValueByField(dataService.getCompanyList(),'name',$scope.company);
        if(!company){
            messageService.setMessage({type:'error',content:'输入的单位不存在'});
            popUpService.pop('message');
            return;
        }
        if(company.debt<$scope.total){
            messageService.setMessage({type:'error',content:'结算的金额超过了单位挂账款'});
            popUpService.pop('message');
            return;
        }
        if ((parseFloat($scope.deposit) + $scope.pay*1) != $scope.total) {
            messageService.setMessage({content: '结账金额不等于支付金额加上预存款，确认结算?'})
            messageService.actionChoose()
                .then(function () {
                    pay();
                })
        }else {
            pay();
        }
    };
    function pay() {
        var companyPost = {};
        companyPost.companyName = $scope.company;
        companyPost.total = $scope.total;
        companyPost.deposit = $scope.deposit;
        companyPost.pay = $scope.pay;
        companyPost.currency = $scope.currency;
        webService.post('companyPay', companyPost)
            .then(function (r) {
                popUpService.close('companyPay');
                /*弹出打印预览界面*/
                window.open(host + "/receipt/" + r);
            })
    }
}]);