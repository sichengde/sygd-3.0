App.directive('szExchangeUserReportJq', function () {
    return {
        restrict: 'E',
        controller: ['$scope','dateFilter', 'util','popUpService','webService', function ($scope,dateFilter, util,popUpService,webService) {
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
            $scope.exchangeUserReportItemClick = function (item, id) {
                var reportJson = $scope.reportJson;
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
                var reportJson = $scope.reportJson;
                var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
                var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
                var userId = reportJson.userId;
                var p = {condition: 'category=\'杂单\' and do_time>' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime)};
                if(userId){
                    p.condition+=' and user_id='+ util.wrapWithBrackets(userId);
                }
                webService.post('debtIntegrationGet', p)
                    .then(function (r) {
                        popUpService.pop('debtDetail', null, null, r);
                    })
            };
            $scope.moneyOutDetail = function () {
                var reportJson = $scope.reportJson;
                var beginTime = dateFilter(reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss');
                var endTime = dateFilter(reportJson.endTime, 'yyyy-MM-dd HH:mm:ss');
                var userId = reportJson.userId;
                var p = {condition: 'category=\'冲账\' and do_time>' + util.wrapWithBrackets(beginTime) + ' and do_time<' + util.wrapWithBrackets(endTime)};
                if(userId){
                    p.condition+=' and user_id='+ util.wrapWithBrackets(userId);
                }
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
        }],
        templateUrl: function () {
            return 'reception/report/szExchangeUserReportJq.html';
        }
    }
});