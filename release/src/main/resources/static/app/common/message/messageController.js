/**
 * Created by Administrator on 2016-07-22.
 */
/**
 * type类型分为
 * error:错误
 * alert:警告
 * choose:选择
 * success:成功
 */
App.controller('messageController',['$scope','messageService','popUpService',function ($scope,messageService,popUpService) {
    $scope.message=messageService.getMessage();
    $scope.now=new Date();
    $scope.ok=function () {
        popUpService.close('message');
        messageService.ok();
    };
    $scope.cancel=function () {
        popUpService.close('message');
        messageService.cancel();
    };
    $scope.close=function () {
        popUpService.close('message');
    };
    messageService.initMessageScope($scope);
}]);