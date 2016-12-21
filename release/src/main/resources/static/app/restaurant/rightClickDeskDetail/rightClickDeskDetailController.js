/**
 * Created by Administrator on 2016/9/13 0013.
 */
App.controller('rightClickDeskDetailController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.deskDetail=popUpService.getParam();
    /*退菜*/
    $scope.cancelMenu=function () {
        popUpService.close('rightClickDeskDetail');
        $scope.$emit('cancelMenu',$scope.deskDetail);
    };
    /*套餐明细*/
    $scope.showFoodSet=function () {
        popUpService.close('rightClickDeskDetail');
        popUpService.pop('foodSetDetail',$scope.deskDetail.foodName);
    }
}]);