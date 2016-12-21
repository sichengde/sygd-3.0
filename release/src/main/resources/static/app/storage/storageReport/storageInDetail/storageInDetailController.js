App.controller('storageInDetailController', ['$scope', 'popUpService', 'util', function ($scope, popUpService, util) {
    var serial = popUpService.getParam();
    $scope.storageInCondition = 'storage_in_serial=' + util.wrapWithBrackets(serial);
    $scope.storageInDetailFields = [
        {name: '仓库', id: 'house', selectId: '0', itemChange: true, notNull: 'true'},
        {name: '货物', id: 'cargo', selectId: '1', itemChange: true, freeSelect: 'true', notNull: 'true'},
        {name: '类别', id: 'category', static: 'true'},
        {name: '单位', id: 'unit', static: 'true'},
        {name: '数量', id: 'num', itemChange: true, notNull: 'true'},
        {name: '单价', id: 'price', notNull: 'true'},
        {name: '金额合计', id: 'total', exp: 'num*price', static: 'true'},
        {name: '供应商', id: 'supplier', static: 'true'},
        {name: '生产日期', id: 'beginTime'},
        {name: '库存期限', id: 'endTime'},
        {name: '备注', id: 'remark'}
    ];
}]);