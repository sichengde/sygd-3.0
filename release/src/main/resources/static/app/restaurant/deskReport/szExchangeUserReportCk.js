App.directive('szExchangeUserReportCk', function () {
    return {
        restrict: 'E',
        controller: ['$scope','dateFilter', 'util','popUpService','webService', function ($scope,dateFilter, util,popUpService,webService) {
            $scope.exchangeUserCkReportFields = [
                {name: '币种', id: 'currency'},
                {name: '结算款', id: 'payMoney', sum: 'true'},
                {name: '预订订金', id: 'bookMoney'},
                {name: '退订金', id: 'cancelBookMoney'},
                {name: '会员充值', id: 'vipMoney'},
                {name: '抵用金额', id: 'vipDeserve'}
            ];
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
        }],
        templateUrl: function () {
            return 'restaurant/deskReport/szExchangeUserReportCk.html';
        }
    }
});