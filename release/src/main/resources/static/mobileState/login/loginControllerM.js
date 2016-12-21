/**
 * Created by Administrator on 2016/7/31 0031.
 */
App.controller('loginControllerM', ['$scope', '$ionicPopup', 'dataService', 'util', 'webService','LoginService', function ($scope, $ionicPopup, dataService, util, webService,LoginService) {
    $scope.login = function () {
        /*云端登录*/
        /*dataService.refreshUserList({condition: 'user_id=' + util.wrapWithBrackets($scope.userId) + ' and ' + 'password=' + util.wrapWithBrackets($scope.password)})
            .then(function (r) {
                if (r.length == 0) {
                    $ionicPopup.alert({
                        title: '登录失败',
                        template: '用户名或密码错误'
                    });
                } else {
                    LoginService.setUser($scope.userId);
                    webService.redirect('app/roomState');
                }
            })*/
        /*本地登录*/
        webService.post('userLoginPhone',{condition: 'user_id=' + util.wrapWithBrackets($scope.userId) + ' and ' + 'password=' + util.wrapWithBrackets($scope.password)})
            .then(function (r) {
                if (!r) {
                    $ionicPopup.alert({
                        title: '登录失败',
                        template: '用户名或密码错误'
                    });
                } else {
                    LoginService.setUser($scope.userId);
                    webService.redirect('app/roomState');
                }
            })
    };
}]);
