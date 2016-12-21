App.controller('loginController',['$scope','webService','util','messageService','popUpService',function ($scope,webService,util,messageService,popUpService) {
    $scope.login = function () {
        var p = {};
        p.condition = 'user_id=' + util.wrapWithBrackets($scope.user.userId) + ' and ' + 'password=' + util.wrapWithBrackets($scope.user.password);
        webService.post('cloudManager', p)
            .then(function (r) {
                if (r) {
                    webService.redirect('app/realState');
                } else {
                    messageService.setMessage({content: '用户名或密码错误！', type: 'error'});
                    popUpService.pop('message');
                }
            });
    }
}]);