/**
 * Created by Administrator on 2016/8/8 0008.
 */
App.controller('changeRoomController', ['$scope', 'dataService', 'roomFilter', 'messageService', 'webService', 'receptionService', 'popUpService', 'dateFilter', 'protocolService', 'doorInterfaceService', function ($scope, dataService, roomFilter, messageService, webService, receptionService, popUpService, dateFilter, protocolService, doorInterfaceService) {
    $scope.currentRoom = receptionService.getChooseRoom();
    $scope.editableRoomPrice = dataService.getOtherParamMapValue('可编辑房价') == 'y';
    dataService.initData(['refreshRoomList'])
        .then(function () {
            $scope.roomList = roomFilter(dataService.getRoomList(), dataService.getRoomStateAvailableList(), '全部');
        });
    if (!$scope.editableRoomPrice) {
        dataService.refreshProtocolList();
    } else {//可编辑房价的话初始化目标房价
        $scope.targetRoomPrice = $scope.currentRoom.checkIn.finalRoomPrice;
    }
    $scope.submitChangeRoom = function (room) {
        if (!$scope.editableRoomPrice) {//不是可编辑房价，才用调目标协议价
            var protocol = protocolService.getProtocolObj($scope.currentRoom.checkIn.protocol, room.category, $scope.currentRoom.checkIn.roomPriceCategory);
        }
        var targetRoomPrice = $scope.editableRoomPrice ? $scope.targetRoomPrice : protocol.roomPrice;
        messageService.setMessage({content: '确认换入' + room.roomId + '?换入后房价为:' + targetRoomPrice});
        messageService.actionChoose()
            .then(function () {
                /*赋值房价，把checkIn的finalRoomPrice设置为新的房价*/
                room.price = targetRoomPrice;
                webService.post('changeRoom', [$scope.currentRoom, room])
                    .then(function () {
                        messageService.actionSuccess();
                        var num = 1;//做几张房卡
                        if (dataService.getOtherParamMapValue('按人数发卡') == 'y') {
                            num = $scope.checkInGuestList.length;
                        }
                        doorInterfaceService.doorWrite([room.roomId], dateFilter($scope.leave, 'yyyyMMddHHmmss'), [num])
                            .then(function () {
                                popUpService.close('changeRoom');
                            })
                    })
            })
    };
    /*考虑到换房发卡可能失败（谷歌浏览器），那样就不刷新盘态了，所以关闭加上强制刷新*/
    $scope.closePopRefresh = function () {
        popUpService.close('changeRoom', false);
    }
}]);