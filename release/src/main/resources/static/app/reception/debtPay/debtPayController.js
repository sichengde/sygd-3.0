/**
 * Created by Administrator on 2016/9/25 0025.
 */
App.controller('debtPayController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.param=popUpService.getParam();
    $scope.debtPayList=$scope.param;
    $scope.debtPayFields = [
        {name: '结账时间', id: 'doneTime', date: 'true', width: '120px', filter: 'date', desc: '0'},
        {name: '结账金额', id: 'debtMoney', width: '100px' ,sum:'true'},
        {name: '币种', id: 'currency', width: '100px', filter: 'list'},
        {name: '单位名称', id: 'company', width: '230px', filter: 'list'},
        {name: '主账号', exp: 'groupAccount?groupAccount:selfAccount', width: '150px'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '离店流水号', id: 'checkOutSerial', width: '150px'},
        {name: '结账类型', id: 'debtCategory', width: '150px', filter: 'list'},
        {name: '操作员', id: 'userId', width: '100px'}
    ];
}]);