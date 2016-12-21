/**
 * Created by Administrator on 2016/7/31 0031.
 */
App.controller('checkOutDetailReportControllerM', ['$scope', 'dataService', 'util', 'LoginService', 'webService', 'host', function ($scope, dataService, util, LoginService, webService, host) {
    $scope.beginTime=new Date();
    $scope.endTime=new Date();
    $scope.checkOutDetail = function (userId, beginTime, endTime, format) {
        var winRef = window.open("");
        webService.post('checkOutDetailReport', {userId: userId, beginTime: beginTime, endTime: endTime, format: format })
            .then(function (r) {
                winRef.location = host + "/receipt/" + r;
            });
    };
}]);
