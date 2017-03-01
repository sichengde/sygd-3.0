App.controller('storageOutSpecifyController', ['$scope', 'popUpService','messageService', function ($scope, popUpService,messageService) {
    $scope.storageInDetailList = popUpService.getParam();
    var backUp = angular.copy(popUpService.getParam());
    $scope.backUp=function () {
        $scope.storageInDetailList=backUp;
    }
    $scope.storageInDetailFields = [
        {name: '仓库', id: 'house', selectId: '0', itemChange: true, notNull: 'true', static: 'true'},
        {name: '货物', id: 'cargo', selectId: '1', itemChange: true, freeSelect: 'true', notNull: 'true', static: 'true'},
        {name: '类别', id: 'category', static: 'true'},
        {name: '单位', id: 'unit', static: 'true'},
        {name: '余量', id: 'remain', itemChange: true, notNull: 'true', static: 'true'},
        {name: '单价', id: 'price', notNull: 'true', static: 'true'},
        {name: '金额合计', id: 'total', exp: 'num*price', static: 'true'},
        {name: '供应商', id: 'supplier', static: 'true'},
        {name: '生产日期', id: 'beginTime', static: 'true'},
        {name: '库存期限', id: 'endTime', static: 'true'},
        {name: '备注', id: 'remark', static: 'true'},
        {name: '出库', id: 'out', boolean: 'true'},
        {name: '出库数量', id: 'outNum',notNull:'zero',itemChange:true}
    ];
    $scope.storageInDetailSpecifyChange=function (item, field) {
        if(field.id=='outNum'){
            if(item.outNum>item.remain){
                messageService.setMessage({type:'error',content:'余量只有:'+item.remain+item.unit});
                popUpService.pop('message');
                item.outNum=item.remain;
            }
        }
    };
    $scope.commit=function () {
        popUpService.close('storageOutSpecify');
    }
}]);