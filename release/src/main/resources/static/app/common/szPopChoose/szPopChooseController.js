App.controller('szPopChooseController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.chooseList=popUpService.getParam();
    $scope.confirm=function (name) {
        popUpService.close(name);
    }
}]);