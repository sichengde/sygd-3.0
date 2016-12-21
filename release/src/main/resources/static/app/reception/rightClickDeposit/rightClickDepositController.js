/**
 * Created by Administrator on 2016/9/9 0009.
 */
App.controller('rightClickDepositController',['$scope','webService','popUpService','host',function ($scope,webService,popUpService,host) {
    $scope.debt=popUpService.getParam();
    $scope.depositPrint=function () {
        popUpService.close('rightClickDeposit');
        webService.post('depositPrintAgain',$scope.debt)
            .then(function (r) {
                window.open(host + "/receipt/" + r);
            })
    }
}]);