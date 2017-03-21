/**
 * Created by 舒展 on 2016-06-21.
 * 报表控制器
 */
App.controller('reportController', ['$scope', 'host', 'dataService', 'util', 'LoginService', '$route', 'webService', 'popUpService', 'dateFilter', '$window', 'messageService', function ($scope, host, dataService, util, LoginService, $route, webService, popUpService, dateFilter, $window, messageService) {
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
    var queryMessage={};
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
    $scope.exchangeUserReport = function (userId, beginTime, endTime, format) {
        $scope.reportJson={userId: userId, beginTime: beginTime, endTime: endTime, format: format};
        webService.post('exchangeUserReport', $scope.reportJson)
            .then(function (r) {
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
                $scope.dailyReportRemark = '会员充值：' + r.vipPay + ' 单位结算：' + r.companyPay + ' 实际抵用：' + r.companyDebt;
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
    /**
     * 押金收款表
     */
    $scope.depositUserReportFields=[
        {name:'操作员',id:'user'},
        {name:'币种',id:'currency'},
        {name:'押金',id:'deposit'}
    ];

    $scope.depositUserReport = function (beginTime, endTime) {
        webService.post('depositUserReport', {
            beginTime: beginTime,
            endTime: endTime
        })
            .then(function (list) {
                queryMessage.beginTime=beginTime;
                queryMessage.endTime=endTime;
                $scope.depositUserReportList=list;
            })
    };
    /*查询押金明细*/
    $scope.depositUserReportItemClick=function (item, id) {
        var query={};
        query.condition='currency='+util.wrapWithBrackets(item.currency)+' and user_id='+util.wrapWithBrackets(item.user)+' and do_time>'+util.wrapWithBrackets(dateFilter(queryMessage.beginTime,'yyyy-MM-dd HH:mm:ss'))+' and do_time<'+util.wrapWithBrackets(dateFilter(queryMessage.endTime,'yyyy-MM-dd HH:mm:ss'));
        webService.post('debtIntegrationGet', query)
            .then(function (r) {
                popUpService.pop('debtDetail', null, null, r);
            })
    }
}]);
