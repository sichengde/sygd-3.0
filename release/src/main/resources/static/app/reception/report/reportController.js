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
     * 客房经营状况
     */
    var columnDefs = [
        {
            headerName: '',
            marryChildren: true,
            children: [
                {headerName: "月份", field: "month"}
            ]
        },
        {
            headerName: '接待',
            marryChildren: true,
            children: [
                {headerName: "住客率", field: "averageRent"},
                {headerName: "平均房价", field: "averagePrice"},
                {headerName: "REVPER", field: "revper"},
                {headerName: "接待人次", field: "guestNum"},
                {headerName: "接待团队", field: "groupNum"},
                {headerName: "外宾", field: "foreigner"}
            ]
        },
        {
            headerName: '收入',
            marryChildren: true,
            children: [
                {
                    headerName: '房费',
                    marryChildren: true,
                    groupId: 'roomConsume',
                    children: []
                }
            ]
        }
    ];
    var allColumnIds = [
        'month',
        'averageRent',
        'averagePrice',
        'revper',
        'guestNum',
        'groupNum',
        'foreigner'
    ];
    var pointOfSaleIds = [];
    dataService.initData(['refreshPointOfSaleList', 'refreshGuestSourceList'], [{condition: 'module=\'接待\''}])
        .then(function () {
            var guestSourceList = dataService.getGuestSourceList();
            var totalRoomConsumeValueGetter = '';
            for (var i = 0; i < guestSourceList.length; i++) {
                columnDefs[2].children[0].children.push({
                    headerName: guestSourceList[i].guestSource,
                    field: guestSourceList[i].guestSource,
                    columnGroupShow: 'open'
                });
                pointOfSaleIds.push(guestSourceList[i].guestSource);
                totalRoomConsumeValueGetter += 'getValue("' + guestSourceList[i].guestSource + '")+';
            }
            totalRoomConsumeValueGetter = totalRoomConsumeValueGetter.substring(0, totalRoomConsumeValueGetter.length - 1);
            columnDefs[2].children[0].children.push({
                headerName: '总计',
                colId: 'totalRoomConsume',
                valueGetter: totalRoomConsumeValueGetter,
                volatile: true
            });
            pointOfSaleIds.push('totalRoomConsume');
            var secondPointOfSale = dataService.getPointOfSale()[0].secondPointOfSale.split(' ');
            var totalPointOfSaleConsumeValueGetter = '';
            for (var i = 0; i < secondPointOfSale.length; i++) {
                if (secondPointOfSale[i] == '房费') {
                    continue;
                }
                columnDefs[2].children.push({headerName: secondPointOfSale[i], field: secondPointOfSale[i]});
                columnDefs[2].children.push({headerName: '次数', field: secondPointOfSale[i] + '次数'});
                totalPointOfSaleConsumeValueGetter += 'getValue("' + secondPointOfSale[i] + '")+';
                pointOfSaleIds.push(secondPointOfSale[i]);
                pointOfSaleIds.push(secondPointOfSale[i] + '次数');
            }
            /*未定义*/
            columnDefs[2].children.push({headerName: "未定义", field: "未定义"});
            columnDefs[2].children.push({headerName: '次数', field: '未定义次数'});
            totalPointOfSaleConsumeValueGetter += 'getValue("未定义")+';
            pointOfSaleIds.push("未定义");
            pointOfSaleIds.push('未定义次数');
            totalPointOfSaleConsumeValueGetter += totalRoomConsumeValueGetter;
            columnDefs[2].children.push({
                headerName: '总计',
                colId: 'totalPointOfSaleConsume',
                valueGetter: totalPointOfSaleConsumeValueGetter,
                volatile: true
            });
            pointOfSaleIds.push('totalPointOfSaleConsume');
            allColumnIds = allColumnIds.concat(pointOfSaleIds);
        });
    $scope.gridOptions = {
        columnDefs: columnDefs,
        enableColResize: true,
        defaultColDef: {
            editable: true
        },
        //angularCompileRows: true,
        rowData: null,
        onGridReady: function () {
            this.columnApi.setColumnGroupOpened('roomConsume', true);
            this.columnApi.autoSizeColumns(allColumnIds);
        },
        localeText: {
            export: '导出',
            csvExport: '导出为CSV',
            excelExport: '导出XLS',
            copy: '复制',
            paste: '粘贴',
            copyWithHeaders: '复制标题',
            toolPanel: '工具栏'
        }
    };
    $scope.range = '年';
    $scope.RoomParseReport = function (beginTime, range) {
        $scope.showRoomParseReport = true;
        webService.post('RoomParseReport', {
            date: beginTime,
            range: range
        })
            .then(function (r) {
                for (var i = 0; i < r.length; i++) {
                    var row = r[i];
                    if (row.month == '小计') {//向上翻三个算总和
                        angular.forEach(r[i - 1], function (value, key) {
                            if (key == 'month' || key == 'averageRent' || key == 'averagePrice' || key == 'revper') {
                            } else {
                                row[key] = r[i - 1][key] + r[i - 2][key] + r[i - 3][key];
                            }
                        });
                        continue;
                    }
                    for (var j = 0; j < pointOfSaleIds.length; j++) {
                        var pointOfSaleId = pointOfSaleIds[j];
                        var rowIndex = row.incomeTitle.indexOf(pointOfSaleId);
                        if (rowIndex == -1) {
                            row[pointOfSaleId] = 0;
                        } else {
                            row[pointOfSaleId] = parseFloat(row.income[rowIndex]);
                        }
                    }
                }
                $scope.gridOptions.api.setRowData(r);
            })
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
        {name: '单位充值/结算', id: 'companyPay'}
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
                var paramList = r[0].paramList;
                $scope.dailyReportFields.push({name: '项目', id: 'secondPointOfSale'});
                for (var i = 0; i < paramList.length; i++) {
                    var param = paramList[i];
                    $scope.dailyReportFields.push({name: param, id: 'paramField' + (i + 1)});
                }
                $scope.dailyReportFields.push({name: '合计', id: 'total'});
                $scope.dailyReportList = r;
                $window.open(host + "/receipt/" + r[0].reportJson.reportIndex);
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
     * 房类出租表
     */
    $scope.roomCategorySaleReport = function (beginTime, endTime) {
        var post = {};
        post.beginTime = beginTime;
        post.endTime = endTime;
        webService.post('roomCategorySaleReport', post)
            .then(function (r) {
                if (r) {
                    webService.openReport(r);
                } else {
                    messageService.setMessage({type: 'alert', content: '没有数据'});
                    popUpService.pop('message');
                }
            });
        /*document.getElementById('roomCategorySaleReport').action = host + '/roomCategorySaleReport';
         document.getElementById('roomCategorySaleReport').submit();
         angular.element("#iframeReport").fadeIn('slow');*/
    };
    /**
     * 单位营业员业务报表
     */
    /*表头*/
    $scope.saleManReportFields = [
        {id: 'name', name: '单位名称'},
        {id: 'consume', name: '消费金额', sum: 'true'}
    ];
    /*查询*/
    $scope.saleManReport = function (saleMan, beginTime, endTime) {
        var post = {};
        post.saleMan = saleMan;
        post.beginTime = beginTime;
        post.endTime = endTime;
        webService.post('saleManReport', post)
            .then(function (r) {
                $scope.saleManReportList = r;
                $scope.queryMessageSaleManReport = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss') + '  销售员:' + saleMan;
            })
    };
    /**
     * 单位欠款分析
     */
    $scope.companyDebtReportFields = [
        {name: '单位名称', id: 'company'},
        {name: '期初余额', id: 'remain'},
        {name: '本期发生额', id: 'debtGenerate'},
        {name: '本期房费', id: 'roomConsume'},
        {name: '其他', id: 'otherConsume'},
        {name: '本期回款', id: 'back'},
        {name: '期末余额', id: 'debt'}
    ];
    $scope.companyDebtReport = function (beginTime, endTime) {
        var post = {};
        post.beginTime = beginTime;
        post.endTime = endTime;
        webService.post('companyDebtReport', post)
            .then(function (r) {
                $scope.companyDebtReportData = r;
                $scope.companyDebtReportList = r.companyDebtReportRowList;
                $scope.queryMessageCompanyDebtReport = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss');
            })
    };
    /*点击查看明细*/
    $scope.companyDebtReportItemClick = function (item, id) {
        var reportJson = $scope.companyDebtReportData.reportJson;
        var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
        var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
        var query = {};
        /*分析表头*/
        switch (id) {
            case 'remain':
                var condition = 'company=' + util.wrapWithBrackets(item.company) + ' and do_time> 1990-01-31 and do_time<' + util.wrapWithBrackets(beginTime);
                popUpService.pop('popCompanyDebt', null, null, condition);
                break;
            case'debt':
                var condition = 'company=' + util.wrapWithBrackets(item.company) + ' and do_time>' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime);
                popUpService.pop('popCompanyDebt', null, null, condition);
                break;
        }
    };
}]);
