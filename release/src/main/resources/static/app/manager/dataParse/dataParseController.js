App.controller('dataParseController', ['$scope', 'webService', 'dataService', 'util', 'dateFilter', 'popUpService', 'echartService', function ($scope, webService, dataService, util, dateFilter, popUpService, echartService) {
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
    var postBeginTime;
    var postEndTime;
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
                $scope.roomCategorySaleRemark=r.remark;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
                echartService.generateChartCompare(r.roomCategoryRowList, r.roomCategoryRowHistoryList, 'category', 'totalConsume');
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
                $scope.guestSourceParseRemark=r.remark;
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
        {id: 'company', name: '单位名称',width:'300px'},
        {id: 'totalRoom', name: '总订房数',width:'100px'},
        {id: 'bookedRoom', name: '已开房数',width:'100px'},
        {id: 'mark', name: '到达率',width:'100px'}
    ];
    $scope.companyBookParseReport = function (beginTime, endTime) {
        webService.post('companyBookParse', {beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.companyBookParseList = r;
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd');
            })
    };
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
    $scope.initHotelParse = function () {
        webService.get('hotelParse')
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