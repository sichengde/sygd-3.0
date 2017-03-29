App.controller('popCompanyGenerateController', ['$scope', 'fieldService', 'webService', 'popUpService', 'util', 'dateFilter', function ($scope, fieldService, webService, popUpService, util, dateFilter) {
    $scope.reportJson = popUpService.getParam().reportJson;
    $scope.debtIntegrationFields = fieldService.getDebtIntegrationFields();
    $scope.deskPayRichFields = fieldService.getDeskPayRichFields(true);
    webService.post('getCompanyGenerateDetail', $scope.reportJson)
        .then(function (r) {
            $scope.debtIntegrationList = r;
        });
    var query = {};
    query.condition = 'currency=\'转单位\' and do_time>' + util.wrapWithBrackets(dateFilter($scope.reportJson.beginTime, 'yyyy-MM-dd HH:mm:ss')) + ' and do_time<' + util.wrapWithBrackets(dateFilter($scope.reportJson.endTime, 'yyyy-MM-dd HH:mm:ss'));
    webService.post('deskPayRichGet', query)
        .then(function (r) {
            $scope.deskPayRichList = r;
        });
    $scope.closePop = function () {
        popUpService.close('popCompanyGenerate');
    }
}]);