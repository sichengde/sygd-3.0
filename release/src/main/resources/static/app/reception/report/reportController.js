/**
 * Created by 舒展 on 2016-06-21.
 * 报表控制器
 */
App.controller('reportController', ['$scope', 'host', 'dataService', 'util', 'LoginService', '$route', 'webService', 'popUpService', 'dateFilter', '$window', 'messageService', function ($scope, host, dataService, util, LoginService, $route, webService, popUpService, dateFilter, $window, messageService) {
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
    dataService.refreshPointOfSaleList().then(function () {
        $scope.pointOfSaleList = dataService.getPointOfSale();
    });
    dataService.initData(['refreshExchangeUserList', 'refreshUserList'])
        .then(function () {
            $scope.exchangeUserList = dataService.getExchangeUserList();
            $scope.userList = util.objectListToString(dataService.getUserList(), 'userId');
        });
    webService.post('sql', 'SELECT DISTINCT sale_man FROM company WHERE sale_man IS NOT NULL')
        .then(function (r) {
            $scope.saleManList = r;
            $scope.saleMan = r[0];
        });
    $scope.userId = LoginService.getUser();
    $scope.chooseExchangeUser = function (r) {
        if (r.beginTime < r.endTime) {
            $scope.beginTime = util.newDateAndTime(new Date(), r.beginTime);
            $scope.endTime = util.newDateAndTime(new Date(), r.endTime);
        } else {
            $scope.beginTime = util.newDateAndTime(new Date(new Date() - 24 * 60 * 60 * 1000), r.beginTime);
            $scope.endTime = util.newDateAndTime(new Date(), r.endTime);
        }
    };
    /**
     * 实时房态表
     */
    $scope.realRoomState = function () {
        document.getElementById('realRoomFormReport').action = host + '/realRoomStateReport';
        document.getElementById('realRoomFormReport').submit();
        angular.element("#iframeReport").fadeIn('slow');
    };
    /**
     * 结账明细表
     */
    $scope.checkOutDetail = function (userId, beginTime, endTime, format) {
        webService.post('checkOutDetailReport', {
            userId: userId,
            beginTime: beginTime,
            endTime: endTime,
            format: format
        })
            .then(function (r) {
                window.open(host + "/receipt/" + r);
            });
    };
    /**
     * 交班审核表
     */
    $scope.exchangeUserReportFields = [
        {name: '币种', id: 'currency'},
        {name: '结算款', id: 'pay'},
        {name: '预付款', id: 'deposit'},
        {name: '退预付款', id: 'cancelDeposit'},
        {name: '预订订金', id: 'subscription'},
        {name: '退订金', id: 'cancelSubscription'},
        {name: '会员充值', id: 'vipRecharge'},
        {name: '单位结算', id: 'companyPay'}
    ];
    $scope.exchangeUserReport = function (userId, beginTime, endTime, format) {
        webService.post('exchangeUserReport', {userId: userId, beginTime: beginTime, endTime: endTime, format: format})
            .then(function (r) {
                $scope.exchangeUserReportJQ = r;
                $scope.exchangeUserReportList = r.exchangeUserRowList;
                $scope.payTotal = r.payTotal;
                $scope.moneyIn = r.moneyIn;
                $scope.moneyOut = r.moneyOut;
                $scope.depositAll = r.depositAll;
                $scope.remarkExchangeUser = '参与统计结算款:' + $scope.payTotal + ',杂单:' + $scope.moneyIn + ',冲账:' + $scope.moneyOut + ',在店押金:' + $scope.depositAll;
                $scope.queryMessageExchangeUser = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss') + '  操作员:' + (userId ? userId : '全部');
            })
    };
    /*小表带商品明细*/
    $scope.exchangeUserReportSmall = function (userId, beginTime, endTime, format) {
        webService.post('exchangeUserReportSmall', {
            userId: userId,
            beginTime: beginTime,
            endTime: endTime,
            format: format
        })
            .then(function (r) {
                webService.openReport(r);
            })
    };
    $scope.exchangeUserReportItemClick = function (item, id) {
        var reportJson = $scope.exchangeUserReportJQ.reportJson;
        var beginTimeDate = reportJson.beginTime;
        var endTimeDate = reportJson.endTime;
        var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
        var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
        var userId = reportJson.userId;
        reportJson.currency = item.currency;
        /*分析表头*/
        switch (id) {
            case 'pay':
                webService.post('debtPayGetByDateCurrencyUserId', reportJson)
                    .then(function (r) {
                        popUpService.pop('debtPay', null, null, r);
                    });
                break;
            case'deposit':
                var conditionUser = '';
                if (userId) {
                    conditionUser = 'and user_id= ' + util.wrapWithBrackets(userId)
                }
                var p = {condition: ' deposit>0 ' + conditionUser + ' and currency= ' + util.wrapWithBrackets(reportJson.currency) + ' and do_time> ' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime)}
                webService.post('debtIntegrationGet', p)
                    .then(function (r) {
                        popUpService.pop('debtDetail', null, null, r);
                    });
                break;
            case 'cancelDeposit':
                var conditionUser = '';
                if (userId) {
                    conditionUser = 'and user_id= ' + util.wrapWithBrackets(userId)
                }
                var p = {condition: ' ((deposit IS NOT NULL AND done_time IS NOT NULL ' + ' and done_time> ' + util.wrapWithBrackets(beginTime) + ' and done_time<' + util.wrapWithBrackets(endTime) + ') or (deposit<0 and done_time IS NULL ' + ' and do_time> ' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime) + ' ))  ' + conditionUser + ' and currency= ' + util.wrapWithBrackets(reportJson.currency)}
                webService.post('debtIntegrationGet', p)
                    .then(function (r) {
                        popUpService.pop('debtDetail', null, null, r);
                    });
                break;
        }
    };
    $scope.moneyInDetail = function () {
        var reportJson = $scope.exchangeUserReportJQ.reportJson;
        var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
        var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
        var userId = reportJson.userId;
        var p = {condition: 'currency=\'杂单\' and user_id=' + util.wrapWithBrackets(userId) + ' and do_time>' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime)};
        webService.post('debtIntegrationGet', p)
            .then(function (r) {
                popUpService.pop('debtDetail', null, null, r);
            })
    };
    $scope.moneyOutDetail = function () {
        var reportJson = $scope.exchangeUserReportJQ.reportJson;
        var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
        var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
        var userId = reportJson.userId;
        var p = {condition: 'currency=\'冲账\' and user_id=' + util.wrapWithBrackets(userId) + ' and do_time>' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime)};
        webService.post('debtIntegrationGet', p)
            .then(function (r) {
                popUpService.pop('debtDetail', null, null, r);
            })
    };
    $scope.depositAllDetail = function () {
        var p = {condition: 'deposit>0 and ifnull(remark,0)!=\'已退\''};
        webService.post('debtGet', p)
            .then(function (r) {
                popUpService.pop('debtDetail', null, null, r);
            })
    };
    /**
     * 借贷平衡表
     */
    $scope.dailyReport = function (beginTime, endTime, format) {
        webService.post('dailyReport', {beginTime: beginTime, endTime: endTime, format: format})
            .then(function (r) {
                $scope.dailyReportFields = [];
                var paramList = r.dailyReportList[0].paramList;
                $scope.dailyReportFields.push({name: '项目', id: 'secondPointOfSale'});
                for (var i = 0; i < paramList.length; i++) {
                    var param = paramList[i];
                    $scope.dailyReportFields.push({name: param, id: 'paramField' + (i + 1)});
                }
                $scope.dailyReportFields.push({name: '合计', id: 'total'});
                $scope.dailyReportList = r.dailyReportList;
                $scope.dailyReportRemark='会员充值：'+r.vipPay+' 单位结算：'+r.companyPay+' 实际抵用：'+r.companyDebt;
            });
    };
    $scope.dailyReportItemClick = function (item, id) {
        /*提取查询信息*/
        var reportJson = $scope.dailyReportList[0].reportJson;
        var beginTimeDate = reportJson.beginTime;
        var endTimeDate = reportJson.endTime;
        var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
        var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
        //先获取模块，一级营业部门
        var firstPointOfSale = util.getValueByField($scope.dailyReportFields, 'id', id).name;
        var module = util.getValueByField($scope.pointOfSaleList, 'firstPointOfSale', firstPointOfSale).module;
        /*获取二级营业部门(也可以是币种)*/
        var secondPointOfSale = item.secondPointOfSale;
        /*第一种，消费明细查询（前）*/
        if (item.mark == 'pointOfSale') {
            switch (module) {
                case '接待':
                    if (secondPointOfSale == '房费') {
                        /*构建查询条件*/
                        var p = {condition: 'consume>0 and done_time>' + util.wrapWithBrackets(beginTime) + ' and done_time<' + util.wrapWithBrackets(endTime) + ' and point_of_sale=' + util.wrapWithBrackets(item.secondPointOfSale)};
                        webService.post('debtHistoryGet', p)
                            .then(function (r) {
                                popUpService.pop('debtDetail', null, null, r);
                            });
                    } else if (secondPointOfSale == '房吧') {
                        webService.post('roomShopDetailGetByDate', reportJson)
                            .then(function (r) {
                                popUpService.pop('roomShopDetail', null, null, r);
                            })
                    } else if (secondPointOfSale == '零售') {
                        webService.post('retailGetByDate', reportJson)
                            .then(function (r) {
                                popUpService.pop('roomShopDetail', null, null, r);
                            })
                    } else if (secondPointOfSale == '冲账') {
                        var p = {condition: 'consume<0 and done_time>' + util.wrapWithBrackets(beginTime) + ' and done_time<' + util.wrapWithBrackets(endTime)};
                        webService.post('debtHistoryGet', p)
                            .then(function (r) {
                                popUpService.pop('debtDetail', null, null, r);
                            });
                    }
                    break;
                case '餐饮':
                    webService.post('deskDetailHistoryGetByDatePointOfSale', {
                        firstPointOfSale: firstPointOfSale,
                        secondPointOfSale: secondPointOfSale,
                        beginTime: beginTimeDate,
                        endTime: endTimeDate
                    })
                        .then(function (r) {
                            popUpService.pop('deskDetailHistory', null, null, r);
                        });
                    break;
            }
        } else if (item.mark == 'currency') {/*第二种，币种结账明细查询*/
            switch (module) {
                case '接待':
                    webService.post('debtPayGetByDateCurrency', {
                        beginTime: beginTimeDate,
                        endTime: endTimeDate,
                        currency: secondPointOfSale
                    })
                        .then(function (r) {
                            popUpService.pop('debtPay', null, null, r);
                        });
                    break;
                case '餐饮':
                    webService.post('deskPayGetByDateCurrency', {
                        currency: secondPointOfSale,
                        pointOfSale: firstPointOfSale,
                        beginTime: beginTimeDate,
                        endTime: endTimeDate
                    })
                        .then(function (r) {
                            popUpService.pop('deskPay', null, null, r);
                        })
                    break;
            }
        }
    };
}]);
