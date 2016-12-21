App.controller('realStateController', ['$scope', 'popUpService','receptionService','dataService','LoginService', function ($scope, popUpService,receptionService,dataService,LoginService) {
    var p = {condition: 'module=\'餐饮\''};
    dataService.initData(['refreshPointOfSaleList'], [p])
        .then(function () {
            $scope.pointOfSaleList = dataService.getPointOfSale();
        });
    $scope.choosePointOfSale=function (pointOfSale) {
        LoginService.setPointOfSale(pointOfSale);
    };
    /*查看客房消费明细*/
    $scope.queryDebtDetail = function () {
        var room = receptionService.getChooseRoom();
        if(room.checkIn) {
            popUpService.pop('debtDetail', null, null, receptionService.getChooseRoom());
        }
    };
    /*查看餐饮消费明细*/
    $scope.queryDeskDetail = function (desk) {
        desk.onlyView=true;
        popUpService.pop('deskDetail', null, null, desk);
    };
}]);