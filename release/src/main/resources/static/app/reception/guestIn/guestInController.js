/**
 *开房提交guestIn对象，其中包括checkIn对象，checkInGuestList数组
 */
'use strict';

App.controller('GuestInController', ['$scope', 'util', 'webService', 'dataService', 'receptionService', 'protocolFilter', 'popUpService', 'protocolService', 'host', 'messageService', 'readIdCardService', 'doorInterfaceService', 'dateFilter', 'LoginService', function ($scope, util, webService, dataService, receptionService, protocolFilter, popUpService, protocolService, host, messageService, readIdCardService, doorInterfaceService, dateFilter, LoginService) {
    /*要提交的对象*/
    var guestIn = {};
    /*开房信息对象*/
    var checkIn = {};
    /*在店宾客对象和数组*/
    $scope.checkInGuest = {};
    $scope.checkInGuestList = [];
    var bookIn = false;//预定开房，如果是预定开房，则监听不起作用，只阻止一次
    $scope.checkInGuestFields = [
        {name: '姓名', id: 'name', width: '100px', notNull: 'true'},
        {name: '生日', id: 'birthdayTime', date: 'short', width: '140px'},
        {name: '性别', id: 'sex', selectId: '0', width: '60px', default: '男'},
        {name: '民族', id: 'race', width: '50px', default: '汉'},
        {name: '国籍', id: 'country', width: '50px'},
        {name: '证件类型', id: 'cardType', selectId: '1', width: '100px', notNull: 'true', default: '身份证'},
        {name: '证件号码', id: 'cardId', width: '200px', notNull: 'true'},
        {name: '地址', id: 'address', width: '310px'},
        {name: '床位', id: 'bed', width: '50px'}
    ];
    $scope.checkInGuestSelect = [];
    $scope.checkInGuestSelect[0] = dataService.getSexList;
    $scope.checkInGuestSelect[1] = dataService.getCardTypeList;
    $scope.room = receptionService.getChooseRoom();
    webService.post('getBookAlone',$scope.room.category)
        .then(function (r) {
            if(!$scope.room.bookList){
                $scope.room.bookList=[];
            }
            $scope.roomBookList = r.concat($scope.room.bookList);//option中使用点不好使，所以在这里使用了
        });
    /*房价是否可编辑*/
    $scope.roomPriceEditable = dataService.getOtherParamMapValue('可编辑房价') == 'y';
    var vipNumberList = [];//会员数组
    var p1 = {condition: 'check_in=1'};
    dataService.initData(['refreshCurrencyList', 'refreshProtocolList', 'refreshTimeNow', 'refreshGuestSourceList', 'refreshCompanyList', 'refreshVipList'], [p1])
        .then(function () {
            /*可选的证件类型*/
            $scope.cardCategoryList = dataService.getCardTypeList;
            /*可选的性别*/
            $scope.sexList = dataService.getSexList;
            /*客源数组*/
            $scope.guestSourceList = util.objectListToString(dataService.getGuestSourceList(), 'guestSource');
            if (localStorage.getItem('guestInGuestSourceIndex')) {
                $scope.guestSource = $scope.guestSourceList[localStorage.getItem('guestInGuestSourceIndex')];
            } else {
                $scope.guestSource = $scope.guestSourceList[0];
            }
            /*房租方式数组*/
            $scope.roomPriceCategoryList = dataService.getRoomPriceCategory;
            $scope.roomPriceCategory = $scope.roomPriceCategoryList[0];
            /*房价协议数组*/
            $scope.protocolList = dataService.getProtocolList();
            /*押金币种*/
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currency = '人民币';
            /*单位名称*/
            $scope.companyList = dataService.getCompanyList();
            /*初始化当前时间和预计离店时间*/
            $scope.now = util.newDateNow(dataService.getTimeNow());
            var time = dataService.getOtherParamMapValue('离店时间');
            if (time != 'n') {
                $scope.leave = util.newDateAndTime(new Date($scope.now.valueOf() + $scope.days * 24 * 60 * 60 * 1000), time)
            } else {
                $scope.leave = new Date($scope.now.valueOf() + $scope.days * 24 * 60 * 60 * 1000);
            }
            $scope.hour = $scope.leave.getHours();
            $scope.min = $scope.leave.getMinutes();
            vipNumberList = util.objectListToString(dataService.getVipList(), 'vipNumber');
        });
    /**
     * 界面上用到的方法
     */
    /*时间按钮减一天*/
    $scope.minusDay = function () {
        $scope.days = $scope.days - 1;
        if($scope.days==0){
            $scope.days++;
        }
        calculateLeaveTime();
    };
    /*时间按钮加一天*/
    $scope.addDay = function () {
        $scope.days = $scope.days + 1;
        calculateLeaveTime();
    };
    /*读取会员开房*/
    $scope.readCard = function () {
        $scope.vip = util.getValueByField(dataService.getVipList(), 'idNumber', $scope.idNumber);
        if ($scope.vip) {
            var checkInGuest = {};
            checkInGuest.name = $scope.vip.name;
            checkInGuest.birthdayTime = $scope.vip.birthdayTime;
            checkInGuest.sex = $scope.vip.sex;
            checkInGuest.race = $scope.vip.race;
            checkInGuest.cardType = '身份证';
            checkInGuest.cardId = $scope.vip.cardId;
            checkInGuest.address = $scope.vip.address;
            $scope.checkInGuestList.push(checkInGuest);
        }
    };
    /*读身份证信息*/
    $scope.readIdCard = function () {
        /*readIdCardService.readIdCard();
         var guestInfo = readIdCardService.getGuestInfo();
         if (dataService.getOtherParamMapValue("客史会员") == 'y' && vipNumberList.indexOf(guestInfo.cardId) > -1) {
         messageService.setMessage({content: '该宾客之前来过，是否启用客史房价'});
         messageService.actionChoose()
         .then(function () {
         $scope.vip = util.getValueByField(dataService.getVipList(), 'vipNumber', guestInfo.cardId);
         })
         }
         $scope.checkInGuestList.push(guestInfo);*/
        /*测试时*/
        $scope.checkInGuestList.push({name: '舒展', cardId: '123456789012345678', bed: '1'});
    };
    /*监听单位和房租方式的的变化，从而设置房价协议*/
    var watch = $scope.$watchGroup(['roomPriceCategory', 'room.category', 'company', 'vip'], function () {
        if (!bookIn) {//只阻止一次
            $scope.protocolShowList = protocolFilter($scope.protocolList, $scope.roomPriceCategory, $scope.room.category, $scope.company, $scope.vip);
            if ($scope.protocolShowList) {
                $scope.protocol = $scope.protocolShowList[0];
            }
        } else {
            bookIn = false;
        }
    });
    /*添加宾客后前检查是不是会议房，如果是的话设置床位号，（后期去掉会议房判断，普通房也有床位）*/
    var watch2 = $scope.$watchCollection('checkInGuestList', function (newValue, oldValue) {
        if (newValue.length > oldValue.length) {
            newValue[newValue.length - 1].bed = newValue.length;
        }
    });
    var watchGuest = $scope.$watch('checkInGuestList[0]',function (newValue, oldValue) {
        if(!newValue){//户籍信息被删除并且之前的会员是通过户籍信息身份证添加的，清空会员信息
            if($scope.vip&&$scope.vip.cardIdAdd){
                $scope.vip=null;
            }
        }else {
            if(!$scope.vip) {//没有会员信息，有宾客信息，查找会员身份证号
                $scope.vip = angular.copy(util.getValueByField(dataService.getVipList(), 'cardId', newValue.cardId));
                $scope.vip.cardIdAdd=true;//通过身份证添加的
            }
        }
    });
    /*选择预定开房*/
    $scope.chooseBook = function (book) {
        $scope.company = util.getValueByField($scope.companyList, 'name', book.company);
        if ($scope.company == false) {
            $scope.company = null;
        }
        $scope.guestSource = book.guestSource;
        $scope.roomPriceCategory = book.roomPriceCategory;
        $scope.protocol = protocolService.getProtocolObj(book.protocol, $scope.room.category, $scope.roomPriceCategory);
        $scope.protocolShowList = protocolFilter($scope.protocolList, $scope.roomPriceCategory, $scope.room.category, $scope.company, $scope.vip);
        /*如果有押金则转为预付*/
        if (book.subscription) {
            $scope.deposit = book.subscription;
            $scope.currency = book.currency;
        }
        bookIn = true;
    };
    /*开房*/
    $scope.guestInAction = function () {
        var protocol;//如果是自定义房价的话，该值不为空
        /*先进行一波输入校验*/
        if (!$scope.protocol) {
            messageService.setMessage({type: 'error', content: '您还没选择房价协议'});
            popUpService.pop('message');
            return;
        }
        if (!$scope.deposit) {
            $scope.deposit = 0;
        }
        if (isNaN($scope.deposit)) {
            messageService.setMessage({type: 'error', content: '您输入的预付款不是数字'});
            popUpService.pop('message');
            return;
        }
        if ($scope.currency == '会员' && !$scope.vip) {
            messageService.setMessage({type: 'error', content: '您选择用会员余额充当押金，但还没有读取会员卡'});
            popUpService.pop('message');
            return;
        }
        checkIn.roomId = $scope.room.roomId;
        checkIn.roomCategory = $scope.room.category;
        checkIn.reachTime = $scope.now;
        $scope.leave.setHours($scope.hour);
        $scope.leave.setMinutes($scope.min);
        checkIn.leaveTime = $scope.leave;
        checkIn.guestSource = $scope.guestSource;
        checkIn.vip = $scope.mainGuest ? 1 : 0;
        checkIn.breakfast = $scope.protocol.breakfast;
        checkIn.important = $scope.important;
        checkIn.remark = $scope.remark;
        checkIn.finalRoomPrice = $scope.protocol.roomPrice;
        if ($scope.roomPriceEditable) {
            $scope.protocol.protocol = $scope.room.roomId + $scope.protocol.protocol;
            $scope.protocol.special = 'y';
            $scope.protocol.temp = true;
            checkIn.protocol = $scope.protocol.protocol;
            protocol = $scope.protocol;
        } else {
            checkIn.protocol = $scope.protocol.protocol;
        }
        if ($scope.company) {
            checkIn.company = $scope.company.name;
        }
        checkIn.vipNumber = $scope.vip ? $scope.vip.vipNumber : null;
        checkIn.currency = $scope.currency;
        checkIn.deposit = $scope.deposit;
        checkIn.roomPriceCategory = $scope.roomPriceCategory;
        checkIn.userId = LoginService.getUser();
        guestIn.checkInList = [checkIn];
        angular.forEach($scope.checkInGuestList, function (item) {
            item.roomId = $scope.room.roomId;
        });
        guestIn.checkInGuestList = $scope.checkInGuestList;
        guestIn.book = $scope.book;
        guestIn.protocol = protocol;
        webService.post('guestIn', guestIn)
            .then(function (d) {
                /*弹出打印预览界面*/
                if (d != -1) {
                    window.open(host + "/receipt/" + d);
                }
                var num = 1;//做几张房卡
                if (dataService.getOtherParamMapValue('按人数发卡') == 'y') {
                    num = $scope.checkInGuestList.length;
                }
                doorInterfaceService.doorWrite($scope.room.roomId, dateFilter($scope.leave, 'yyyyMMddHHmmss'), num)
                    .then(function () {
                        /*执行成功后，关闭该页面*/
                        popUpService.close('guestIn');
                        /*本地记录选择的客源*/
                        localStorage.setItem('guestInGuestSourceIndex', $scope.guestSourceList.indexOf($scope.guestSource));
                    })
            });
        watch();
        watch2();
        watchGuest();
    };
    /*点击阴影关闭,注销相关watch*/
    $scope.closePop = function () {
        popUpService.close('guestIn', true);
        watch();
    };
    /**
     * 私有方法
     */
    /*根据来期和预住天数计算出离期*/
    function calculateLeaveTime() {
        $scope.leave = new Date($scope.now.valueOf() + $scope.days * 24 * 60 * 60 * 1000);
    }
}]);
