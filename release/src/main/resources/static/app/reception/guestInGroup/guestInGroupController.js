/**
 * Created by 舒展 on 2016-04-28.
 * 团队开房
 */
App.controller('GuestInGroupController', ['$scope', 'util', 'webService', 'dataService', 'receptionService', 'protocolFilter', 'LoginService', 'protocolService', 'messageService', 'popUpService', 'roomFilter', 'doorInterfaceService', 'host', '$filter', 'dateFilter', 'readIdCardService', function ($scope, util, webService, dataService, receptionService, protocolFilter, LoginService, protocolService, messageService, popUpService, roomFilter, doorInterfaceService, host, $filter, dateFilter, readIdCardService) {
    /*用于提交的对象*/
    var guestInGroup = {};
    $scope.checkInList = [];
    $scope.checkInGuestList = [];
    $scope.checkInGroup = {};
    dataService.refreshOtherParamList()
        .then(function () {
            $scope.editableRoomPrice = dataService.getOtherParamMapValue('可编辑房价') == 'y';
        });
    $scope.checkInFields = [{name: '房号', id: 'roomId', width: '120px', static: 'true'},
        {name: '房型', id: 'roomCategory', width: '200px', static: 'true'},
        {name: '结算价格', id: 'finalRoomPrice', width: '150px'},
        {name: '早餐人数', id: 'breakfast', width: '100px'}];
    $scope.checkInGuestFields = [
        {name: '房号', id: 'roomId', width: '120px', static: 'true'},
        {name: '姓名', id: 'name', width: '150px', notNull: 'true'},
        {name: '性别', id: 'sex', selectId: '0', width: '70px'},
        {name: '民族', id: 'race', width: '150px'},
        {name: '证件类型', id: 'cardType', selectId: '1', width: '150px', default: '身份证'},
        {name: '证件号码', id: 'cardId', width: '300px', notNull: 'true'},
        {name: '地址', id: 'address', width: '300px'},
        {name: '床位', id: 'bed', width: '50px', static: 'true'}
    ];
    $scope.selectGuestList = [];
    $scope.selectGuestList[0] = dataService.getSexList;
    $scope.selectGuestList[1] = dataService.getCardTypeList;
    var bookIn = false;//预定开房，如果是预定开房，则监听不起作用，只阻止一次
    var p2 = {condition: 'check_in=1'};
    dataService.initData(['refreshBookList', 'refreshCurrencyList', 'refreshRoomList', 'refreshGuestSourceList', 'refreshProtocolList', 'refreshRoomCategoryList', 'refreshTimeNow', 'refreshBookRoomList', 'refreshCheckInGroupList', 'refreshCompanyList', 'refreshVipList'], [{condition: 'state=\'有效\' and total_room-book.booked_room>0'}, p2])
        .then(function () {
            /*房间类别数组*/
            $scope.roomCategoryList = util.objectListToString(dataService.getRoomCategoryList(), 'category');
            $scope.roomCategory = $scope.roomCategoryList[0];
            /*房间数组*/
            $scope.availableState = dataService.getRoomStateAvailableList();
            $scope.roomList = roomFilter(dataService.getRoomList(), $scope.availableState, '全部');
            $scope.roomShowList = roomFilter($scope.roomList, $scope.availableState, $scope.roomCategory);
            /*可选的证件类型*/
            $scope.cardCategoryList = dataService.getCardTypeList;
            /*可选的性别*/
            $scope.sexList = dataService.getSexList;
            /*客源数组*/
            $scope.guestSourceList = util.objectListToString(dataService.getGuestSourceList(), 'guestSource');
            $scope.guestSource = $scope.guestSourceList[0];
            /*房租方式数组*/
            $scope.roomPriceCategoryList = dataService.getRoomPriceCategory;
            $scope.roomPriceCategory = $scope.roomPriceCategoryList[0];
            /*房价协议数组*/
            $scope.protocolList = dataService.getProtocolList();
            /*押金币种*/
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.checkInGroup.currency = $scope.currencyList[0];
            /*预定数组*/
            $scope.bookList = dataService.getBookList();
            /*已开团队数组*/
            $scope.checkInGroupList = dataService.getCheckInGroupList();
            /*单位名称*/
            $scope.companyList = dataService.getCompanyList();
            /*时间信息*/
            $scope.checkInGroup.reachTime = util.newDateNow(dataService.getTimeNow());
            var time = dataService.getOtherParamMapValue('离店时间');
            if (time != 'n') {
                $scope.checkInGroup.leaveTime = util.newDateAndTime(new Date($scope.checkInGroup.reachTime.valueOf() + $scope.days * 24 * 60 * 60 * 1000), time)
            } else {
                $scope.checkInGroup.leaveTime = new Date($scope.now.valueOf() + $scope.days * 24 * 60 * 60 * 1000);
            }
        });
    /**
     * 界面交互
     */
    /*时间按钮减一天*/
    $scope.minusDay = function () {
        $scope.days = $scope.days - 1;
        if ($scope.days == 0) {
            $scope.days++;
        }
        calculateLeaveTime();
    };
    /*时间按钮加一天*/
    $scope.addDay = function () {
        $scope.days = $scope.days + 1;
        calculateLeaveTime();
    };
    /*选择房间，初始化checkIn对象，然后加入到checkInList数组中去*/
    $scope.clickRoom = function (r, shiftKey) {
        if (shiftKey) {
            var j = $scope.roomShowList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                if (util.getValueByField($scope.checkInList, 'roomId', $scope.roomShowList[i].roomId)) {
                    break;
                } else {
                    $scope.clickRoom($scope.roomShowList[i]);
                }
            }
        } else {
            var room=util.getValueByField($scope.roomList, 'roomId', r.roomId);
            if (util.deleteFromArray($scope.checkInList, 'roomId', r.roomId)) {
                r.hover = null;
            } else {
                r.hover = 'hover';
                /*先获取一下当前房价协议对象*/
                var checkIn = {};
                checkIn.roomId = r.roomId;
                checkIn.reachTime = $scope.checkInGroup.reachTime;
                checkIn.leaveTime = $scope.checkInGroup.leaveTime;
                checkIn.guestSource = $scope.guestSource;
                checkIn.breakfast = $scope.protocol.breakfast;
                checkIn.finalRoomPrice = $scope.protocol.roomPrice;
                checkIn.protocol = $scope.protocol.protocol;
                if ($scope.company) {
                    checkIn.company = $scope.company.name;
                }
                checkIn.vipNumber = $scope.vipNumber;
                checkIn.roomPriceCategory = $scope.roomPriceCategory;
                checkIn.userId = LoginService.getUser();
                /*用于前端显示的值，后端不会接收*/
                checkIn.roomCategory = room.category;
                checkIn.ifRoom = room.ifRoom;
                $scope.checkInList.push(checkIn);
            }
        }
        $scope.checkInRoomIdList = util.objectListToString($scope.checkInList, 'roomId');
        $scope.checkInBackUp=angular.copy($scope.checkInList);
    };
    /*预定转入住*/
    $scope.bookIn = function (book) {
        $scope.clearList();
        $scope.leave = book.leaveTime;
        $scope.checkInGroup.name = '预定开房:' + book.name;
        $scope.checkInGroup.guestSource = book.guestSource;
        $scope.checkInGroup.roomPriceCategory = book.roomPriceCategory;
        $scope.company = util.getValueByField($scope.companyList, 'name', book.company);
        if ($scope.company == false) {
            $scope.company = null;
        }
        bookIn = true;
        /*订单中明确房间*/
        if (book.bookRoomList.length > 0) {
            var wrongMsg = {};
            wrongMsg.content = '';
            for (var i = 0; i < book.bookRoomList.length; i++) {
                var obj = book.bookRoomList[i];
                if (obj.opened) {//已经开过了
                    continue;
                }
                $scope.protocol = protocolService.getProtocolObj(book.protocol, obj.roomCategory, book.roomPriceCategory);
                var room = util.getValueByField($scope.roomList, 'roomId', obj.roomId);
                if (room) {
                    $scope.clickRoom(room);
                } else {
                    wrongMsg.content += obj.roomId + ','
                }
            }
            if (wrongMsg.content) {
                wrongMsg.type = 'alert';
                wrongMsg.content += '房间在用无法开房，请选择其他房间或者退房';
                messageService.setMessage(wrongMsg);
                popUpService.pop('message');
            }
            $scope.roomCategory = book.bookRoomList[i - 1].roomCategory;//设置最后一个房间类型为当前显示的房间类型
            $scope.protocolShowList = protocolFilter($scope.protocolList, $scope.roomPriceCategory, $scope.roomCategory, $scope.company, $scope.vip);
        }
        /*订单中不明确房间*/
        if (book.bookRoomCategoryList.length > 0) {
            /*提示一下是否需要安排*/
            /*需要自动安排*/
            messageService.setMessage({content: '没有预定具体房间，是否需要自动安排'});
            messageService.actionChoose()
                .then(function () {
                    wrongMsg = '';
                    for (i = 0; i < book.bookRoomCategoryList.length; i++) {//遍历预定的房类
                        var obj1 = book.bookRoomCategoryList[i];
                        var available = roomFilter($scope.roomList, $scope.availableState, obj1.roomCategory);//该房类可用房数组
                        var bookedNum = 0;
                        $scope.protocol = protocolService.getProtocolObj(book.protocol, obj1.roomCategory, book.roomPriceCategory);
                        for (var j = 0; j < available.length; j++) {
                            if (bookedNum == obj1.num) {
                                break;
                            }
                            var obj2 = available[j];
                            $scope.clickRoom(obj2);
                            bookedNum++;
                        }
                        if (bookedNum < obj1.num) {
                            wrongMsg = wrongMsg + '预定' + obj1.roomCategory + ':' + obj1.num + '间,只有' + bookedNum + '间可用'
                        }
                    }
                    if (wrongMsg != '') {
                        popUpService.pop('message');
                        messageService.updateMessage({content: wrongMsg, type: 'alert'});
                    }
                    $scope.roomCategory = book.bookRoomCategoryList[i - 1].roomCategory;//设置最后一个房间类型为当前显示的房间类型
                    $scope.protocolShowList = protocolFilter($scope.protocolList, $scope.roomPriceCategory, $scope.roomCategory, $scope.company, $scope.vip);
                });
        }
    };
    /*清空选择*/
    $scope.clearList = function () {
        angular.forEach($scope.roomShowList, function (value) {
            value.hover = null;
        });
        $scope.checkInList = [];
    };
    /*读取会员开房*/
    $scope.readCard = function () {
        $scope.vip = util.getValueByField(dataService.getVipList(), 'idNumber', $scope.idNumber)
    };
    /*监听单位和房租方式的的变化，从而设置房价协议*/
    var watch = $scope.$watchGroup(['roomPriceCategory', 'roomCategory', 'company', 'vip', 'onlyBook'], function (newValues, oldValues) {
        /*如果房间类型变了，还要更新一下房间数组*/
        if (newValues[1] != oldValues[1]) {
            $scope.roomShowList = roomFilter($scope.roomList, $scope.availableState, $scope.roomCategory)
        }
        if ($scope.onlyBook) {//二次过滤，只有预定的房才显示
            $scope.roomShowList = $filter('filter')($scope.roomShowList, function (item) {
                return util.getValueByField($scope.book.bookRoomList, 'roomId', item.roomId);
            })
        }
        if (!bookIn) {
            $scope.protocolShowList = protocolFilter($scope.protocolList, $scope.roomPriceCategory, $scope.roomCategory, $scope.company, $scope.vip);
            if ($scope.protocolShowList) {
                $scope.protocol = $scope.protocolShowList[0];
            }
        } else {
            bookIn = false;
        }
    });
    /*添加宾客后前检查是不是会议房，如果是的话设置床位号，（后期去掉会议房判断，普通房也有床位）*/
    var watch2 = $scope.$watchCollection('checkInGuestList', function (newValue, oldValue) {
        if (newValue.length > oldValue.length) {//大于说明是新增
            /*相对于散客开房这里要判断一下房号*/
            var newRoomId = newValue[newValue.length - 1].roomId;
            for (var i = oldValue.length - 1; i >= 0; i--) {
                var checkInGuest = oldValue[i];
                if (newRoomId == checkInGuest.roomId) {
                    newValue[newValue.length - 1].bed = checkInGuest.bed + 1;
                    break;
                }
            }
            if (i == -1) {//之前没有，那就设置为1
                newValue[newValue.length - 1].bed = 1;
            }
            /*checkIn里设置一个床位*/
            util.getValueByField($scope.checkInList, 'roomId', newRoomId).totalBed = newValue[newValue.length - 1].bed;
        }
    });
    /*读身份证信息*/
    $scope.readIdCard = function (roomId) {
        if (!roomId) {
            messageService.setMessage({type: 'error', content: '请先输入该宾客的房号'});
            popUpService.pop('message');
            return;
        }
        readIdCardService.readIdCard()
            .then(function (r) {
                r.roomId = roomId;
                $scope.checkInGuestList.push(r);
            });
    };
    /*开房确认*/
    $scope.guestInGroupAction = function () {
        /*输入验证*/
        if (!$scope.checkInGroup.name || $scope.checkInGroup.name == '') {
            messageService.setMessage({type: 'error', content: '您还没有输入团名'});
            popUpService.pop('message');
            return;
        }
        /*忘了为什么有这个限制了，先删除*/
        /*if ($scope.checkInList.length < 2) {
         messageService.setMessage({type: 'error', content: '至少要开两间以上的房间'});
         popUpService.pop('message');
         return;
         }*/
        if (!$scope.checkInGroup.leaderRoom) {
            messageService.setMessage({type: 'error', content: '至少选择一个领队房'});
            popUpService.pop('message');
            return;
        }
        /*if ($scope.checkInGuestList.length == 0) {
            messageService.setMessage({type: 'error', content: '至少需要一个客人信息'});
            popUpService.pop('message');
            return;
        }*/
        if ($scope.checkInGroup.currency == '会员' && !$scope.vip) {
            messageService.setMessage({type: 'error', content: '您选择用会员余额充当押金，但还没有读取会员卡'});
            popUpService.pop('message');
            return;
        }
        if (!$scope.checkInGroup.deposit) {
            $scope.checkInGroup.deposit = 0;
        }
        if (isNaN($scope.checkInGroup.deposit)) {
            messageService.setMessage({type: 'error', content: '您输入的预付款不是数字'});
            popUpService.pop('message');
            return;
        }
        /*验证有没有乱输入房号的*/
        for (var i = 0; i < $scope.checkInGuestList.length; i++) {
            var obj = $scope.checkInGuestList[i];
            if (!obj.roomId) {
                messageService.setMessage({type: 'error', content: '客人' + obj.name + '信息没有输入房号'});
                popUpService.pop('message');
                return;
            }
        }
        /*如果该房间没有录入客人信息，则产生一条无名客人*/
        for (var i = 0; i < $scope.checkInList.length; i++) {
            var checkIn = $scope.checkInList[i];
            if (!util.getValueByField($scope.checkInGuestList, 'roomId', checkIn.roomId)) {
                $scope.checkInGuestList.push({roomId: checkIn.roomId, name: '临时', cardId: '000'});
                checkIn.totalBed = 1;
            }
            /*初始化信息*/
            checkIn.reachTime = $scope.checkInGroup.reachTime;
            checkIn.leaveTime = $scope.checkInGroup.leaveTime;
            checkIn.guestSource = $scope.guestSource;
            checkIn.protocol = $scope.protocol.protocol;
            if ($scope.company) {
                checkIn.company = $scope.company.name;
            }
            checkIn.vipNumber = $scope.vipNumber;
            checkIn.groupName=$scope.checkInGroup.name;
        }
        /*ajax对象封装*/
        guestInGroup.checkInList = $scope.checkInList;
        guestInGroup.checkInGuestList = $scope.checkInGuestList;
        if ($scope.company) {
            $scope.checkInGroup.company = $scope.company.name;
        }
        $scope.checkInGroup.protocol = $scope.protocol.protocol;
        $scope.checkInGroup.roomPriceCategory = $scope.roomPriceCategory;
        $scope.checkInGroup.guestSource = $scope.guestSource;
        if ($scope.vip) {
            $scope.checkInGroup.vipNumber = $scope.vip.vipNumber;
        }
        guestInGroup.checkInGroup = $scope.checkInGroup;
        guestInGroup.checkInGroup.totalRoom = $scope.checkInList.length;
        guestInGroup.book = $scope.book;
        webService.post('guestIn', guestInGroup)
            .then(function (d) {
                if (d != -1) {
                    window.open(host + "/receipt/" + d);
                }
                /*批量制卡*/
                doorInterfaceService.doorWrite(util.objectListToString($scope.checkInList, 'roomId'), dateFilter(guestInGroup.checkInGroup.leaveTime, 'yyyyMMddHHmmss'), util.objectListToStringDuplicate($scope.checkInList, 'totalBed'))
                    .then(function () {
                        /*弹出打印预览界面*/
                        window.location.reload();
                    }, function () {
                        alert('发卡失败');
                        window.location.reload();
                    })
            });
        watch();
        watch2();
    };
    /**
     * 私有方法
     */
    /*根据来期和预住天数计算出离期*/
    function calculateLeaveTime() {
        $scope.checkInGroup.leaveTime = new Date($scope.checkInGroup.reachTime.valueOf() + $scope.days * 24 * 60 * 60 * 1000);
    }
}]);
