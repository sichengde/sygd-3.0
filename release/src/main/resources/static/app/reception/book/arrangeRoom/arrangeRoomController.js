/**
 * Created by Administrator on 2016/7/27 0027.
 */
App.controller('arrangeRoomController', ['$scope', 'popUpService', 'bookRoomFilter', 'dataService', 'messageService', 'webService','protocolFilter','util','protocolService',
    function ($scope, popUpService, bookRoomFilter, dataService, messageService, webService,protocolFilter,util,protocolService) {
        $scope.book = popUpService.getParam();
        $scope.bookList = dataService.getBookList();//用于筛选可用房间
        $scope.categoryTotal={};//统计各个房类开了多少13998244895yyzzfb zzjgdmz swdjz
        /*已选房间*/
        $scope.bookInputFields = [
            {name: '房号', id: 'roomId',width:'70px'},
            {name: '房价', id: 'roomPrice',width:'70px'},
            {name: '房类', id: 'category'}
            ];
        $scope.chooseRoomList=[];
        dataService.initData(['refreshRoomList', 'refreshRoomCategoryList','refreshProtocolList'])
            .then(function () {
                $scope.checkInList = dataService.getCheckInList();
                $scope.roomList = dataService.getRoomList();
                $scope.roomCategoryList = ['全部'].concat(util.objectListToString(dataService.getRoomCategoryList(), 'category'));
                $scope.roomCategory = $scope.roomCategoryList[0];
            });
        $scope.arrange = function () {
            var wrongMsg = '';
            for (var i = 0; i < $scope.book.bookRoomCategoryList.length; i++) {//遍历预定的房类
                var obj1 = $scope.book.bookRoomCategoryList[i];
                var available = bookRoomFilter($scope.roomList,obj1.roomCategory,$scope.bookList, $scope.book.reachTime, $scope.book.leaveTime);//该房类可用房数组
                var bookedNum = 0;
                for (var j = 0; j < available.length; j++) {
                    if (bookedNum == obj1.num) {
                        break;
                    }
                    var obj2 = available[j];
                    $scope.addOrDeleteRoom(obj2);
                    bookedNum++;
                }
                if (bookedNum < obj1.num) {
                    wrongMsg = wrongMsg + '预定' + obj1.roomCategory + ':' + obj1.num + '间只有' + bookedNum + '间可用';
                }
            }
            if (wrongMsg != '') {
                popUpService.pop('message');
                messageService.updateMessage({content: wrongMsg, type: 'alert'});
            }
        };
        $scope.addOrDeleteRoom = function (r, shiftKey) {
            if (!$scope.categoryTotal[r.category]){
                $scope.categoryTotal[r.category]=0;
            }
            if (shiftKey) {
                var j = $scope.roomList.indexOf(r);
                for (var i = j; i >= 0; i--) {
                    var id = '#bookInput' + $scope.roomList[i].roomId;
                    if (util.getValueByField($scope.chooseRoomList, 'roomId', $scope.roomList[i].roomId)) {
                        break;
                    } else {
                        $scope.chooseRoomList.push($scope.roomList[i]);
                        angular.element(id).addClass("hover");
                    }
                }
            } else {
                var id = "bookInput" + r.roomId;
                /*Liu created*/
                if (util.deleteFromArray($scope.chooseRoomList, 'roomId', r.roomId)) {
                    $scope.categoryTotal[r.category]--;
                    angular.element(document.getElementById(id)).removeClass("hover");
                    /*Liu created*/
                } else {
                    $scope.categoryTotal[r.category]++;
                    var room = {};
                    room.roomId = r.roomId;
                    var protocolObj = protocolService.getProtocolObj($scope.book.protocol, r.category, $scope.book.roomPriceCategory);
                    room.roomPrice = protocolObj.roomPrice;
                    room.category=r.category;
                    $scope.chooseRoomList.push(room);
                    angular.element(document.getElementById(id)).addClass("hover");
                    angular.element(document.getElementsByClassName('kong')).fadeIn("fast");
                }
            }
        };
        $scope.submitArrangeRoom = function () {
            for (var i = 0; i < $scope.book.bookRoomCategoryList; i++) {
                var obj = $scope.book.bookRoomCategoryList[i];
                if(obj.num!=$scope.categoryTotal[obj.category]){
                    messageService.setMessage({type:'error',content:'选择的房间数量与订单不一致'});
                    return
                }
            }
            var post={};
            post.bookRoomList=$scope.chooseRoomList
            post.bookRoomCategoryList=$scope.book.bookRoomCategoryList;
            webService.post('arrangeRoom',post)
                .then(function () {
                    popUpService.close('arrangeRoom');
                })
        }
    }]);