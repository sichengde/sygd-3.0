/**
 * Created by 舒展 on 2016/6/19 0019.
 */
App.controller('bookInputController', ['$scope', 'dataService', 'util', 'protocolFilter', 'protocolService', 'webService', 'LoginService', 'popUpService', 'bookRoomFilter','messageService','doorInterfaceService','host', function ($scope, dataService, util, protocolFilter, protocolService, webService, LoginService, popUpService, bookRoomFilter,messageService,doorInterfaceService,host) {
    $scope.bookList = angular.copy(dataService.getBookList());
    if(popUpService.getParam()){
        $scope.mode='修改';
        $scope.book=angular.copy(popUpService.getParam());
        $scope.chooseRoomList=$scope.book.bookRoomList;
        $scope.chooseCategoryList=$scope.book.bookRoomCategoryList;
        util.deleteFromArray($scope.bookList,'id',$scope.book.id);
    }else {
        $scope.mode='新增';
        $scope.book = {};
        $scope.chooseRoomList = [];
        $scope.chooseCategoryList = [];
    }
    $scope.bookInputFields = [
        {name: '房号', id: 'roomId', width: '80px'},
        {name: '房类', id: 'roomCategory', width: '80px'},
        {name: '房价', id: 'roomPrice'}
    ];
    $scope.bookRoomCategoryFields = [
        {name: '房类', id: 'roomCategory', width: '130px'},
        {name: '数量', id: 'num'}
    ];
    $scope.ensureRoom=true;//初始化是锁房界面
    var p1={condition:'check_in=1'};
    dataService.initData(['refreshCurrencyList','refreshRoomList', 'refreshRoomCategoryList', 'refreshTimeNow', 'refreshProtocolList', 'refreshCompanyList','refreshGuestSourceList'],[p1])
        .then(function () {
            $scope.roomCategoryList = util.objectListToString(dataService.getRoomCategoryList(), 'category');
            $scope.roomCategorySub = util.objectListToString(dataService.getRoomCategoryList(), 'category');//不带全部;
            $scope.roomCategory = $scope.roomCategoryList[0];
            /*房租方式数组*/
            $scope.roomPriceCategoryList = dataService.getRoomPriceCategory;
            /*协议数组*/
            $scope.protocolList = dataService.getProtocolList();
            var a = protocolFilter($scope.protocolList, $scope.book.roomPriceCategory, $scope.roomCategory);
            /*单位数组*/
            $scope.companyList = dataService.getCompanyList();
            /*分析房间*/
            $scope.roomList = dataService.getRoomList();
            $scope.roomShowList = bookRoomFilter($scope.roomList, $scope.roomCategory,$scope.bookList, $scope.book.reachTime, $scope.book.leaveTime);
            $scope.unsureRoomBookNum=calculateTotalUnsureRoom();
            /*把选中的房间图标置为红色*/
            if($scope.mode=='修改') {
                for (var i = 0; i < $scope.chooseRoomList.length; i++) {
                    var obj = $scope.chooseRoomList[i];
                    util.getValueByField($scope.roomShowList, 'roomId', obj.roomId).hover = 'hover';
                }
            }
            /*房间对象*/
            $scope.checkInList = dataService.getCheckInList();
            /*客源数组*/
            $scope.guestSourceList = util.objectListToString(dataService.getGuestSourceList(), 'guestSource');
            /*订金币种*/
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            if($scope.mode=='新增') {
                $scope.book.roomPriceCategory = $scope.roomPriceCategoryList[0];
                /*时间*/
                $scope.book.reachTime = util.newDateNow(dataService.getTimeNow());
                var time = dataService.getOtherParamMapValue('离店时间');
                if (time != 'n') {
                    $scope.book.leaveTime = util.newDateAndTime(new Date($scope.book.reachTime.valueOf() + 24 * 60 * 60 * 1000), time)
                } else {
                    $scope.book.leaveTime = new Date($scope.book.reachTime.valueOf() + 24 * 60 * 60 * 1000);
                }
                $scope.book.remainTime = util.newDateAndTime(new Date($scope.book.leaveTime.valueOf() + 24 * 60 * 60 * 1000), '00:00:00');//保留时间取第二天凌晨
                $scope.book.guestSource = $scope.guestSourceList[0];
                $scope.book.currency = '人民币';
                $scope.protocol = a[0];
            }
        });
    /**
     * 页面交互
     */
    /*选择预定的房间*/
    $scope.addOrDeleteRoom = function (r, shiftKey) {
        $scope.chooseCategoryList = [];//清楚不定房的选择（如果有）
        if (shiftKey) {
            var j = $scope.roomShowList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                if (util.getValueByField($scope.chooseRoomList, 'roomId', $scope.roomShowList[i].roomId)) {
                    break;
                } else {
                    $scope.addOrDeleteRoom($scope.roomShowList[i]);
                }
            }
        } else {
            if (util.deleteFromArray($scope.chooseRoomList, 'roomId', r.roomId)) {
                r.hover=null;
            } else {
                var room = {};
                room.roomId = r.roomId;
                room.totalBed = r.totalBed;
                room.roomPrice = $scope.protocol.roomPrice;
                room.roomCategory = $scope.protocol.roomCategory;
                $scope.chooseRoomList.push(room);
                r.hover='hover';
            }
        }
    };
    /*选择预定的房类*/
    $scope.addOrDeleteCategory = function (category, num) {
        /*请出选中的房间，改为不确定房间订房*/
        for (var i = 0; i < $scope.chooseRoomList.length; i++) {
            var obj = $scope.chooseRoomList[i];
            util.getValueByField($scope.roomShowList, 'roomId', obj.roomId).hover = null;
        }
        $scope.chooseRoomList = [];//清除确定房的选择（如果有）
        var exist = util.getValueByField($scope.chooseCategoryList, 'roomCategory', category);
        if (exist) {
            exist.num = exist.num * 1 + num * 1;
        } else {
            $scope.chooseCategoryList.push({roomCategory: category, num: num});
        }
    };

    /*清除选择*/
    $scope.clearList = function () {
        angular.forEach($scope.roomShowList, function (value) {
            value.hover = null;
        });
        $scope.chooseRoomList.length = 0;
        $scope.chooseCategoryList.length = 0;
    };
    /*批量制卡*/
    $scope.writeCard=function () {
        var bed;
        if (dataService.getOtherParamMapValue('按人数发卡') == 'y') {
            bed=true;
        }
        doorInterfaceService.doorWriteList(util.objectListToString($scope.chooseRoomList,'roomId'),$scope.book.leaveTime,bed);
    };
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
    /*监听变量的变化，从而设置房价协议*/
    var watch = $scope.$watchGroup(['book.roomPriceCategory', 'roomCategory', 'company', 'book.reachTime', 'book.leaveTime', 'abortTime'], function (newValues, oldValues) {
        if(newValues[0]!=oldValues[0] || newValues[1]!=oldValues[1] || newValues[2]!=oldValues[2]) {
            $scope.protocolShowList = protocolFilter($scope.protocolList, $scope.book.roomPriceCategory, $scope.roomCategory, $scope.company);
            if ($scope.protocolShowList) {
                $scope.protocol = $scope.protocolShowList[0];
            }
        }
        if(newValues[1]!=oldValues[1] || newValues[3]!=oldValues[3] || newValues[4]!=oldValues[4] || newValues[5]!=oldValues[5]) {
            $scope.roomShowList = bookRoomFilter($scope.roomList, $scope.roomCategory, $scope.bookList, $scope.book.reachTime, $scope.book.leaveTime, $scope.abortTime);
            $scope.unsureRoomBookNum=calculateTotalUnsureRoom();
        }
    });
    /*监听选中的房间或者房类*/
    var watch2 =$scope.$watchCollection('chooseRoomList',function (newValue) {
        $scope.beginChoose = !($scope.chooseRoomList.length == 0 && $scope.chooseCategoryList.length == 0);
    });
    var watch3 =$scope.$watchCollection('chooseCategoryList',function (newValue) {
        $scope.beginChoose = !($scope.chooseRoomList.length == 0 && $scope.chooseCategoryList.length == 0);
    });
    /*监听预离时间，设置保留时间*/
    var watch4=$scope.$watch('book.leaveTime',function () {
        if($scope.book.leaveTime) {
            $scope.book.remainTime = util.newDateAndTime(new Date($scope.book.leaveTime.valueOf() + 24 * 60 * 60 * 1000), '00:00:00');//保留时间取第二天凌晨
        }
    });
    /*确认预订*/
    $scope.confirm = function () {
        /*先进行一下输入校验*/
        if(!$scope.book.name || $scope.book.name==''){
            messageService.setMessage({type:'error',content:'您需要输入一个订单名称'});
            popUpService.pop('message');
            return;
        }
        if($scope.chooseRoomList.length==0 && $scope.chooseCategoryList==0){
            messageService.setMessage({type:'error',content:'您好像什么房也没有订'});
            popUpService.pop('message');
            return;
        }
        var post = {};
        if ($scope.chooseRoomList.length > 0) {
            $scope.book.totalRoom = $scope.chooseRoomList.length;
        } else {
            var totalNum = 0;
            angular.forEach($scope.chooseCategoryList, function (value) {
                totalNum = totalNum + value.num * 1;
            });
            $scope.book.totalRoom = totalNum;
        }
        $scope.book.protocol = $scope.protocol.protocol;
        if ($scope.company) {
            $scope.book.company = $scope.company.name;
        }
        $scope.book.bookedRoom = 0;
        $scope.book.userId = LoginService.getUser();
        $scope.book.state = '有效';
        post.book = $scope.book;
        post.bookRoomList = $scope.chooseRoomList;
        post.bookRoomCategoryList = $scope.chooseCategoryList;
        if($scope.mode=='新增') {
            webService.post('bookInput', post)
                .then(function (r) {
                    /*关闭该页面*/
/*                    popUpService.close('bookInput');*/
                    messageService.actionSuccess();
                    window.open(host + "/receipt/" + r);

                });
        }else {
            webService.post('bookUpdate', post)
                .then(function () {
                    /*关闭该页面*/
                   /* popUpService.close('bookInput');*/
                    messageService.actionSuccess();
                });
        }
        watch();
    };
    $scope.close = function () {
        popUpService.close('bookInput');
        watch();
        watch2();
        watch3();
        watch4();
    };
    function calculateLeaveTime() {
        $scope.book.leaveTime = new Date($scope.book.reachTime.valueOf() + $scope.days * 24 * 60 * 60 * 1000);
    }
    function calculateTotalUnsureRoom() {
        var totalRoom=0;//该房类总计预定的房数
        for (var i = 0; i < $scope.bookList.length; i++) {
            var book=$scope.bookList[i];
            /*var1不为空则表示有该房类*/
            var var1 = util.getValueByField(book.bookRoomCategoryList, 'roomCategory', $scope.roomCategory);
            if (var1) {
                /*有交集*/
                if (book.reachTime < $scope.book.leaveTime && book.leaveTime > $scope.book.reachTime) {
                    totalRoom++;
                }
            }
        }
        return totalRoom;
    }

}]);
