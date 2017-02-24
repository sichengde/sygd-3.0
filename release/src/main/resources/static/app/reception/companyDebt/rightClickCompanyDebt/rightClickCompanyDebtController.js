App.controller('rightClickCompanyDebtController',['$scope','popUpService','messageService',function ($scope,popUpService,messageService) {
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
                /*判断二字母是不是p，p是单位结算*/
                if(companyDebt.paySerial[1]=='p'){
                    messageService.setMessage({type:'alert',content:'单位转入明细请根据结账流水号在结算明细中查找'})
                    popUpService.pop('message');
                    return;
                }
                popUpService.close('rightClickCompanyDebt',true);
                popUpService.pop('deskDetailHistory', null, null, {ckSerial:companyDebt.paySerial});
                break;
            case 's':
                break;
        }
    }
}]);