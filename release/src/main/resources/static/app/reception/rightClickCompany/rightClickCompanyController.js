App.controller('rightClickCompanyController',['$scope','popUpService',function ($scope,popUpService) {
    var company=popUpService.getParam();
    $scope.clickPop=function (name) {
        popUpService.close('rightClickCompany',true);
        popUpService.pop(name,null,null,company);
    }
}]);