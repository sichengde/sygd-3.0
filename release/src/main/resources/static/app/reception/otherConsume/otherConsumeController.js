/**
 * Created by Administrator on 2016-05-03.
 */
App.controller('OtherConsumeController', ['$scope', 'webService', 'dataService', 'receptionService', 'popUpService', 'host', 'util','messageService', function ($scope, webService, dataService, receptionService, popUpService, host, util,messageService) {
    var debt = {};
    $scope.company = popUpService.getParam();
    $scope.room = receptionService.getChooseRoom();
    dataService.refreshPointOfSaleList({condition: 'module=\'接待\''})
        .then(function (r) {
            $scope.pointOfSaleList = r[0].secondPointOfSale.split(' ');
            $scope.pointOfSale = $scope.pointOfSaleList[0];
        });
    if ($scope.company&&$scope.company.name) {
        dataService.refreshCompanyLordList({condition: 'company=' + util.wrapWithBrackets($scope.company.name)})
            .then(function (r) {
                $scope.companyLordListShow = util.objectListToString(r, 'name');
            })
    }
    $scope.remark = '';

    $scope.addDebt = function () {
        if (!$scope.company.name) {//客房挂账
            debt.pointOfSale = $scope.pointOfSale;
            debt.currency = '挂账';
            if ($scope.manualConsume == '冲账') {
                debt.consume = -Math.abs($scope.consume);
            } else {
                debt.consume = Math.abs($scope.consume);
            }
            debt.description = $scope.manualConsume + ':' + $scope.remark;
            debt.roomId = $scope.room.roomId;
            return webService.post('otherConsumeRoom', debt)
                .then(
                    function (r) {
                        popUpService.close('otherConsume');
                        /*弹出打印预览界面*/
                        window.open(host + "/receipt/" + r);
                    }
                )
        } else {//单位挂账
            var debtHistory = {};
            debtHistory.doTime = new Date();
            debtHistory.doneTime = debtHistory.doTime;
            debtHistory.pointOfSale = $scope.pointOfSale;
            if ($scope.manualConsume == '冲账') {
                debtHistory.consume = -Math.abs($scope.consume);
            } else {
                debtHistory.consume = Math.abs($scope.consume);
            }
            debtHistory.category = $scope.manualConsume;
            debtHistory.currency = $scope.company.name;
            debtHistory.currencyAdd = $scope.companyLord;
            debtHistory.description = '单位' + $scope.manualConsume + '/' + $scope.companyLord + ':' + $scope.remark;
            return webService.post('otherConsumeCompany', debtHistory)
                .then(function (d) {
                    messageService.actionSuccess();
                    /*弹出打印预览界面*/
                    window.open(host + "/receipt/" + d);
                    popUpService.close('otherConsume');
                });
        }
    };

}]);