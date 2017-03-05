/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('companyPayController', ['$scope', 'webService', 'dataService', 'popUpService', 'util', 'messageService', 'dateFilter', function ($scope, webService, dataService, popUpService, util, messageService, dateFilter) {
    var param = popUpService.getParam();
    if (param.debtList) {//指定明细的精确解算
        $scope.targetPay = true;
        $scope.company = param.company;
        $scope.debt = param.debt;
        $scope.pay = param.debt;
    } else {//没指定账务
        $scope.company = param.name;
        $scope.totalDebt = param.debt;
    }
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.payBy = '按明细结';
    //账务明细
    $scope.companyDebtFields = [
        {name: '单位名称', id: 'company', width: '230px'},
        {name: '签单人', id: 'lord', width: '100px'},
        {name: '记账时间', id: 'doTime', date: 'true', width: '100px', desc: '0'},
        {name: '挂账款', id: 'debt', width: '100px'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '备注', id: 'description', width: '150px'},
        {name: '模块', id: 'pointOfSale', width: '150px'},
        {name: '类型', id: 'secondPointOfSale', width: '150px'},
        {name: '操作员', id: 'userId', width: '120px'}
    ];
    dataService.initData(['refreshCurrencyList'], [{condition: 'check_in=1'}])
        .then(function () {
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currencyList.push('转单位');
            $scope.currency = '人民币';
        });
    dataService.refreshPointOfSaleList({condition: 'module=\'接待\''})
        .then(function (r) {
            $scope.pointOfSaleList = ['全部', '离店'].concat(r[0].secondPointOfSale.split(' '));
            $scope.pointOfSale = $scope.pointOfSaleList[0];
        });
    $scope.moduleList = ['全部', '接待', '餐饮', '桑拿'];
    $scope.module = '全部';
    var pay;
    $scope.showDetail = function () {
        if (!$scope.pay && $scope.payBy == '定额结算') {
            messageService.setMessage({type: 'error', content: '请输入金额'});
            popUpService.pop('message');
            return;
        }
        if ($scope.payBy == '定额结算') {
            var companyPost = {};
            companyPost.companyName = $scope.company;
            companyPost.pay = $scope.pay;
            pay = $scope.pay;//避免二次修改
            webService.post('getNotPaidDebt', companyPost)
                .then(function (r) {
                    var totalDebt;
                    if (r.length > 0) {
                        totalDebt = r[0].total;
                    }
                    /*合计多了就冲账*/
                    var companyDebtTmp = {};
                    if (totalDebt > pay) {
                        companyDebtTmp.company = $scope.company;
                        companyDebtTmp.debt = pay - totalDebt;
                        companyDebtTmp.doTime = r[r.length - 1].doTime;
                        companyDebtTmp.description = '定额结算冲账';
                        companyDebtTmp.tmp = true;
                        r.push(companyDebtTmp);
                    }
                    /*少了就入杂单*/
                    if (totalDebt < pay) {
                        companyDebtTmp.company = $scope.company;
                        companyDebtTmp.debt = pay - totalDebt;
                        companyDebtTmp.doTime = r[r.length - 1].doTime;
                        companyDebtTmp.description = '定额结算杂单';
                        companyDebtTmp.tmp = true;
                        r.push(companyDebtTmp);
                    }
                    $scope.companyDebtList = r;
                })
        } else {
            var query = {condition: 'company=' + util.wrapWithBrackets($scope.company)};
            if ($scope.beginTime) {
                var beginTime = dateFilter($scope.beginTime, 'yyyy-MM-dd HH:mm:ss');
                query.condition += ' and do_time>' + util.wrapWithBrackets(beginTime);
            }
            if ($scope.endTime) {
                var endTime = dateFilter($scope.endTime, 'yyyy-MM-dd HH:mm:ss');
                query.condition += ' and do_time<' + util.wrapWithBrackets(endTime);
            }
            if ($scope.pointOfSale != '全部') {
                query.condition += ' and second_point_of_sale=' + util.wrapWithBrackets($scope.pointOfSale);
            }
            if ($scope.module != '全部') {
                query.condition += ' and point_of_sale=' + util.wrapWithBrackets($scope.module);
            }
            dataService.refreshCompanyDebtList(query)
                .then(function (r) {
                    $scope.companyDebtList = r;
                    $scope.debt = 0.0;
                    for (var i = 0; i < r.length; i++) {
                        var companyDebt = r[i];
                        $scope.debt += companyDebt.debt;
                    }
                })
        }
    };
    $scope.submit = function () {
        if ($scope.payBy == '定额结算' && pay != $scope.pay) {
            messageService.setMessage({type: 'error', content: '修改金额后请执行一次查询'});
            popUpService.pop('message');
            return;
        }
        if (!$scope.pay) {
            messageService.setMessage({type: 'error', content: '请输入实付金额'});
            popUpService.pop('message');
            return;
        }
        if ($scope.pay > $scope.debt) {
            messageService.setMessage({type: 'error', content: '实付金额不能大于应付金额'});
            popUpService.pop('message');
            return;
        }
        /*在这里先进行指向结算*/
        if ($scope.targetPay) {
            var companyTargetPost = {};
            companyTargetPost.companyName = $scope.company;
            companyTargetPost.pay = $scope.pay;
            companyTargetPost.debt = $scope.debt;
            companyTargetPost.remark = $scope.remark;
            companyTargetPost.debtHistoryList = param.debtList;
            companyTargetPost.paySerialMap = param.paySerialMap;
            companyTargetPost.currencyPost = $scope.currencyPayList[0];
            webService.post('companyTargetPay', companyTargetPost)
                .then(function (r) {
                    popUpService.close('companyPay');
                    /*弹出打印预览界面*/
                    webService.openReport(r);
                });
            return
        }
        if (!$scope.companyDebtList.length > 0) {
            messageService.setMessage({type: 'error', content: '没有可结算的明细'});
            popUpService.pop('message');
            return;
        }
        var companyPost = {};
        companyPost.companyName = $scope.company;
        companyPost.pay = $scope.pay;
        if ($scope.payBy == '定额结算') {
            companyPost.debt = $scope.pay;
        } else {
            companyPost.debt = $scope.debt;
        }
        companyPost.remark = $scope.remark;
        companyPost.companyDebtList = $scope.companyDebtList;
        companyPost.currencyPost = $scope.currencyPayList[0];
        webService.post('companyPay', companyPost)
            .then(function (r) {
                popUpService.close('companyPay');
                /*弹出打印预览界面*/
                webService.openReport(r);
            });
        //}这里是判断定量使用预存款时的方式
    };
}]);