App.controller('dataParseController', ['$scope', 'webService', 'dataService', 'util', 'dateFilter', 'popUpService', 'echartService', 'fieldService', 'agGridService', function ($scope, webService, dataService, util, dateFilter, popUpService, echartService, fieldService, agGridService) {
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
    /*房类分析*/
    $scope.roomCategorySaleFields = [
        {name: '房类', id: 'category'},
        {name: '总数', id: 'total'},
        {name: '空房', id: 'empty'},
        {name: '维修', id: 'repair'},
        {name: '自用', id: 'self'},
        {name: '备用', id: 'backUp'},
        {name: '出租', id: 'rentFake', exp: 'allDayRoom*1+addRoom*1+hourRoom*1+nightRoom*1'},
        {name: '全日房', id: 'allDayRoom'},
        {name: '加收房', id: 'addRoom'},
        {name: '小时房', id: 'hourRoom'},
        {name: '凌晨房', id: 'nightRoom'},
        {name: '全日房费', id: 'allDayRoomConsume'},
        {name: '加收房费', id: 'addRoomConsume'},
        {name: '小时房费', id: 'hourRoomConsume'},
        {name: '凌晨房费', id: 'nightRoomConsume'},
        {
            name: '总计房费',
            id: 'totalConsume',
            exp: 'allDayRoomConsume*1+addRoomConsume*1+hourRoomConsume*1+ nightRoomConsume*1'
        },
        {
            name: '平均房价',
            id: 'ava',
            exp: '((allDayRoomConsume*1+nightRoomConsume*1)/(allDayRoom*1+nightRoom*1)).toFixed(2)'
        },
        {name: 'REVPAR', id: 'REVPAR', exp: '((allDayRoomConsume*1+nightRoomConsume*1)/totalReal*1).toFixed(2)'},
        {name: '出租率', id: 'averageRent', exp: '(rent/totalReal).toFixed(2)'}
    ];
    $scope.roomCategorySaleReport = function (beginTime, endTime) {
        webService.post('roomCategorySaleReport', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.roomCategorySaleList = r.roomStateReportList;
                $scope.roomCategorySaleRemark = r.remark;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
            })
    };
    /*发生额与结算款*/
    $scope.debtAndPayFields = [
        {name: '营业部门', id: 'pointOfSale'},
        {name: '期初未结', id: 'undoneBefore'},
        {name: '期间发生', id: 'debt'},
        {name: '期间结算', id: 'debtPay'},
        {name: '转单位', id: 'toCompany'},
        {name: '转哑房', id: 'lost'},
        {name: '期末未结', id: 'undoneLast'}
    ];
    $scope.debtAndPayReport = function (beginTime, endTime) {
        webService.post('debtAndPayReport', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.debtAndPayList = r.debtAndPayRowList;
                $scope.companyDebt = r.companyDebt;
                $scope.companyPay = r.companyPay;
                $scope.vipPay = r.vipPay;
                $scope.debtAndPayRemark = '单位回款:' + $scope.companyDebt + ',单位回款抵用:' + $scope.companyPay + ',会员充值:' + $scope.vipPay;
                $scope.debtAndPayQueryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
            })
    };
    /**
     * 客房经营状况
     */
    $scope.setShowRoomParseReportFalse=function () {
        $scope.showRoomParseReport=false;
    };
    var roomParseColumnDefs = [
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
                {
                    headerName: "接待人次",
                    marryChildren: true,
                    groupId: 'guestIn',
                    openByDefault: false,
                    children: [
                        {
                            headerName:'总计',
                            field:'guestNum'
                        }
                    ]
                },
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
                    openByDefault: false,
                    children: []
                }
            ]
        }
    ];
    var pointOfSaleIds = [];
    dataService.initData(['refreshPointOfSaleList', 'refreshGuestSourceList'], [{condition: 'module=\'接待\''}, {orderByList: ['countCategory']}])
        .then(function () {
            /*统计房费客源明细*/
            var guestSourceList = dataService.getGuestSourceList();
            var totalRoomConsumeValueGetter = '';
            var lastCategory = '';
            var totalStr = '';
            var guestSourceCategory;
            for (var i = 0; i < guestSourceList.length; i++) {
                /*接待人次按客源统计*/
                roomParseColumnDefs[1].children[3].children.push({
                    headerName: guestSourceList[i].guestSource,
                    field:'num'
                });
                var guestSource = guestSourceList[i].guestSource;
                var countCategory = guestSourceList[i].countCategory;
                if (countCategory !== lastCategory) {
                    if (lastCategory !== '') {
                        totalStr = totalStr.substring(0, totalStr.length - 1);
                        guestSourceCategory.children.push({
                            headerName: '总计',
                            colId: '总计',
                            valueGetter: totalStr
                        });
                        totalStr = '';
                    }
                    guestSourceCategory = {
                        headerName: countCategory,
                        marryChildren: true,
                        groupId: countCategory,
                        columnGroupShow: 'open',
                        openByDefault: true,
                        children: []
                    };
                    roomParseColumnDefs[2].children[0].children.push(guestSourceCategory);
                    lastCategory = countCategory;
                }
                guestSourceCategory.children.push({
                    headerName: guestSource,
                    colId: guestSource,
                    field: guestSource,
                    columnGroupShow: 'open'
                });
                pointOfSaleIds.push(guestSourceList[i].guestSource);
                totalRoomConsumeValueGetter += 'getValue("' + guestSourceList[i].guestSource + '")+';
                totalStr += 'getValue("' + guestSourceList[i].guestSource + '")+';
            }
            totalStr = totalStr.substring(0, totalStr.length - 1);
            guestSourceCategory.children.push({
                headerName: '总计',
                colId: '总计',
                valueGetter: totalStr
            });
            totalRoomConsumeValueGetter = totalRoomConsumeValueGetter.substring(0, totalRoomConsumeValueGetter.length - 1);
            roomParseColumnDefs[2].children[0].children.push({
                headerName: '总计',
                colId: 'totalRoomConsume',
                valueGetter: totalRoomConsumeValueGetter
            });
            pointOfSaleIds.push('totalRoomConsume');
            /*统计其他营业部门*/
            var secondPointOfSale = dataService.getPointOfSale()[0].secondPointOfSale.split(' ');
            var totalPointOfSaleConsumeValueGetter = '';
            for (var i = 0; i < secondPointOfSale.length; i++) {
                if (secondPointOfSale[i] === '房费') {
                    continue;
                }
                roomParseColumnDefs[2].children.push({headerName: secondPointOfSale[i], field: secondPointOfSale[i]});
                roomParseColumnDefs[2].children.push({headerName: '次数', field: secondPointOfSale[i] + '次数'});
                totalPointOfSaleConsumeValueGetter += 'getValue("' + secondPointOfSale[i] + '")+';
                pointOfSaleIds.push(secondPointOfSale[i]);
                pointOfSaleIds.push(secondPointOfSale[i] + '次数');
            }
            /*未定义*/
            roomParseColumnDefs[2].children.push({headerName: "未定义", field: "未定义"});
            roomParseColumnDefs[2].children.push({headerName: '次数', field: '未定义次数'});
            totalPointOfSaleConsumeValueGetter += 'getValue("未定义")+';
            pointOfSaleIds.push("未定义");
            pointOfSaleIds.push('未定义次数');
            totalPointOfSaleConsumeValueGetter += totalRoomConsumeValueGetter;
            roomParseColumnDefs[2].children.push({
                headerName: '总计',
                colId: 'totalPointOfSaleConsume',
                valueGetter: totalPointOfSaleConsumeValueGetter,
                volatile: true
            });
            pointOfSaleIds.push('totalPointOfSaleConsume');
        });
    $scope.roomParseGridOptions = {
        columnDefs: roomParseColumnDefs,
        enableColResize: true,
        defaultColDef: {
            editable: true
        },
        //angularCompileRows: true,
        rowData: null,
        onGridReady: function () {
            /*this.columnApi.setColumnGroupOpened('roomConsume', true);*/
            /*this.columnApi.autoSizeColumns(allColumnIds);*/
            /*this.api.sizeColumnsToFit();*/
        },
        localeText: agGridService.getLocalText
    };
    $scope.range = '年';
    $scope.showRoomParseReport = false;
    $scope.RoomParseReport = function (beginTime, range) {
        $scope.showRoomParseReport = true;
        webService.post('roomParseReport', {date: beginTime, range: range})
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
                ;
                $scope.roomParseGridOptions.api.setColumnDefs(roomParseColumnDefs);
                $scope.roomParseGridOptions.api.setRowData(r);
                var allColumnIds = [];
                agGridService.getColumnDef(roomParseColumnDefs, allColumnIds);
                $scope.roomParseGridOptions.columnApi.autoSizeColumns(allColumnIds);
                $scope.roomParseGridOptions.api.ensureColumnVisible('totalPointOfSaleConsume');
                $scope.roomParseGridOptions.columnApi.autoSizeColumns(allColumnIds);
            })
    };

    $scope.exportAgGrid = function (param) {
        param.api.exportDataAsExcel({
            columnGroups: true,
            skipGroups: false
        });
    };
    /*客源人数分析*/
    $scope.guestSourceParseFields = [
        {name: '客源', id: 'guestSource'},
        {name: '开房', id: 'num'},
        {name: '平均消费', id: 'averageConsume'},
        {name: '消费总计', id: 'totalConsume'}
    ];
    $scope.guestSourceParseReport = function (beginTime, endTime) {
        /*有问题，不包括在店客人，应该包括*/
        webService.post('guestSourceParseReport', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.guestSourceParseList = r.guestParseRowList;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
                $scope.guestSourceParseRemark = r.remark;
                echartService.generateChartCompare(r.guestParseRowList, r.guestParseRowListHistory, 'guestSource', 'totalConsume');
            })
    };
    /*客源房类分析*/
    $scope.guestSourceRoomCategoryParseFields = [
        {name: '客源', id: 'guestSource'},
        {name: '房类', id: 'roomCategory'},
        {name: '房费总计', id: 'consume'}
    ];
    $scope.guestSourceRoomCategoryParseReport = function (beginTime, endTime) {
        webService.post('guestSourceRoomCategoryParseReport', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.guestSourceRoomCategoryParseList = r.guestSourceRoomCategoryRowList;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
            })
    };
    /*单位预订到达率*/
    $scope.companyBookParseFields = [
        {id: 'company', name: '单位名称', width: '300px'},
        {id: 'totalRoom', name: '总订房数', width: '100px'},
        {id: 'bookedRoom', name: '已开房数', width: '100px'},
        {id: 'mark', name: '到达率', width: '100px'}
    ];
    $scope.companyBookParseReport = function (beginTime, endTime) {
        webService.post('companyBookParse', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.companyBookParseList = r;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
            })
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
        {name: '期初余额', id: 'remain', sum: 'true'},
        {name: '期间发生额', id: 'debtGenerate', sum: 'true'},
        {name: '期间回款', id: 'back', sum: 'true'},
        {name: '期末余额', id: 'debt', sum: 'true'}
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
    /*单位挂账明细*/
    $scope.companyDebtRichFields = fieldService.getCompanyDebtRichFields();
    /*全店收入表*/
    $scope.hotelParseFields = [
        {name: '营业部门', id: 'pointOfSale'},
        {name: '当日累计', id: 'dayTotal'},
        {name: '当月累计', id: 'monthTotal'},
        {name: '当年累计', id: 'yearTotal'},
        {name: '当日历史同期', id: 'dayHistoryTotal'},
        {name: '当月历史同期', id: 'monthHistoryTotal'},
        {name: '当年历史同期', id: 'yearHistoryTotal'}
    ];
    $scope.hotelParseReport = function (beginTime) {
        webService.post('hotelParse', {beginTime: beginTime})
            .then(function (r) {
                $scope.hotelParseList = r;
            })
    };
    /*全店收入表点击之后显示图表*/
    $scope.hotelParseItemClick = function (item, id) {
        var p = [];
        if (id == 'monthTotal' || id == 'monthHistoryTotal') {
            p.push('month');
        } else if (id == 'yearTotal' || id == 'yearHistoryTotal') {
            p.push('year');
        }
        /*查询*/
        if (item.module == '接待') {
            if (!item.fatherFirstPointOfSale) {//一级营业部门
                p.push(null);
            } else {
                p.push(item.pointOfSale);
            }
            if (p.length == 2) {//两个参数都填写完全了
                webService.post('hotelParseLine', p)
                    .then(function (r) {
                        var list1 = r.hotelParseLineRowList;
                        var list2 = r.hotelParseLineRowListHistory;
                        var title;
                        if (list1.length > 40) {
                            title = '全年' + item.pointOfSale + '发生额对比';
                        } else {
                            title = '全月' + item.pointOfSale + '发生额对比';
                        }
                        echartService.generateChartTimeLine(title, list1, list2, true);
                    })
            }
        } else if (item.module == '餐饮') {
            if (!item.fatherFirstPointOfSale) {//一级营业部门
                p.push(item.pointOfSale);
                p.push(null);
            } else {
                p.push(item.fatherFirstPointOfSale);
                p.push(item.pointOfSale);
            }
            if (p.length == 3) {//三个参数都填写完全了
                webService.post('hotelParseLineCK', p)
                    .then(function (r) {
                        var list1 = r.hotelParseLineRowList;
                        var list2 = r.hotelParseLineRowListHistory;
                        var title;
                        if (list1.length > 40) {
                            title = '全年' + '-' + item.pointOfSale + '发生额对比';
                        } else {
                            title = '全月' + '-' + item.pointOfSale + '发生额对比';
                        }
                        echartService.generateChartTimeLine(title, list1, list2, true);
                    })
            }
        }
    };
    /*房扫分析*/
    $scope.cleanRoomFields = [
        {name: '姓名', id: 'userId'},
        {name: '在住房', id: 'in'},
        {name: '走客房', id: 'leave'}
    ];
    $scope.cleanRoomParse = function (beginTime, endTime) {
        webService.post('cleanRoomParse', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.cleanRoomList = [];
                var lastRow = {};
                for (var i = 0; i < r.length; i++) {
                    var row = r[i];
                    if (lastRow.userId == row.userId) {//补充
                        if (row.category == '走客房') {
                            lastRow.leave = row.num;
                        } else if (row.category == '在住房') {
                            lastRow.in = row.num;
                        }
                    } else {//新增
                        var add = {userId: row.userId};
                        if (row.category == '走客房') {
                            add.leave = row.num;
                        } else if (row.category == '在住房') {
                            add.in = row.num;
                        }
                        lastRow = add;
                        $scope.cleanRoomList.push(add);
                    }
                }
                var xAxisData = [];
                var series = [];
                var seriesIn = [];
                var seriesLeave = [];
                for (var i = 0; i < $scope.cleanRoomList.length; i++) {
                    var cleanRoom = $scope.cleanRoomList[i];
                    xAxisData.push(cleanRoom.userId);
                    seriesIn.push(cleanRoom.in);
                    seriesLeave.push(cleanRoom.leave);
                }
                series.push({name: '在住房', type: 'bar', data: seriesIn});
                series.push({name: '走客房', type: 'bar', data: seriesLeave});
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
                echartService.generateChartStd('房扫情况统计', ['在住房', '走客房'], xAxisData, series);
            })
    };
    /*库存盘点*/
    $scope.storageRemainFields = [
        {name: '仓库', id: 'house', filter: 'list'},
        {name: '货品', id: 'cargo', filter: 'input'},
        {name: '单位', id: 'unit'},
        {name: '单价', id: 'price'},
        {name: '余量', id: 'remain'},
        {name: '总金额', id: 'total', exp: 'price*remain', sum: 'true'}
    ];

}]);