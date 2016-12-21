/**
 * Created by Administrator on 2016/9/28 0028.
 */
App.controller('lostRoomController',['$scope','popUpService','webService','messageService',function ($scope,popUpService,webService,messageService) {
    $scope.currencyPayList = [];
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    var debtPay=popUpService.getParam();
    $scope.totalConsume=$scope.currencyPayList[0].money=debtPay.debtMoney;
    $scope.lostRoomCheckOut=function () {
        var lostRoom={};
        lostRoom.currencyPostList=$scope.currencyPayList;
        lostRoom.debtPay=debtPay;
        webService.post('lostRoomCheckOut',lostRoom)
            .then(function () {
                messageService.actionSuccess();
                popUpService.close('lostRoom');
            })
    }
}]);