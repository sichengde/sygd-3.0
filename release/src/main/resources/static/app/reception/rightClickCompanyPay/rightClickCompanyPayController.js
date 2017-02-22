App.controller('rightClickCompanyPayController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.pay=popUpService.getParam();
    $scope.clickPop=function (name) {
        popUpService.pop(name);
    }
}]);