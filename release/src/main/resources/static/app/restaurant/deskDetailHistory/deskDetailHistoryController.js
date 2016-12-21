/**
 * Created by Administrator on 2016-09-18.
 */
App.controller('deskDetailHistoryController', ['$scope', 'popUpService', 'util','dataService','webService', function ($scope, popUpService, util,dataService,webService) {
    $scope.param= popUpService.getParam();
    $scope.deskDetailHistoryFields = [
        {name: '菜品', id: 'foodName'},
        {name: '数量', id: 'num'},
        {name: '单价', id: 'price'},
        {name: '小计', id: 'total',sum:'true'}
    ];
    $scope.deskPayFields = [
        {name: '币种', id: 'currency'},
        {name: '金额', id: 'payMoney'}
    ];
    var deskDetailHistoryRefresh;
    $scope.deskDetailHistoryRefresh=function (f) {
        deskDetailHistoryRefresh=f;
    };
    if ($scope.param.length) {
        $scope.deskDetailHistoryList = $scope.param;
    } else {
        var p = {condition: 'ck_serial=' + util.wrapWithBrackets($scope.param.ckSerial)};
        dataService.initData(['refreshDeskDetailHistoryList'], [p])
            .then(function () {
                $scope.deskDetailHistoryList = dataService.getDeskDetailHistoryList();
            });
        $scope.deskPayCondition = 'ckSerial=' + util.wrapWithBrackets($scope.param.ckSerial);
    }
}]);