/**
 * Created by Administrator on 2016-12-07.
 */
App.controller('roomDetailControllerMM', ['$scope', 'webService', 'receptionService', 'dataService', 'util', function ($scope, webService, receptionService, dataService, util) {
    $scope.room = receptionService.getChooseRoom();
    var p = {condition: 'room_id=' + util.wrapWithBrackets($scope.room.roomId)};
    dataService.initData(['refreshDebtList', 'refreshCheckInGuestList'], [p, p])
        .then(function () {
            $scope.debtList = dataService.getDebtList();
            $scope.checkInGuestList = dataService.getCheckInGuestList();
            $scope.checkInGuestName = util.objectListToString($scope.checkInGuestList, 'name').join();
        })
}]);
