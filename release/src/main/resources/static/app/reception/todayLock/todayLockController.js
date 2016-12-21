/**
 * Created by Administrator on 2016-09-28.
 */
App.controller('todayLockController',['$scope','webService','popUpService',function ($scope,webService,popUpService) {
    var room=popUpService.getParam();
    $scope.todayLock=function () {
        room.todayLock=$scope.reason;
        webService.post('roomUpdate',[room])
            .then(function () {
                popUpService.close('todayLock');
            })
    }
}]);