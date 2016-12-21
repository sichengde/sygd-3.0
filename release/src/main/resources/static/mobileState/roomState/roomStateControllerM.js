/**
 * Created by Administrator on 2016/7/31 0031.
 */
App.controller('roomStateControllerM', ['$scope', 'dataService', 'webService','receptionService','util', function ($scope, dataService, webService,receptionService,util) {
    $scope.roomDetail = function (r) {
        if (dataService.getRoomStateInList.indexOf(r.state)>-1) {
            receptionService.setChooseRoom(r);
            webService.redirect('app/roomDetail');
        }
    };
    $scope.doRefresh = function () {
        dataService.refreshRoomList({orderByList: ['roomId']})
            .then(function (r) {
                $scope.roomList = r;
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
        $scope.$broadcast('scroll.refreshComplete');
    };
    /*判断今天是否预离*/
    $scope.checkLeaveTime = function (leaveTime) {
        if (util.dateEqualsDay(new Date(leaveTime), new Date())) {
            return true
        } else {
            return false;
        }
    };
    $scope.doRefresh();
}]);
