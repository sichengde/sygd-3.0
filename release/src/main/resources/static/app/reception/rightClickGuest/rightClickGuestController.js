/**
 * Created by Administrator on 2016-09-05.
 */
App.controller('rightClickGuestController',['$scope','doorInterfaceService','webService','messageService','popUpService',function ($scope,doorInterfaceService,webService,messageService,popUpService) {
    $scope.guest=popUpService.getParam();
    $scope.debtDetail=function () {
        var idNumber=doorInterfaceService.getDoorIdNumber();
        if(idNumber){
            $scope.guest.doorId=idNumber;
            if($scope.guest.id) {//有id，需要上数据库中更新
                webService.post('checkInGuestUpdate', [$scope.guest])
                    .then(function () {
                        messageService.actionSuccess();
                    })
            }
        }
    }
}]);
