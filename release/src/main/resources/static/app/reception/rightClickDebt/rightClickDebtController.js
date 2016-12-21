/**
 * Created by Administrator on 2016-08-03.
 */
App.controller('rightClickDebtController', ['$scope', 'popUpService', 'webService', 'host','messageService','$location','LoginService', function ($scope, popUpService, webService, host,messageService,$location,LoginService) {
    $scope.debtPay = popUpService.getParam();//debtPay这个名字其实不太准确，因为不光是结账记录，有可能也是离店记录，但就这么用了
    $scope.debtTool=$location.path()=='/debtTool';
    $scope.userPermission=LoginService.getOwnPermissionList().indexOf('客房叫回')>-1;
    $scope.debtDetail = function () {
        popUpService.close('rightClickDebt');
        if($scope.debtPay.paySerial) {
            popUpService.pop('debtDetail', null, null, {paySerial: $scope.debtPay.paySerial})
        }else if($scope.debtPay.checkOutSerial){
            popUpService.pop('debtDetail', null, null, {checkOutSerial: $scope.debtPay.checkOutSerial})
        }
    };
    /*补打结账单*/
    $scope.guestOutPrintAgain = function () {
        popUpService.close('rightClickDebt');
        webService.post('guestOutPrintAgain', $scope.debtPay)
            .then(function (r) {
                window.open(host + "/receipt/" + r);
            })
    };
    /*叫回结账单*/
    $scope.checkOutReverse = function () {
        popUpService.close('rightClickDebt');
        messageService.setMessage({content:'确认叫回账单:'+$scope.debtPay.checkOutSerial+'?'});
        messageService.actionChoose()
            .then(function () {
                webService.post('checkOutReverse', $scope.debtPay)
                    .then(function () {
                        window.location.reload();
                    })
            })
    };
    /*哑房结账*/
    $scope.lostRoom=function () {
        popUpService.close('rightClickDebt');
        popUpService.pop('lostRoom',null,null,$scope.debtPay);
    }
}]);
