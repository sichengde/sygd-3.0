App.controller('rightClickCompanyDebtController',['$scope','popUpService',function ($scope,popUpService) {
    var companyDebt=popUpService.getParam();
    /*现实消费明细，分为，接待，餐饮，桑拿三种*/
    $scope.showDetail=function () {
        /*判断首字母p是接待，c是餐饮，s是桑拿*/
        switch (companyDebt.paySerial[0]){
            case 'p':
                popUpService.close('rightClickCompanyDebt',true);
                popUpService.pop('debtDetail', null, null, {paySerial: companyDebt.paySerial});
                break;
            case 'c':
                popUpService.close('rightClickCompanyDebt',true);
                popUpService.pop('deskDetailHistory', null, null, {ckSerial:companyDebt.paySerial});
                break;
            case 's':
                break;
        }
    }
}]);