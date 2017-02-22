/**
 * Created by Administrator on 2016-05-03.
 */
App.controller('OtherConsumeController', ['$scope', 'webService', 'dataService', 'receptionService', 'popUpService', 'host', function ($scope, webService, dataService, receptionService, popUpService, host) {
    var debt = {};
    $scope.company=popUpService.getParam();
    $scope.room = receptionService.getChooseRoom();
    dataService.refreshPointOfSaleList({condition: 'module=\'接待\''})
        .then(function (r) {
            $scope.pointOfSaleList = r[0].secondPointOfSale.split(' ');
            $scope.pointOfSale = $scope.pointOfSaleList[0];
        });
    $scope.remark = '';

    $scope.addDebt = function () {
        debt.pointOfSale = $scope.pointOfSale;
        debt.currency = '挂账';
        if ($scope.manualConsume == '冲账') {
            debt.consume = -Math.abs($scope.consume);
        } else {
            debt.consume = Math.abs($scope.consume);
        }
        debt.description = $scope.manualConsume + ':' + $scope.remark;
        debt.roomId = $scope.room.roomId;
        webService.post('otherConsumeRoom', debt)
            .then(
                function (r) {
                    popUpService.close('otherConsume');
                    /*弹出打印预览界面*/
                    window.open(host + "/receipt/" + r);
                }
            )
    };

}]);