App.controller('popCompanyDebtController', ['$scope', 'popUpService', function ($scope, popUpService) {
    /*等待核对*/
    $scope.condition = popUpService.getParam();
    $scope.companyDebtFields = [
        {name: '单位名称', id: 'company', width: '230px'},
        {name: '签单人', id: 'lord', width: '100px'},
        {name: '结账时间', id: 'doTime', date: 'true', width: '100px', desc: '0', filter: 'date'},
        {name: '挂账款', id: 'debt', width: '100px', sum: 'true'},
        {name: '剩余挂账', id: 'currentRemain', width: '100px'},
        {name: '币种', id: 'currency', width: '150px', filter: 'list'},
        {name: '币种信息', id: 'currencyAdd', width: '150px', filter: 'list'},
        {name: '操作类别', id: 'category', width: '150px', filter: 'list'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '备注', id: 'description', width: '150px'},
        {name: '营业部门', id: 'pointOfSale', width: '150px'},
        {name: '操作员', id: 'userId', width: '120px'}
    ];
    $scope.closePop = function () {
        popUpService.close('popCompanyDebt');
    }
}]);