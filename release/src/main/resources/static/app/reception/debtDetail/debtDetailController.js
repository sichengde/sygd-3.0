/**
 * Created by Administrator on 2016-06-29.
 */
App.controller('debtDetailController', ['$scope', 'dataService', 'util', 'receptionService', 'popUpService','webService', function ($scope, dataService, util, receptionService, popUpService,webService) {
    $scope.debtFields = [
        {name: '房号', id: 'roomId'},
        {name: '项目名称', id: 'description'},
        {name: '消费', id: 'consume', sum: 'true'},
        {name: '押金', id: 'deposit'},
        {name: '支付方式', id: 'currency'},
        {name: '入账时间', id: 'doTime'},
        {name: '备注', id: 'remark'},
        {name: '床位', id: 'bed'}
    ];
    var refreshDebt;
    $scope.refreshDebtTable = function (f) {
        refreshDebt = f;
    };
    $scope.param = popUpService.getParam();
    $scope.debtPayFields = [
        {name: '结账金额', id: 'debtMoney'},
        {name: '结账币种', id: 'currency'}
    ];
    var p;
    if ($scope.param.length) {//是数组，已经算出来了，不用再查询了，直接赋值
        $scope.debtList = $scope.param;
        $scope.debtFields.push({id: 'done_time', name: '结账时间'})
    } else if ($scope.param.paySerial) {//通过结账序列号查询
        p = {condition: 'pay_serial=' + util.wrapWithBrackets($scope.param.paySerial)};
        dataService.initData(['refreshDebtHistoryList'], [p])
            .then(function () {
                $scope.debtList = dataService.getDebtHistoryList();
                refreshDebt();
            });
        $scope.debtPayCondition = 'pay_serial=' + util.wrapWithBrackets($scope.param.paySerial);
        $scope.debtFields.push({id: 'done_time', name: '结账时间'})
    } else if ($scope.param.roomId) {//房间对象，查找在店的消费
        $scope.room = receptionService.getChooseRoom();
        p = {
            condition: 'room_id=' + util.wrapWithBrackets($scope.param.roomId),
            orderByListDesc: ['deposit']
        };
        dataService.initData(['refreshDebtList'], [p])
            .then(function () {
                $scope.debtList = dataService.getDebtList();
                refreshDebt();
            })
    }else if($scope.param.checkOutSerial){//根据离店序列号查找
        webService.post('debtHistoryGetByCheckOutSerial',{data:$scope.param.checkOutSerial})
            .then(function (r) {
                $scope.debtList=r;
                refreshDebt();
            });
        /*p = {condition: 'check_out_serial=' + util.wrapWithBrackets($scope.param.checkOutSerial)};
        dataService.refreshDebtPayList(p)
            .then(function (r) {

            });
        p = {condition: 'pay_serial=' + util.wrapWithBrackets($scope.param.paySerial)};
        dataService.initData(['refreshDebtHistoryList'], [p])
            .then(function () {
                $scope.debtList = dataService.getDebtHistoryList();
                refreshDebt();
            });
        $scope.debtPayCondition = 'pay_serial=' + util.wrapWithBrackets($scope.param.paySerial);
        $scope.debtFields.push({id: 'done_time', name: '结账时间'})*/
    }
    $scope.closePop = function () {
        popUpService.close('debtDetail');
    }
}]);