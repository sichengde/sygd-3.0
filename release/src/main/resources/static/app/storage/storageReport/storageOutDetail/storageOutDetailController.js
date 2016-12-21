App.controller('storageOutDetailController',['$scope','popUpService','util',function ($scope,popUpService,util) {
    var serial = popUpService.getParam();
    $scope.storageOutCondition = 'storage_out_serial=' + util.wrapWithBrackets(serial);
    $scope.storageOutDetailFields = [
        {name: '仓库', id: 'house', selectId: '0', itemChange: true, notNull: 'true'},
        {name: '货物', id: 'cargo', selectId: '1', itemChange: true, freeSelect: 'true', notNull: 'true'},
        {name: '类别', id: 'category', static: 'true'},
        {name: '单位', id: 'unit', static: 'true'},
        {name: '数量', id: 'num', itemChange: true, notNull: 'true'},
        {name: '单价', id: 'price'},
        {name: '金额合计', id: 'total', exp: 'num*price', static: 'true'},
        {name: '用途', id: 'myUsage'}
    ];
}]);