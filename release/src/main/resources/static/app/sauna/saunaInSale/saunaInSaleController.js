App.controller('saunaInSaleController',['$scope','dataService','util','popUpService','webService','messageService',function ($scope,dataService,util,popUpService,webService,messageService) {
    var post=popUpService.getParam();
    $scope.saunaInSaleList=post.saunaInRowList;
    $scope.backUp=angular.copy($scope.saunaInSaleList);
    $scope.saunaInSaleSelectList=[];
    dataService.refreshInCategoryList()
        .then(function (r) {
            $scope.inCategoryList=['正常'].concat(util.objectListToString(r,'category'));
            $scope.inCategory='正常';
        });
    dataService.refreshSaunaMenuList({condition:'category=\'门票\''})
        .then(function (r) {
            $scope.saunaInSaleSelectList[0]=util.objectListToString(r,'name');
        });
    $scope.saunaInSaleFields=[
        {name:'手牌号',id:'ringNumber',static:'true'},
        {name:'浴资',id:'menu',selectId:'0'}
    ];
    $scope.saunaIn=function () {
        post.inCategory=$scope.inCategory;
        webService.post('saunaIn',post)
            .then(function () {
                messageService.actionSuccess();
                popUpService.close('saunaInSale');
            })
    }
}]);