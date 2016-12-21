/**
 * Created by Administrator on 2016-05-30.
 */
App.controller('rightClickRoomController', ['$scope', 'popUpService', 'receptionService', 'webService', 'messageService','LoginService', function ($scope, popUpService, receptionService, webService, messageService,LoginService) {
    var room = receptionService.getChooseRoom();
    $scope.clickPop = function (name) {
        popUpService.close('rightClickRoom', true);
        popUpService.pop(name,null,null,room);
    };
    $scope.clearRoom = function () {
        popUpService.pop('cleanRoom',null,null,[room]);
        popUpService.close('rightClickRoom', true);
    };
    if (receptionService.inRoomList.indexOf(room.state) > -1) {
        $scope.showIn = true;//房间为在店时显示
    }
    if (room.state == '散客房') {
        $scope.selfAccount = true;
    }
    if (room.state == '团队房') {
        $scope.groupAccount = true;
    }
    if (room.dirty == '1' || room.state == '走客房') {
        $scope.dirty = true;
    }
    $scope.todayLock = !!room.todayLock;
    $scope.todayUnLock = function () {
        room.todayLock=null;
        webService.post('roomUpdate',[room])
            .then(function () {
                popUpService.close('rightClickRoom');
            })
    }
}]);