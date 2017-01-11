/**
 * Created by Administrator on 2016-08-03.
 */
App.controller('rightClickDeskController', ['$scope', 'popUpService', 'webService', 'host','messageService','$location','LoginService', function ($scope, popUpService, webService, host,messageService,$location,LoginService) {
    $scope.deskInHistory = popUpService.getParam();
    $scope.debtTool=$location.path()=='/debtTool';
    $scope.userPermission=LoginService.getOwnPermissionList().indexOf('餐饮叫回')>-1;
    $scope.deskDetail = function () {
        popUpService.close('rightClickDesk');
        popUpService.pop('deskDetailHistory', null, null, {ckSerial:$scope.deskInHistory.ckSerial});
    };
    /*补打结账单*/
    $scope.guestOutPrintAgain = function () {
        popUpService.close('rightClickDesk');
        webService.post('deskOutPrintAgain', $scope.deskInHistory.ckSerial)
            .then(function (r) {
                window.open(host + "/receipt/" + r);
            })
    };
    /*账单分析*/
    $scope.deskParse=function () {
        popUpService.close('rightClickDesk');
        popUpService.pop('deskParse', null, null, {ckSerial:$scope.deskInHistory.ckSerial});
    };
    /*叫回结账单*/
    $scope.checkOutReverse = function () {
        popUpService.close('rightClickDesk');
        messageService.setMessage({content:'确认叫回账单:'+$scope.deskInHistory.ckSerial+'?'});
        messageService.actionChoose()
            .then(function () {
                webService.post('deskOutReverse', $scope.deskInHistory)
                    .then(function (r) {
                        if(r==0) {
                            window.location.reload();
                        }else if(r==1){
                            messageService.setMessage({type:'error',content:'该台有客人，无法叫回'});
                            popUpService.pop('message');
                        }
                    })
            })
    }
}]);
