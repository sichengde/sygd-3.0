/**
 * Created by 刘丹 on 2016-08-05.
 */

App.controller('bookListController',['$scope', 'dataService','webService', function ($scope, dataService,webService) {
    $scope.bookDetail=function () {
        webService.redirect('app/bookUpdate');
    };
}]);