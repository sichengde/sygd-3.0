/**
 * Created by Administrator on 2016/7/27 0027.
 */
App.controller('addSubscriptionController',['$scope','popUpService','webService','dataService','util',function ($scope,popUpService,webService,dataService,util) {
    $scope.book=popUpService.getParam()[0];
    $scope.add=popUpService.getParam()[1];//增加订金还是退订金
    dataService.refreshCurrencyList({condition:'check_in=1'})
        .then(function () {
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.currency = '人民币';
        });
    $scope.addSubscription=function () {
        /*校验输入的金额*/
        $scope.book.subscription=$scope.add=='add'?Math.abs($scope.subscription):-Math.abs($scope.subscription);
        $scope.book.currency=$scope.currency;
        if($scope.book.bookSerial) {//客房的预定
            webService.post('addSubscription', $scope.book)
                .then(function () {
                    popUpService.close('addSubscription');
                })
        }else if($scope.book.deskBookSerial){//餐饮的预订
            webService.post('addSubscriptionDesk', $scope.book)
                .then(function () {
                    popUpService.close('addSubscription');
                })
        }
    }
}]);