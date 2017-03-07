/**
 * Created by Administrator on 2016-04-26.
 * 离店结算
 */
App.controller('GuestOutController', ['$scope', 'util', 'dataService', 'receptionService', 'LoginService', 'popUpService', 'webService', 'roomFilter', 'host', 'doorInterfaceService', 'protocolService', 'guestOutService', '$filter', 'messageService', '$q', function ($scope, util, dataService, receptionService, LoginService, popUpService, webService, roomFilter, host, doorInterfaceService, protocolService, guestOutService, $filter, messageService, $q) {
    var guestOut = {};//用于提交的对象集合
    $scope.debtList = [];//消费明细数组
    var chooseRoom = [];//结账房间（在店户籍数组）
    var chooseBed = [];//结账宾客（在店宾客数组）
    var debtAddList = [];//加收房租数组
    /*获取房间对象*/
    $scope.room = receptionService.getChooseRoom();
    /*初始化分单数组，默认不分担，值为1，只有一个币种*/
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.excludeRoom = $scope.room.roomId;
    $scope.enableCancelDebtAdd = dataService.getOtherParamMapValue('允许不加收房租') == 'y';
    $scope.ifDebtAdd = true;
    guestOutService.initData($scope.room)
        .then(function () {
            $scope.debtList = dataService.getDebtList();
            $scope.debtShowList = angular.copy(dataService.getDebtList());
            $scope.nowTime = util.newDateNow(dataService.getTimeNow());
            /*分析加收房租数组*/
            if (($scope.room.checkIn && $scope.room.checkIn.vipNumber) || ($scope.room.checkInGroup && $scope.room.checkInGroup.vipNumber)) {//如果是会员，开始分析加收房租方式
                var roomPriceAddList = dataService.getRoomPriceAddList();
                $scope.roomPriceAddList = [];
                /*看看会员走的是全部还是*/
                for (var i = 0; i < roomPriceAddList.length; i++) {
                    var roomPriceAdd = roomPriceAddList[i];
                    if (roomPriceAdd.vip) {
                        $scope.roomPriceAddList.push(roomPriceAdd);
                    }
                }
                /*如果没有定义任何会员加收房价*/
                if ($scope.roomPriceAddList.length == 0) {
                    $scope.roomPriceAddList = dataService.getRoomPriceAddList();
                }
            } else {
                $scope.roomPriceAddList = dataService.getRoomPriceAddList();
            }
            $scope.checkInList = $filter('orderBy')(dataService.getCheckInList(), 'roomId');
            chooseRoom = angular.copy($scope.checkInList);
            util.addAttributeAnyWay($scope.checkInList, 'hover', 'hover');
            if ($scope.room.checkInGroup) {
                $scope.checkInGroup = dataService.getCheckInGroupList()[0];
                $scope.groupAccount = $scope.room.checkInGroup.groupAccount;
            } else {
                $scope.checkIn = $scope.checkInList[0];
                $scope.checkInGuestList = dataService.getCheckInGuestList();
                $scope.checkInGuestName = util.objectListToString($scope.checkInGuestList, 'name').join();
            }
            chooseBed = util.objectListToString($scope.checkInGuestList, 'bed');
            /*先都设置为hover*/
            util.addAttributeAnyWay($scope.checkInGuestList, 'hover', 'hover');
            $scope.calculateDebtAdd();//一次计算加收房租，不允许二次修改
            /*$scope.ifRoomPriceAdd = true;*/
        });
    /**
     * 界面交互的方法
     */
    /*选择部分房间是否参与结算*/
    $scope.toggleRoom = function (r, shiftKey) {
        if (!$scope.groupAccount) {
            return;
        }
        if (shiftKey) {
            var j = $scope.checkInList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                var obj = $scope.checkInList[i];
                if (util.getValueByField(chooseRoom, 'roomId', obj.roomId)) {
                    break;
                } else {
                    $scope.toggleRoom(obj);
                }
            }
        } else {
            if (util.deleteFromArray(chooseRoom, 'roomId', r.roomId)) {
                /*修改样式*/
                r.hover = null;
            } else {
                chooseRoom.push(r);
                r.hover = 'hover';
            }
        }
        /*最后分析查询条件，重新获取账务数据*/
        var p = {condition: 'group_account=' + util.wrapWithBrackets($scope.groupAccount)};
        var strIn = '';
        for (i = 0; i < chooseRoom.length; i++) {
            var obj1 = chooseRoom[i];
            strIn += util.wrapWithBrackets(obj1.roomId) + ',';
        }
        if (strIn == '') {
            p.condition += ' and room_id is null';
        } else {
            strIn = strIn.substring(0, strIn.length - 1);
            p.condition += ' and (room_id in (' + strIn + ') or room_id is null)';
        }
        dataService.refreshDebtList(p)
            .then(function () {
                $scope.debtList = dataService.getDebtList();
                $scope.debtShowList = angular.copy(dataService.getDebtList());
                var totalConsume = 0;
                var totalDeposit = 0;
                for (var i = 0; i < $scope.debtList.length; i++) {
                    var obj2 = $scope.debtList[i];
                    if (obj2.deposit) {
                        totalDeposit += obj2.deposit;
                    }
                    if (obj2.consume) {
                        totalConsume += obj2.consume;
                    }
                }
                $scope.checkInGroup.deposit = totalDeposit;
                $scope.checkInGroup.consume = totalConsume;
                $scope.currencyPayList = [];//sz-pay
                $scope.currencyPayList[0] = {};
                $scope.currencyPayList[0].currency = $scope.currencyList[0];
                if ($scope.debtPayMiddleMode) {
                    $scope.debtShowList = [];
                    totalConsumeMiddle = 0.0;
                    for (var i = 0; i < $scope.debtList.length; i++) {
                        var debt = $scope.debtList[i];
                        if (debt.consume > 0) {
                            debt.choose = true;
                            $scope.debtShowList.push(debt);
                            totalConsumeMiddle += debt.consume;
                        }
                    }
                    /*重新设置总金额*/
                    $scope.currencyPayList[0].money = totalConsume;
                    $scope.totalConsume = totalConsumeMiddle;
                }
                else {//在非中间结算的情况下，才可以计算加收房租
                    $scope.calculateDebtAdd();
                }
            })
    };
    /*选择按哪个宾客结算*/
    $scope.toggleGuest = function (guest) {
        /*只能单向的，想双向就再点两次按床位结算*/
        if (util.deleteFromArray(chooseBed, null, guest.bed)) {
            /*修改样式*/
            guest.hover = null;
            $scope.watchCheckOutByBed();
        }
    };
    /*房吧入账*/
    $scope.roomShopIn = function () {
        popUpService.pop('roomShopIn', null, refreshData);
    };
    /*杂单冲账*/
    $scope.otherConsume = function () {
        popUpService.pop('otherConsume', null, refreshData);
    };
    /*退押金*/
    $scope.cancelDeposit = function () {
        popUpService.pop('cancelDeposit', null, refreshData, {room: $scope.room.roomId});
    };
    /*中间结算*/
    var totalConsumeMiddle;//中间结算总额
    var totalConsumeMiddleHistory = 0.0;//中间结算历史总额
    $scope.debtPayMiddle = function () {
        if ($scope.debtPayMiddleMode) {/*已经是中间结算的模式下，取消中间结算*/
            $scope.debtShowList = angular.copy(dataService.getDebtList());
            $scope.calculateDebtAdd();
            $scope.debtPayMiddleMode = false;
        } else {/*正常结算的模式下，开启中间结算*/
            $scope.debtPayMiddleMode = true;
            $scope.debtPayMiddleByDetail = true;
            $scope.debtShowList = [];
            /*刨去加收房租，押金*/
            var debtList = dataService.getDebtList();
            totalConsumeMiddle = 0.0;
            totalConsumeMiddleHistory = 0.0;
            for (var i = 0; i < debtList.length; i++) {
                var debt = debtList[i];
                if (debt.consume > 0) {
                    debt.choose = true;
                    $scope.debtShowList.push(debt);
                    totalConsumeMiddle += debt.consume;
                } else if (debt.consume < 0) {
                    $scope.debtShowList.push(debt);
                    totalConsumeMiddleHistory += debt.consume;
                }
            }
            /*重新设置总金额*/
            $scope.currencyPayList[0].money = totalConsumeMiddle;
            $scope.totalConsume = totalConsumeMiddle;
        }
    };
    /*中间结算改变实收款*/
    $scope.changeTotalConsume = function () {
        if ($scope.totalConsume > totalConsumeMiddle) {
            messageService.setMessage({type: 'error', content: '中间结算不能大于消费总额'});
            popUpService.pop('message');
            $scope.totalConsume = totalConsumeMiddle;
        } else {
            $scope.currencyPayList[0].money = $scope.totalConsume;
        }
    };
    /*中间结算设置为按照明细结算*/
    $scope.changeToMiddleByDetail = function () {
        if ($scope.debtPayMiddleByDetail) {
            /*重新设置总金额*/
            $scope.currencyPayList[0].money = totalConsumeMiddle;
            $scope.totalConsume = totalConsumeMiddle;
        } else {
            $scope.currencyPayList[0].money = totalConsumeMiddle + totalConsumeMiddleHistory;
            $scope.totalConsume = totalConsumeMiddle + totalConsumeMiddleHistory;
        }

    };
    /*中间结算选择账务*/
    $scope.chooseMiddleDebt = function (debt) {
        if (debt.choose) {
            $scope.currencyPayList[0].money += debt.consume;
            $scope.totalConsume += debt.consume;
        } else {
            $scope.currencyPayList[0].money -= debt.consume;
            $scope.totalConsume -= debt.consume;
        }
    };
    /*读房卡*/
    $scope.readDoor = function () {
        doorInterfaceService.doorRead();
    };

    /*把账务拆分为按床位结算*/
    $scope.watchCheckOutByBed = function (init) {
        if (init) {
            util.addAttributeAnyWay($scope.checkInGuestList, 'hover', 'hover');
            chooseBed = util.objectListToString($scope.checkInGuestList, 'bed');
        }
        var newDebtList = [];
        /*按床位结算*/
        if ($scope.checkOutByBed) {
            var totalGuest = $scope.checkInGuestList.length;
            var totalConsume = 0.0;
            var totalDeposit = 0.0;
            for (var i = 0; i < $scope.debtShowList.length; i++) {
                var debt = $scope.debtShowList[i];
                var tempTotalConsume = 0.0;//统计之前几个房的金额合计，最后一个房用减法
                /*没有床位的明细，平均分*/
                if (!debt.bed) {
                    for (var j = 0; j < totalGuest; j++) {
                        var debtDivision = angular.copy(debt);
                        debtDivision.bed = j + 1;
                        debtDivision.description += ',床位:' + (j + 1);
                        if (j == totalGuest - 1) {//最后一个要用减法，否则总数就不对了
                            debtDivision.consume = debt.consume - tempTotalConsume;
                        } else {
                            debtDivision.consume = Math.round(debt.consume / totalGuest);
                            tempTotalConsume += debtDivision.consume;
                        }
                        newDebtList.push(debtDivision);
                        if (debtDivision.consume) {
                            totalConsume += debtDivision.consume;
                        }
                        if (debtDivision.deposit) {
                            totalDeposit += debtDivision.deposit;
                        }
                    }
                } else {
                    /*有床位的，看看在不在选择范围内*/
                    if (chooseBed.indexOf(debt.bed.toString()) > -1) {
                        newDebtList.push(debt);
                        if (debt.consume) {
                            totalConsume += debt.consume;
                        }
                        if (debt.deposit) {
                            totalDeposit += debt.deposit;
                        }
                    }
                }
            }
            $scope.checkIn.consume = totalConsume;
            $scope.checkIn.deposit = totalDeposit;
            $scope.totalConsume = totalConsume;
            $scope.currencyPayList = [];//sz-pay
            $scope.currencyPayList[0] = {};
            $scope.currencyPayList[0].currency = $scope.currencyList[0];
            $scope.currencyPayList[0].money = totalConsume;
            $scope.debtShowList = newDebtList;
        } else {
            $scope.debtShowList = angular.copy(dataService.getDebtList());
            $scope.calculateDebtAdd();
        }
    };
    /*私人方法，计算加收房租金额，同时影响结算金额*/
    $scope.calculateDebtAdd = function () {
        debtAddList = [];//加收之前清空
        if ($scope.nowTime - ($scope.checkInGroup ? $scope.checkInGroup.reachTime : $scope.checkIn.reachTime) < util.timestampByTimeString(dataService.getOtherParamMapValue('免费退房'))) {
            $scope.currencyPayList[0].money = 0;
            $scope.totalConsume = 0;//sz-pay need
            return;
        }
        $scope.debtShowList = angular.copy(dataService.getDebtList());//重新赋值，之后拼接增加的房租
        $scope.debtConsume = 0;//加收房租总金额
        if ($scope.ifDebtAdd) {
            $scope.checkRoomPriceAdd();
            calculateHourRoom();
        }
        if ($scope.room.checkInGroup) {
            $scope.currencyPayList[0].money = util.isNullSetZero($scope.checkInGroup.consume) * 1 + util.isNullSetZero($scope.debtConsume);
            $scope.totalConsume = $scope.currencyPayList[0].money;
        } else {
            $scope.currencyPayList[0].money = util.isNullSetZero($scope.checkIn.consume) * 1 + util.isNullSetZero($scope.debtConsume);
            $scope.totalConsume = $scope.currencyPayList[0].money;
        }
    };

    /*私人方法，计算小时房*/
    function calculateHourRoom() {
        var debtAdd;
        for (var i = 0; i < chooseRoom.length; i++) {
            var checkIn = chooseRoom[i];
            if (checkIn.roomPriceCategory == '小时房') {
                /*获取小时房房价定义*/
                var protocol = protocolService.getProtocolObj(checkIn.protocol, checkIn.roomCategory, checkIn.roomPriceCategory);
                /*先计算有没有超时房价*/
                var price;
                var description = '';
                var overTime = $scope.nowTime - checkIn.reachTime - util.timestampByTimeString(protocol.base);//超时时间
                if (overTime > 0) {
                    var var1 = util.getHourMinSec(overTime);
                    $scope.overTime = '超时' + var1[0] + '小时' + var1[1] + '分' + var1[2] + '秒';
                    var stepTime = util.timestampByTimeString(protocol.step);
                    price = protocol.roomPrice * 1 + Math.ceil(overTime / stepTime) * protocol.stepPrice;
                    if (!protocol.maxPrice) {//如果没定义最大房租，则取日租房的房价
                        var protocolDay = protocolService.getProtocolObj(checkIn.protocol, checkIn.roomCategory, '日租房');
                        protocol.maxPrice = protocolDay.roomPrice;
                    }
                    if (price > protocol.maxPrice) {
                        price = protocol.maxPrice;
                    }
                    description = '小时房基础房租:' + protocol.roomPrice + '超时房租:' + Math.ceil(overTime / stepTime) * protocol.stepPrice;
                } else {
                    price = protocol.roomPrice;
                    description = '小时房房租' + protocol.roomPrice;
                }
                /*添加小时房房租*/
                debtAdd = {};
                debtAdd.doTime = $scope.nowTime;
                debtAdd.pointOfSale = '房费';
                debtAdd.deposit = null;
                debtAdd.consume = price;//基础房价
                debtAdd.description = description;
                debtAdd.selfAccount = checkIn.selfAccount;
                debtAdd.roomId = checkIn.roomId;
                debtAdd.protocol = checkIn.protocol;
                debtAdd.userId = LoginService.getUser();
                debtAdd.category = '小时房费';
                debtAdd.currency = '挂账';
                debtAddList.push(debtAdd);
                $scope.debtShowList.push(debtAdd);
                $scope.debtConsume = $scope.debtConsume * 1 + price * 1;
            }
        }
    }

    /*判断是否加收房租*/
    $scope.checkRoomPriceAdd = function () {
        var debtAdd;
        /*日租房，会议房计算方式*/
        for (var i = 0; i < chooseRoom.length; i++) {
            var checkIn = chooseRoom[i];
            /*看看是不是当日来当日走*/
            var reachTime = new Date(checkIn.reachTime);
            if (checkIn.roomPriceCategory != '小时房') {
                if (util.dateEqualsDay($scope.nowTime, reachTime)) {//先判断是不是当日来当日走
                    var nightRoomLimit = util.newDateAndTime($scope.nowTime, dataService.getOtherParamMapValue('凌晨房时段'));
                    if (nightRoomLimit < reachTime) {//再判断开房的时候是不是凌晨房，因为凌晨房已经加上房租了
                        if (dataService.getOtherParamMapValue('当日转小时') == 'y') {//转为小时房结算
                            checkIn.roomPriceCategory = '小时房';
                            continue;
                        }
                        if (checkIn.roomPriceCategory == '日租房') {
                            debtAdd = {};
                            debtAdd.doTime = $scope.nowTime;
                            debtAdd.pointOfSale = '房费';
                            debtAdd.consume = checkIn.finalRoomPrice;
                            debtAdd.deposit = null;
                            debtAdd.currency = '挂账';
                            debtAdd.description = '加收房租(当日来当日走):' + debtAdd.consume;
                            debtAdd.selfAccount = checkIn.selfAccount;
                            debtAdd.roomId = checkIn.roomId;
                            debtAdd.protocol = checkIn.protocol;
                            debtAdd.userId = LoginService.getUser();
                            debtAdd.category = '加收房租';
                            debtAddList.push(debtAdd);
                            $scope.debtShowList.push(debtAdd);
                            $scope.debtConsume += debtAdd.consume;
                        } else if (checkIn.roomPriceCategory == '会议房') {//会议房要根据人头算
                            for (var j = 0; j < chooseRoom.checkInGuestList.length; j++) {
                                var checkInGuest = chooseRoom.checkInGuestList[j];
                                var debtAdd = {};
                                debtAdd.doTime = $scope.nowTime;
                                debtAdd.pointOfSale = '房费';
                                debtAdd.consume = checkIn.finalRoomPrice;
                                debtAdd.deposit = null;
                                debtAdd.currency = '挂账';
                                debtAdd.description = '加收房租(当日来当日走):' + debtAdd.consume;
                                debtAdd.selfAccount = checkIn.selfAccount;
                                debtAdd.roomId = checkIn.roomId;
                                debtAdd.protocol = checkIn.protocol;
                                debtAdd.userId = LoginService.getUser();
                                debtAdd.category = '加收房租';
                                debtAdd.bed = checkInGuest.bed;
                                debtAddList.push(debtAdd);
                                $scope.debtShowList.push(debtAdd);
                                $scope.debtConsume += debtAdd.consume;
                            }
                        }
                        continue;
                    }
                }
                for (var j = 0; j < $scope.roomPriceAddList.length; j++) {
                    var timeLimit = new Date('2050-01-01 ' + $scope.roomPriceAddList[j].timeLimit);
                    /*获取时间成功之后判断是否加收房租*/
                    if ($scope.nowTime.getHours() * 60 + $scope.nowTime.getMinutes() > timeLimit.getHours() * 60 + timeLimit.getMinutes()) {
                        /*加收房租对象*/
                        debtAdd = {};
                        debtAdd.doTime = $scope.nowTime;
                        debtAdd.pointOfSale = '房费';
                        if ($scope.roomPriceAddList[j].roomAddByMultiple != null) {
                            debtAdd.consume = checkIn.finalRoomPrice * $scope.roomPriceAddList[j].roomAddByMultiple;
                        } else {
                            debtAdd.consume = $scope.roomPriceAddList[j].roomAddStatic;
                        }
                        debtAdd.category = '加收房租';
                        debtAdd.deposit = null;
                        debtAdd.currency = '挂账';
                        debtAdd.description = '加收房租(超时退房):' + debtAdd.consume;
                        debtAdd.selfAccount = checkIn.selfAccount;
                        debtAdd.roomId = checkIn.roomId;
                        debtAdd.protocol = checkIn.protocol;
                        debtAdd.userId = LoginService.getUser();
                        debtAddList.push(debtAdd);
                        $scope.debtShowList.push(debtAdd);
                        $scope.debtConsume += debtAdd.consume;
                        break;
                    }
                }
            }
        }
    };

    /*确认离店结算*/
    $scope.guestOutAction = function () {
        /*结算条件验证*/
        if (chooseRoom.length == 0) {
            messageService.setMessage({type: 'error', content: '至少要选择一个房间'});
            popUpService.pop('message');
            return $q.resolve();
        }
        /*判断输入的金额对不对*/
        var totalPay = 0;
        for (var i = 0; i < $scope.currencyPayList.length; i++) {
            var obj = $scope.currencyPayList[i];
            if (obj.money < 0) {
                messageService.setMessage({type: 'error', content: '结账金额不可以为负数'});
                popUpService.pop('message');
                return $q.resolve();
            }
            totalPay += obj.money * 1;
        }
        if (totalPay != $scope.totalConsume) {
            messageService.setMessage({type: 'error', content: '结账金额总和不等于消费额'});
            popUpService.pop('message');
            return $q.resolve();
        }
        guestOut.groupAccount = $scope.groupAccount;
        guestOut.currencyPayList = $scope.currencyPayList;
        guestOut.debtAddList = debtAddList;
        guestOut.remark = $scope.remark;
        guestOut.roomIdList = util.objectListToString(chooseRoom, 'roomId');
        guestOut.real = !(dataService.getOtherParamMapValue("结账确认") == 'y');//real为真时计表
        /*先判断是不是中间结算*/
        if ($scope.debtPayMiddleMode) {
            /*如果是按照明细结算*/
            guestOut.payMoney = $scope.totalConsume;
            if ($scope.debtPayMiddleByDetail) {
                guestOut.debtList = util.getValueListByField($scope.debtShowList, 'choose', true);
            }
            if (totalConsumeMiddle - $scope.totalConsume + totalConsumeMiddleHistory < 0) {
                messageService.setMessage({type: 'error', content: '剩余消费不可小于0'});
                popUpService.pop('message');
                return $q.resolve();
            }
            return webService.post('guestOutMiddle', guestOut)
                .then(
                    function (d) {
                        guestOutReCall(d,guestOut);
                    }
                );
        }
        /*有卡结算判断，就是必须先注销掉门卡才能结算，只有在散客结算（也就是只有一间房时才起作用）*/
        if (dataService.getOtherParamMapValue('有卡结算') == 'y' && guestOut.roomIdList.length == 1) {
            return doorInterfaceService.doorClear(guestOut.roomIdList[0])
                .then(function () {
                    webService.post('guestOut', guestOut)
                        .then(
                            function (d) {
                                guestOutReCall(d,guestOut);
                            }
                        );
                })
        } else {
            return webService.post('guestOut', guestOut)
                .then(
                    function (d) {
                        guestOutReCall(d,guestOut);
                    }
                );
        }
    };

    function guestOutReCall(i,guestOut) {
        /*弹出打印预览界面*/
        webService.openReport(i);
        if(!guestOut.real){
            messageService.setMessage({content:'是否确认计表'});
            messageService.actionChoose()
                .then(function () {
                    guestOut.real=true;//之前不是真实计表的话，在此计表
                    webService.post('guestOut', guestOut)
                        .then(function (i) {
                            /*关闭该页面*/
                            popUpService.close('guestOut');
                        })
                })

        }else {
            /*关闭该页面*/
            popUpService.close('guestOut');
        }
    }
    /*转哑房*/
    $scope.guestOutLost = function () {
        $scope.currencyPayList = [
            {currency: '转哑房', money: $scope.totalConsume}
        ];
        $scope.guestOutAction();
    };
    /**
     * 结账明细单
     */
    $scope.guestOutQuery = function () {
        guestOut.groupAccount = $scope.groupAccount;
        guestOut.currencyPayList = $scope.currencyPayList;
        guestOut.debtAddList = debtAddList;
        guestOut.remark = $scope.remark;
        guestOut.roomIdList = util.objectListToString(chooseRoom, 'roomId');
        webService.post('guestOutQuery', guestOut)
            .then(
                function (d) {
                    /*弹出打印预览界面*/
                    window.open(host + "/receipt/" + d);
                }
            );
    };
    /**
     * 刷新数据，当房吧录入或者杂单冲账之后
     */
    function refreshData() {
        guestOutService.initData($scope.room)
            .then(function () {
                $scope.debtList = dataService.getDebtList();
                $scope.debtShowList = angular.copy(dataService.getDebtList());
                $scope.checkIn = dataService.getCheckInList()[0];
                $scope.nowTime = util.newDateNow(dataService.getTimeNow());
                if ($scope.room.checkInGroup) {
                    $scope.checkInGroup = dataService.getCheckInGroupList()[0];
                }
                $scope.calculateDebtAdd();
            });
    }
}]);