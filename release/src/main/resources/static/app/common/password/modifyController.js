/**
 * Created by Administrator on 2016/7/25 0025.
 */
App.controller('modifyController',['$scope','LoginService','webService','popUpService',function ($scope,LoginService,webService,popUpService) {
    $scope.user=LoginService.getUserObj();
    $scope.modifyPassword=function () {
        $scope.user.password=$scope.newPassword;
        webService.post('userUpdate',[$scope.user])
            .then(function () {
                popUpService.close('modify');
            })
    };
}]);