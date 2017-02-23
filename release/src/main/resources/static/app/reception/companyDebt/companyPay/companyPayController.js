/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('companyPayController', ['$scope', 'webService', 'dataService', 'popUpService', 'util', 'messageService', function ($scope, webService, dataService, popUpService, util, messageService) {
    var company = popUpService.getParam();
    $scope.company = company.name;
    $scope.totalDebt = company.debt;
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.initConditionCompany = 'company=' + util.wrapWithBrackets(company.name) + ' and ifnull(paid,false) = false and category=\'单位挂账\'';
    $scope.payBy = '定额结算';
    //账务明细
    $scope.companyDebtFields = [
        {name: '单位名称', id: 'company', width: '230px'},
        {name: '签单人', id: 'lord', width: '100px'},
        {name: '记账时间', id: 'doTime', date: 'true', width: '100px', desc: '0', filter: 'date'},
        {name: '挂账款', id: 'debt', width: '100px', sum: 'true'},
        {name: '操作类别', id: 'category', width: '150px'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '备注', id: 'description', width: '150px'},
        {name: '营业部门', id: 'pointOfSale', width: '150px'},
        {name: '操作员', id: 'userId', width: '120px'}
    ];
    dataService.initData(['refreshCurrencyList'], [{condition: 'check_in=1'}])
        .then(function () {
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currencyList.push('转单位');
            $scope.currency = '人民币';
        });
    $scope.showDetail = function () {
        if (!$scope.pay) {
            messageService.setMessage({type: 'error', content: '请输入金额'});
            popUpService.pop('message');
        }
        if ($scope.payBy = '定额结算') {
            var companyPost = {};
            companyPost.companyName = $scope.company;
            companyPost.total = $scope.pay;
            companyPost.currencyPost = $scope.currencyPayList[0];
            webService.post('getNotPaidDebt',companyPost)
        } else {

        }
    };
    $scope.submit = function () {
        if (!$scope.total) {
            return
        }
        /*/!*看看支付金额是不是超过了单位的挂账款，取消了预付，这个也没用了*!/
         if (company.debt < $scope.total) {
         messageService.setMessage({type: 'error', content: '结算的金额超过了单位挂账款'});
         popUpService.pop('message');
         return;
         }*/
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
        companyPost.currencyPost = $scope.currencyPayList[0];
        webService.post('companyPay', companyPost)
            .then(function (r) {
                popUpService.close('companyPay');
                /*弹出打印预览界面*/
                window.open(host + "/receipt/" + r);
            })
    }
}]);