/**
 * Created by Administrator on 2016-10-19.
 */
App.controller('exchangeUserReportControllerM', ['$scope', 'dataService', 'util', 'LoginService', 'webService', 'host', function ($scope, dataService, util, LoginService, webService, host) {
    dataService.initData(['refreshExchangeUserList', 'refreshUserList'])
        .then(function () {
            $scope.exchangeUserList = dataService.getExchangeUserList();
            $scope.userList = util.objectListToString(dataService.getUserList(), 'userId');
        });
    $scope.chooseExchangeUser = function (r) {
        if (r.beginTime < r.endTime) {
            $scope.beginTime = util.newDateAndTime(new Date(), r.beginTime);
            $scope.endTime = util.newDateAndTime(new Date(), r.endTime);
        } else {
            $scope.beginTime = util.newDateAndTime(new Date(new Date() - 24 * 60 * 60 * 1000), r.beginTime);
            $scope.endTime = util.newDateAndTime(new Date(), r.endTime);
        }
    };
    $scope.exchangeUserReport = function (userId, beginTime, endTime, format) {
        /*Safari浏览器只能这么解决*/
        var winRef = window.open("");
        webService.post('exchangeUserReport', {userId: userId, beginTime: beginTime, endTime: endTime, format: format})
            .then(function (r) {
                winRef.location = host + "/receipt/" + r.reportJson.reportIndex;
            });
    };
}]);
