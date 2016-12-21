/**
 * Created by Administrator on 2016-07-01.
 */
App.controller('cancelVipController',['$scope','webService','popUpService','util','messageService','dataService',function ($scope,webService,popUpService,util,messageService,dataService) {
    var vip=popUpService.getParam();
    $scope.vipNumber=vip.vipNumber;
    $scope.cancel=function () {
        var vipRemain=util.getValueByField(dataService.getVipList(),'vipNumber',$scope.vipNumber).remain;
        if(vipRemain>0){
            messageService.setMessage({type:'error',content:'余额不为0无法注销'});
            popUpService.pop('message');
            return;
        }
        webService.post('vipCancel',$scope.vipNumber)
            .then(function () {
                popUpService.close('cancel');
            })
    }
}]);