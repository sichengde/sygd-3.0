/**
 * Created by Administrator on 2016-09-29.
 */
App.controller('cleanRoomController', ['$scope', 'popUpService', 'webService', 'messageService', 'dataService','util', function ($scope, popUpService, webService, messageService, dataService,util) {
    $scope.roomList = popUpService.getParam();
    dataService.refreshCleanRoomManList()
        .then(function () {
            $scope.userList = util.objectListToString(dataService.getCleanRoomManList(),'user');
        });
    $scope.cleanRoom = function () {
        var cleanRoomPost = {};
        cleanRoomPost.roomList = $scope.roomList;
        cleanRoomPost.userId = $scope.userId;
        webService.post('cleanRoom', cleanRoomPost)
            .then(function () {
                messageService.actionSuccess();
                popUpService.close('cleanRoom');
            });
    };
}]);