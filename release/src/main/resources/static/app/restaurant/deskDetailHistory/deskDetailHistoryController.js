/**
 * Created by Administrator on 2016-09-18.
 */
App.controller('deskDetailHistoryController', ['$scope', 'popUpService', 'util','dataService','webService', function ($scope, popUpService, util,dataService,webService) {
    $scope.param= popUpService.getParam();
    $scope.deskDetailHistoryFields = [
        {name: '菜品', id: 'foodName'},
        {name: '数量', id: 'num'},
        {name: '单价', id: 'price'},
        {name: '小计', id: 'total',sum:'true'}
    ];
    $scope.deskPayFields = [
        {name: '币种', id: 'currency'},
        {name: '实收金额', id: 'payMoney'}
    ];
    var deskDetailHistoryRefresh;
    $scope.deskDetailHistoryRefresh=function (f) {
        deskDetailHistoryRefresh=f;
    };
    if ($scope.param.length) {//只有数据，说明不是选的某个结账单，那么就丰富一下字段
        $scope.deskDetailHistoryList = $scope.param;
        $scope.deskDetailHistoryFields=$scope.deskDetailHistoryFields.concat([
            {name: '账单号', id: 'ckSerial'},
            {name: '营业部门', id: 'pointOfSale'},
            {name: '结账时间', id: 'doneTime'},
            {name: '菜品类别', id: 'category'}
        ]);
    } else {
        var p = {condition: 'ck_serial=' + util.wrapWithBrackets($scope.param.ckSerial)};
        dataService.initData(['refreshDeskDetailHistoryList','refreshDeskInHistoryList'], [p,p])
            .then(function () {
                $scope.deskDetailHistoryList = dataService.getDeskDetailHistoryList();
                var deskInHistory=dataService.getDeskInHistoryList()[0];
                $scope.deskPayRemark='总金额:'+deskInHistory.totalPrice+'  实收款:'+deskInHistory.finalPrice
            });
        $scope.deskPayCondition = 'ckSerial=' + util.wrapWithBrackets($scope.param.ckSerial);
    }
}]);