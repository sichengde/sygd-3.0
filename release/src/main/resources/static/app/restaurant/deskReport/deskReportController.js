/**
 * Created by Administrator on 2016-09-18.
 */
App.controller('deskReportController', ['$scope', 'webService', 'popUpService', 'util', 'dateFilter', 'LoginService', 'dataService', 'host', function ($scope, webService, popUpService, util, dateFilter, LoginService, dataService, host) {
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
    dataService.refreshPointOfSaleList({condition: 'module=\'餐饮\''}).then(function () {
        $scope.pointOfSaleList = dataService.getPointOfSale();
        $scope.pointOfSale = $scope.pointOfSaleList[0];
    });
    dataService.initData(['refreshExchangeUserList', 'refreshUserList', 'refreshSaleCountList'])
        .then(function () {
            $scope.exchangeUserList = dataService.getExchangeUserList();
            $scope.userList = util.objectListToString(dataService.getUserList(), 'userId');
        });
    $scope.userId = LoginService.getUser();
    $scope.exchangeUserCkReportFields = [
        {name: '币种', id: 'currency'},
        {name: '结算款', id: 'payMoney', sum: 'true'},
        {name: '预订订金', id: 'bookMoney'},
        {name: '退订金', id: 'cancelBookMoney'},
        {name: '会员充值', id: 'vipMoney'},
        {name: '抵用金额', id: 'vipDeserve'}
    ];
    $scope.deskInHistoryFields = [
        {name: '结账流水号', id: 'ckSerial', filter: 'input'},
        {name: '桌号', id: 'desk', filter: 'list'},
        {name: '来店时间', id: 'doTime', filter: 'date'},
        {name: '结账时间', id: 'doneTime', filter: 'date', desc: '0'},
        {name: '消费总额', id: 'totalPrice', sum: 'true'},
        {name: '操作员', id: 'userId', filter: 'list'},
        {name: '营业部门', id: 'pointOfSale', filter: 'list'}
    ];
    $scope.saleStreamReportFields = [
        {name: '菜品名称', id: 'foodName'},
        {name: '数量', id: 'num', sum: 'true'},
        {name: '金额', id: 'total', sum: 'true'}
    ];
    $scope.deskCategoryParseFields = [
        {name: '类别', id: 'category'},
        {name: '金额', id: 'total', sum: 'true'},
        {name: '占比', id: 'percent'}
    ];
    $scope.deskPayRichFields = [
        {name: '餐台', id: 'desk', width: '69px', filter: 'input'},
        {name: '币种', id: 'currency', width: '69px', filter: 'list'},
        {name: '额外币种信息', id: 'currencyAdd', width: '89px', filter: 'input'},
        {name: '金额', id: 'payMoney', sum: 'true', width: '69px'},
        {name: '开单时间', id: 'doTime', width: '129px', desc: '0', filter: 'date'},
        {name: '结账时间', id: 'doneTime', width: '129px', desc: '0', filter: 'date', filterInit: 'today'},
        {name: '结算序列号', id: 'ckSerial', width: '159px', filter: 'input'},
        {name: '操作员', id: 'userId', width: '69px', filter: 'list'},
        {name: '营业部门', id: 'pointOfSale', width: '109px', filter: 'list'},
        {name: '被取消', id: 'disabled', width: '69px', boolean: 'true', filter: 'list'}
    ];
    $scope.deskInHistoryFields = [
        {name: '结账流水号', id: 'ckSerial', filter: 'input'},
        {name: '桌号', id: 'desk', filter: 'list'},
        {name: '来店时间', id: 'doTime', filter: 'date'},
        {name: '结账时间', id: 'doneTime', filter: 'date', desc: '0'},
        {name: '消费总额', id: 'totalPrice', sum: 'true'},
        {name: '操作员', id: 'userId', filter: 'list'},
        {name: '营业部门', id: 'pointOfSale', filter: 'list'}
    ];
    $scope.deskProfitFields = [
        {name: '菜品', id: 'foodName'},
        {name: '数量', id: 'num'},
        {name: '总销售额', id: 'afterDiscount'},
        {name: '总成本', id: 'totalCost', filter: 'date'},
        {name: '成本毛利率', id: 'costRate'},
        {name: '销售毛利率', id: 'saleRate'}
    ];
    /*选择交班的班次*/
    $scope.chooseExchangeUser = function (r) {
        if (r.beginTime < r.endTime) {
            $scope.beginTime = util.newDateAndTime(new Date(), r.beginTime);
            $scope.endTime = util.newDateAndTime(new Date(), r.endTime);
        } else {
            $scope.beginTime = util.newDateAndTime(new Date(new Date() - 24 * 60 * 60 * 1000), r.beginTime);
            $scope.endTime = util.newDateAndTime(new Date(), r.endTime);
        }
    };
    /*选择营业部门后生成可选的类别*/
    $scope.chooseCategory = function (pointOfSale) {
        dataService.refreshSaleCountList({condition: 'first_point_of_sale=' + util.wrapWithBrackets(pointOfSale)})
            .then(function (r) {
                $scope.chooseSelectList = [];
                $scope.categoryList = util.objectListToString(r, 'name');
                var existList;
                if ($scope.category) {
                    existList = $scope.category.split(',');
                } else {
                    existList = '';
                }
                for (var i = 0; i < $scope.categoryList.length; i++) {
                    var check = existList.indexOf($scope.categoryList[i]) > -1;
                    $scope.chooseSelectList.push({name: $scope.categoryList[i], check: check});
                }
                popUpService.pop('szPopChoose', null, chooseCategoryOver, $scope.chooseSelectList);
            });
    };
    function chooseCategoryOver() {
        $scope.category = '';
        for (var i = 0; i < $scope.chooseSelectList.length; i++) {
            var obj = $scope.chooseSelectList[i];
            if (obj.check) {
                $scope.category += obj.name + ',';
            }
        }
        $scope.category = $scope.category.substring(0, $scope.category.length - 1);
    }

    /**
     * 交班审核表数据生成
     */
    $scope.exchangeUserCk = function (userId, beginTime, endTime) {
        webService.post('exchangeUserCkReport', {userId: userId, beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.exchangeUserCkReportList = r;
                /*生成交班审核表的查询信息，用来传递给jasper*/
                $scope.queryMessage = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss') + '  操作员:' + (userId ? userId : '全部');
            })
    };
    /**
     * 点击交班审核表一个项目时，显示明细
     */
    $scope.exchangeUserCkReportItemClick = function (item, id) {
        /*查询*/
        var itemWithQuery = $scope.exchangeUserCkReportList[0].reportJson;
        var user = util.wrapWithBrackets(itemWithQuery.userId);
        var beginTime = util.wrapWithBrackets(dateFilter(itemWithQuery.beginTime, 'yyyy-MM-dd HH:mm:ss'));
        var endTime = util.wrapWithBrackets(dateFilter(itemWithQuery.endTime, 'yyyy-MM-dd HH:mm:ss'));
        var currency = util.wrapWithBrackets(item.currency);
        var whereUser = '';
        if (itemWithQuery.userId) {
            whereUser = 'user_id = ' + user + ' and ';
        }
        if (id == 'payMoney') {
            var p = {condition: whereUser + 'done_time > ' + beginTime + ' and done_time< ' + endTime + ' and currency=' + currency + ' and ifnull(disabled,false)=false'};
            webService.post('deskPayGet', p)
                .then(function (r) {
                    popUpService.pop('deskPay', null, null, r);
                })
        }
        if (id == 'vipMoney') {
            var p = {condition: whereUser + 'do_time > ' + beginTime + ' and do_time< ' + endTime + ' and currency=' + currency};
            webService.post('vipIntegrationGet', p)
                .then(function (r) {
                    popUpService.pop('vipDetail', null, null, r);
                })
        }
    };
    /**
     * 提交销售流水报表统计
     */
    var backUpQueryMessage = {};
    $scope.SaleStreamReport = function (pointOfSale, beginTime, endTime, category) {
        backUpQueryMessage.pointOfSale = pointOfSale;
        backUpQueryMessage.beginTime = beginTime;
        backUpQueryMessage.endTime = endTime;
        backUpQueryMessage.category = category;
        webService.post('SaleStreamReport', {
            pointOfSale: pointOfSale,
            beginTime: beginTime,
            endTime: endTime,
            category: category
        })
            .then(function (r) {
                $scope.saleStreamReportList = r.saleStreamRowList;
                $scope.saleStreamRemark = r.categoryParse;
                webService.openReport(r.reportIndex);
            })
    };
    /*点击流水数量弹出明细,注意：弹出的明细无法区分类别，也就是同名菜*/
    $scope.SaleStreamReportItemClick = function (item, id) {
        var user = util.wrapWithBrackets(backUpQueryMessage.userId);
        var foodName = util.wrapWithBrackets(item.foodName);
        var beginTime = util.wrapWithBrackets(dateFilter(backUpQueryMessage.beginTime, 'yyyy-MM-dd HH:mm:ss'));
        var endTime = util.wrapWithBrackets(dateFilter(backUpQueryMessage.endTime, 'yyyy-MM-dd HH:mm:ss'));
        var condition='done_time>' + beginTime + ' and done_time<' + endTime+' and food_name='+foodName;
        if(backUpQueryMessage.userId&&backUpQueryMessage.userId!=''){
            condition+=' and user_id = ' + user;
        }
        var p = {condition: condition};
        webService.post('deskDetailHistoryGet', p)
            .then(function (r) {
                popUpService.pop('deskDetailHistory', null, null, r);
            })
    };
    /**
     * 提交类别分析报表
     */
    $scope.ckCategoryParseReport = function (pointOfSale, beginTime, endTime) {
        webService.post('deskCategoryParseReport', {pointOfSale: pointOfSale, beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.deskCategoryParseList = r;
                $scope.queryMessageDeskCategoryParse = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss') + ' 营业部门:' + pointOfSale;
            })
    };
    /**
     * 提交毛利率分析表
     */
    $scope.deskProfitReport = function (pointOfSale, beginTime, endTime, category) {
        webService.post('deskProfitReport', {
            pointOfSale: pointOfSale,
            beginTime: beginTime,
            endTime: endTime,
            category: category
        })
            .then(function (r) {
                $scope.deskProfitList = r;
                $scope.queryMessageDeskProfit = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss') + ' 营业部门:' + pointOfSale;
            })
    };
    /**
     * 提交账单分析表
     */
    $scope.deskInHistoryParseReport = function (pointOfSale, beginTime, endTime) {
        webService.post('deskInHistoryParseReport', {pointOfSale: pointOfSale, beginTime: beginTime, endTime: endTime})
            .then(function (r) {
                $scope.deskInHistoryParseList = r.fieldTemplateList;
                $scope.deskInHistoryParseFields = r.headFieldList;
                $scope.deskInHistoryParseRemark = r.remark;
                $scope.queryMessageDeskInHistoryParse = dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss') + ' 至 ' + dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss') + ' 营业部门:' + pointOfSale;
            })
    }
}]);