App.controller('rightClickStorageInOutController',['$scope','popUpService',function ($scope,popUpService) {
    var item=popUpService.getParam();
    $scope.showDetail=function () {
        popUpService.close('rightClickStorageInOut');
        if(item.inTime){//是入库明细
            popUpService.pop('storageInDetail',null,null,item.storageInSerial);
        }else {//出库明细
            popUpService.pop('storageOutDetail',null,null,item.storageOutSerial);
        }
    }
}]);