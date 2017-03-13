App.controller('dataParseController', ['$scope', 'webService', 'dataService', 'util', 'dateFilter', 'popUpService', 'echartService', 'fieldService', function ($scope, webService, dataService, util, dateFilter, popUpService, echartService, fieldService) {
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
    var postBeginTime;
    var postEndTime;
    /**
     * 客房经营状况
     */
    var localeText = {
        export: '导出',
        csvExport: '导出为CSV',
        excelExport: '导出XLS',
        copy: '复制',
        paste: '粘贴',
        copyWithHeaders: '复制标题',
        toolPanel: '工具栏'
    }
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
                    openByDefault: true,
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
                var guestSource = guestSourceList[i].guestSource;
                var countCategory = guestSourceList[i].countCategory;
                if (countCategory != lastCategory) {
                    if (lastCategory != '') {
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
                    columnDefs[2].children[0].children.push(guestSourceCategory);
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
            columnDefs[2].children[0].children.push({
                headerName: '总计',
                colId: 'totalRoomConsume',
                valueGetter: totalRoomConsumeValueGetter
            });
            pointOfSaleIds.push('totalRoomConsume');
            /*统计其他营业部门*/
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
            /*this.columnApi.setColumnGroupOpened('roomConsume', true);*/
            /*this.columnApi.autoSizeColumns(allColumnIds);*/
            /*this.api.sizeColumnsToFit();*/
        },
        localeText: localeText
    };
    $scope.range = '年';
    $scope.showRoomParseReport = false;
    $scope.RoomParseReport = function (beginTime, range) {
        $scope.showRoomParseReport = true;
        webService.post('RoomParseReport', {date: beginTime, range: range})
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
                $scope.gridOptions.api.setColumnDefs(columnDefs);
                $scope.gridOptions.api.setRowData(r);
                var allColumnIds = [];
                getColumnDef(columnDefs, allColumnIds);
                $scope.gridOptions.columnApi.autoSizeColumns(allColumnIds);
                $scope.gridOptions.api.ensureColumnVisible('totalPointOfSaleConsume');
                $scope.gridOptions.columnApi.autoSizeColumns(allColumnIds);
            })
    };
    function getColumnDef(defs, allColumnIds) {
        defs.forEach(function (columnDef) {
            if (columnDef.children) {
                getColumnDef(columnDef.children, allColumnIds)
            } else {
                if (columnDef.field) {
                    allColumnIds.push(columnDef.field);
                } else {
                    allColumnIds.push(columnDef.colId);
                }
            }
        });
    }

    $scope.RoomParseReportExport = function () {
        $scope.gridOptions.api.exportDataAsExcel({
            columnGroups: true,
            skipGroups: false
        });
    };
    /*房类分析*/
    $scope.roomCategorySaleFields = [
        {name: '房类', id: 'category'},
        {name: '总数', id: 'total'},
        {name: '空房', id: 'empty'},
        {name: '维修', id: 'repair'},
        {name: '自用', id: 'self'},
        {name: '备用', id: 'backUp'},
        {name: '出租', id: 'rent'},
        {name: '全日房', id: 'allDay'},
        {name: '加收房', id: 'addDay'},
        {name: '小时房', id: 'hourRoom'},
        {name: '总计房费', id: 'totalConsume'},
        {name: '平均房价', id: 'averagePrice'},
        {name: 'REVPAR', id: 'revper'},
        {name: '出租率', id: 'averageRent'}
    ];
    $scope.roomCategorySaleReport = function (beginTime, endTime) {
        postBeginTime = beginTime;
        postEndTime = endTime;
        webService.post('roomCategorySaleReport', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.roomCategorySaleList = r.roomCategoryRowList;
                $scope.roomCategorySaleRemark = r.remark;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
                echartService.generateChartCompare(r.roomCategoryRowList, r.roomCategoryRowHistoryList, 'category', 'totalConsume');
            })
    };
    /*发生额与结算款*/
    $scope.debtAndPayReport = function (beginTime) {
        webService.post('debtAndPayReport', {beginTime: beginTime})
            .then(function (r) {

            })
    };
    /*客源人数分析*/
    $scope.guestSourceParseFields = [
        {name: '客源', id: 'guestSource'},
        {name: '开房', id: 'num'},
        {name: '平均消费', id: 'averageConsume'},
        {name: '消费总计', id: 'totalConsume'}
    ];
    $scope.guestSourceParseReport = function (beginTime, endTime) {
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
        {name: '期初余额', id: 'remain'},
        {name: '期间发生额', id: 'debtGenerate'},
        {name: '期间回款', id: 'back'},
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
    /*单位挂账明细*/
    $scope.companyDebtRichFields = fieldService.getCompanyDebtRichFields();
    /*$scope.companyDebtDetailGet = function (paid, beginTime, endTime) {
     var post = {};
     post.beginTime = beginTime;
     post.endTime = endTime;
     post.paid = paid;
     webService.post('companyDebtDetailGet', post)
     .then(function (r) {
     var rowData=[];
     for (var i = 0; i < r.length; i++) {
     var company = r[i];
     var companyDebtList;
     if(company.CompanyDebtIntegration){
     companyDebtList=company.CompanyDebtIntegration;
     }else if(company.CompanyDebt){
     companyDebtList=company.CompanyDebt;
     }
     if(companyDebtList){//有明细
     for (var j = 0; j < companyDebtList.length; j++) {
     var companyDebt = companyDebtList[j];

     }
     }
     }
     $scope.showCompanyDebtDetailReport = true;
     $scope.companyDebtReportData = r;
     $scope.companyDebtReportList = r.companyDebtReportRowList;
     $scope.queryMessageCompanyDebtReport = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss');
     })
     };*/
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
    $scope.hotelParseReport=function (beginTime) {
        webService.post('hotelParse',{beginTime:beginTime})
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