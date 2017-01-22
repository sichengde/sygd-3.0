/**
 * Created by Administrator on 2016/9/11 0011.
 * 结算分单币种选择
 * 父页面如果设置分单按钮，则可以分单，否则无法分单
 * 父页面调用方法：
 * $scope.currencyPayList = [];//sz-pay
 * $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
 * $scope.currencyPayList[0].money=10;//设置总金额，有时候也可以没有
 * $scope.currencyList=xxx 自定义币种数组
 */
App.directive('szPay', function () {
    return {
        restrict: 'E',
        controller: ['$scope', 'dataService', 'util', 'roomFilter', 'messageService', 'dateFilter', 'popUpService', function ($scope, dataService, util, roomFilter, messageService, dateFilter, popUpService) {
            dataService.initData(['refreshCurrencyList', 'refreshCompanyList', 'refreshVipList', 'refreshRoomList', 'refreshCompanyLordList', 'refreshFreemanList'], [{condition: 'check_out=1'}, {condition: 'if_debt=\'y\''}, {condition: 'state=\'正常\''}])
                .then(function () {
                    /*可供选择的币种*/
                    if (!$scope.currencyList) {
                        $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
                    }
                    $scope.currencyPayList[0].currency = $scope.currencyList[0];
                    /*初始化可供选择的单位结账数组*/
                    $scope.companyList = util.objectListToString(dataService.getCompanyList(), 'name');
                    $scope.companyLordList = dataService.getCompanyLordList();
                    /*宴请数组*/
                    $scope.freemanList = util.objectListToString(dataService.getFreemanList(), 'freeman');
                    /*转房客时需要用的到房号，离店结算的话考虑不能有本房间*/
                    var roomList = dataService.getRoomList();
                    if ($scope.excludeRoom) {
                        util.deleteFromArray(roomList, 'roomId', $scope.excludeRoom);
                    }
                    $scope.roomList = roomFilter(roomList, ['散客房', '团队房'], '全部');
                });
            /*读会员卡*/
            $scope.vipScale = dataService.getOtherParamMapValue('积分比率');//先确定好积分比率
            $scope.readCard = function (r) {
                var vip = util.getValueByField(dataService.getVipList(), 'idNumber', r.idNumber);
                /*判断一下有效日期*/
                if (vip) {
                    dataService.refreshTimeNow()
                        .then(function (time) {
                            if (vip.remainTime < time && vip.remainTime) {//过期不能刷
                                messageService.setMessage({
                                    type: 'error',
                                    content: '有效日期为:' + dateFilter(vip.remainTime, 'yyyy-MM-dd') + ',当前时间:' + dateFilter(time, 'yyyy-MM-dd')
                                });
                                popUpService.pop('message');
                            } else if (vip.remain < r.money && $scope.vipScale * vip.score < r.money) {//余额不足不能刷
                                messageService.setMessage({
                                    type: 'error',
                                    content: '余额为:' + vip.remain + ',积分可抵:' + $scope.vipScale * vip.score + ',当前消费:' + r.money
                                });
                                popUpService.pop('message');
                            } else {
                                r.memberInfo = vip;
                                r.currencyAdd1 = r.memberInfo.vipNumber;
                                r.currencyAdd = r.currencyAdd1 + ' 余额';
                                r.currencyAdd2 = '余额';
                            }
                        })
                }
            };
            /*选择会员积分还是余额*/
            $scope.vipPayCategory = function (r) {
                r.currencyAdd = r.currencyAdd1 + ' ' + r.currencyAdd2;
            };
            /*选择房间之后显示余额*/
            $scope.changeChooseRoom = function (r) {
                var room=util.getValueByField($scope.roomList,'roomId',r.currencyAdd);
                if (room.checkInGroup) {
                    r.chooseRoomRemain = room.checkInGroup.deposit-room.checkInGroup.consume;
                } else {
                    r.chooseRoomRemain = room.checkIn.deposit-room.checkIn.consume;
                }
            };
            /*选择单位之后重新筛选可签单人*/
            $scope.changeCompany = function (r) {
                r.companyLordListShow = util.objectListToString(util.getValueListByField($scope.companyLordList, 'company', r.currencyAdd1), 'name');
            };
            /*选择单位签单人之后拼接到单位字符串上*/
            $scope.changeCompanyLord = function (r) {
                r.currencyAdd = r.currencyAdd1 + ' ' + r.currencyAdd2;
            };
            /*选择宴请后整合1,2两条数据*/
            $scope.integration = function (r) {
                r.currencyAdd = util.isNullSetString(r.currencyAdd1) + ' ' + util.isNullSetString(r.currencyAdd2);
            };
            /*增加一条分单币种*/
            $scope.manyCurrency = function () {
                $scope.currencyPayList.push({});
            };
            /*删除一条分单币种*/
            $scope.deleteCurrency = function (r) {
                util.deleteFromArray($scope.currencyPayList, null, r);
            };
            /*双击计算出剩余分单金额*/
            $scope.calculateRemainMoney = function (r) {
                var total = 0;//其他总和
                angular.forEach($scope.currencyPayList, function (currency) {
                    if (currency != r) {
                        total += currency.money * 1;
                    }
                });
                r.money = $scope.totalConsume - total;
            };
            /*计算找零补交信息，跟后台算法一样*/
            $scope.$watch('currencyPayList', function () {
                if (!$scope.debtShowList) {//没有账务列表，例如商品零售，餐饮
                    return;
                }
                $scope.depositMessage = '';//找零补交信息
                var currencyPayTemp = angular.copy($scope.currencyPayList);
                for (var i = 0; i < currencyPayTemp.length; i++) {
                    var obj = currencyPayTemp[i];
                    obj.money = -obj.money;
                }
                for (i = 0; i < $scope.debtShowList.length; i++) {
                    var debtShow = $scope.debtShowList[i];
                    if (debtShow.deposit > 0 && debtShow.remark != '已退') {
                        var haveSame;
                        for (var j = 0; j < currencyPayTemp.length; j++) {
                            var currencyPay = currencyPayTemp[j];
                            if (currencyPay.currency == debtShow.currency) {//相同的币种结账方式
                                currencyPay.money = debtShow.deposit + currencyPay.money;
                                haveSame = true;
                            }
                            if (!haveSame) {
                                $scope.depositMessage += "找回金额：" + debtShow.deposit + "(" + debtShow.currency + ") ";
                            }
                        }
                    }
                }
                for (i = 0; i < currencyPayTemp.length; i++) {
                    var currencyPayRemain = currencyPayTemp[i];
                    if (currencyPayRemain.money > 0) {
                        $scope.depositMessage += "找回金额：" + currencyPayRemain.money + "(" + currencyPayRemain.currency + ") ";
                    }
                    if (currencyPayRemain.money < 0) {
                        $scope.depositMessage += "补交金额：" + -currencyPayRemain.money + "(" + currencyPayRemain.currency + ")";
                    }
                }
            }, true);
        }],
        templateUrl: function () {
            return '../directive/szPay.html';
        }
    }
});