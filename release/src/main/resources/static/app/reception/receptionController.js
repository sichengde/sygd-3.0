/**
 * 主页控制器，主要负责初始化所有经常用到的系统参数,Service负责从服务器索取数据，Util提供一些本模块用到的方法
 */
App.controller('ReceptionController', ['$scope', 'dataService', 'popUpService', 'receptionService', 'util', 'dateFilter', 'messageService', function ($scope, dataService, popUpService, receptionService, util, dateFilter, messageService) {

    /*鼠标移动到盘态--用于右键菜单确定房间对象*/
    $scope.mouseEnter = function (r) {
        var room=angular.copy(r);
        receptionService.setChooseRoom(room);
    };
    /*刷新全部房态按钮*/
    $scope.refresh = function () {
        var p = {orderByList: ['roomId']};
        dataService.initData(['refreshRoomList', 'refreshRoomCategoryList'], [p])
            .then(function () {
                $scope.roomCategoryList = ['全部'].concat(util.objectListToString(dataService.getRoomCategoryList(), 'category'));
                $scope.roomCategory = $scope.roomCategoryList[0];
                $scope.roomStateList = ['全部'].concat(dataService.getRoomStateList);
                $scope.roomState = $scope.roomStateList[0];
                $scope.roomList = dataService.getRoomList();    
                $scope.areaList=util.objectListToString($scope.roomList, 'area');
                $scope.floorList=util.objectListToString($scope.roomList, 'floor');
                $scope.areaChoose=angular.copy($scope.areaList);
                $scope.floorChoose=angular.copy($scope.floorList);
                /*计算出当前的房间信息*/
                $scope.roomMessage = {};
                $scope.roomMessage.totalRoom = $scope.roomList.length;
                $scope.roomMessage.dirtyRoom = 0;
                $scope.roomMessage.availableRoom = 0;
                $scope.roomMessage.leaveRoom = 0;
                $scope.roomMessage.bookRoom = 0;
                $scope.roomMessage.roominUse = 0;
                for (var i = 0; i < $scope.roomList.length; i++) {
                    var room = $scope.roomList[i];
                    if (room.dirty == 1) {
                        $scope.roomMessage.dirtyRoom++;
                    }
                    if (room.state == '可用房') {
                        $scope.roomMessage.availableRoom++;
                    }
                    if (room.state == '走客房') {
                        $scope.roomMessage.leaveRoom++;
                    }
                    if (room.bookList) {
                        $scope.roomMessage.bookRoom++;
                    }
                    if (dataService.getRoomStateInList.indexOf(room.state) > -1) {
                        $scope.roomMessage.roominUse++;
                    }
                }
                $scope.roomMessage.roomRate = $scope.roomMessage.roominUse / $scope.roomMessage.totalRoom * 100;
            });
    };
    /*鼠标房态图标，根据房态智能选择弹出的窗口*/
    $scope.clickGuestIn = function (r) {
        receptionService.setChooseRoom(angular.copy(r));
        if (r.state == '可用房' || r.state == '走客房') {
            if (dataService.getOtherParamMapValue('脏房可否开房') == 'n' && r.state == '走客房') {
                messageService.setMessage({type: 'alert', content: '脏房不可以开房，如需修改，请到系统维护其他参数中修改'});
                popUpService.pop('message');
                return;
            }
            if(r.bookList){//有预定
                if(24*60*60*1000>r.bookList[0].reachTime-new Date()>-60*60*1000){
                    messageService.setMessage({content:'未来24小时内该房有预定，是否确认开房?'});
                    messageService.actionChoose()
                        .then(function () {
                            popUpService.pop('guestIn');
                        })
                }else {
                    popUpService.pop('guestIn');
                }
            }else {
                popUpService.pop('guestIn');
            }
        } else if (r.state == '团队房' || r.state == '散客房') {
            popUpService.pop('guestOut');
        }
    };
    /*计算出客人姓名字符串*/
    $scope.guestName = function (r) {
        if (!r) {
            return;
        }
        return util.objectListToString(r, 'name').join(' ');
    };
    /*判断在店户籍的生日是否是今天*/
    $scope.checkBirthday = function (checkInGuestList) {
        /*没有开房的房间*/
        if (!checkInGuestList) {
            return false;
        }
        /*开了的有在店宾客的*/
        for (var i = 0; i < checkInGuestList.length; i++) {
            var checkInGuest = checkInGuestList[i];
            if (util.dateEqualsDay(new Date(checkInGuest.birthdayTime), new Date())) {
                return true
            }
        }
        return false;
    };
    /*判断今天是否预离*/
    $scope.checkLeaveTime = function (leaveTime) {
        if (util.dateEqualsDay(new Date(leaveTime), new Date())) {
            return true
        } else {
            return false;
        }
    };
    /*先刷新一下*/
    $scope.refresh();

    /*选择楼栋或者楼层*/
    $scope.clickArea=function (r,shiftKey) {
        if (shiftKey) {
            var j = $scope.areaList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                if ($scope.areaChoose.indexOf($scope.areaList[i])) {
                    break;
                } else {
                    $scope.clickArea($scope.areaList[i]);
                }
            }
        } else {
            var index=$scope.areaChoose.indexOf(r);
            if(index>-1){
                $scope.areaChoose.splice(index,1);
            } else {
                $scope.areaChoose.push(r);
            }
        }
        angular.element(".lpan").fadeIn('slow');
    };
    $scope.clickFloor=function (r,shiftKey) {
        if (shiftKey) {
            var j = $scope.floorList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                if ($scope.floorChoose.indexOf($scope.floorList[i])) {
                    break;
                } else {
                    $scope.clickFloor($scope.floorList[i]);
                }
            }
        } else {
            var index=$scope.floorChoose.indexOf(r);
            if(index>-1){
                $scope.floorChoose.splice(index,1);
            } else {
                $scope.floorChoose.push(r);
            }
        }
        angular.element(".lcan").fadeIn('slow');
    };

    /*点击样式，同一团队浮动*/
    $scope.receptionClick = function (r) {
        if (r.checkIn) {
            angular.forEach($scope.roomList, function (item) {
                item.hover = null;
                if (r.checkIn.groupAccount) {
                    if (item.checkIn) {
                        if (item.checkIn.groupAccount == r.checkIn.groupAccount) {
                            item.hover = 'hover';
                        }
                    }
                }
            });
            r.hover = 'hover';
        }
        else {
            angular.forEach($scope.roomList, function (item) {
                item.hover = null;
            })
        }
    }
}]);
