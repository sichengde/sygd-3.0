/**
 * Created by Administrator on 2016-07-27.
 */
App.controller('cancelDepositController', ['$scope', 'popUpService', 'util', 'webService', 'dataService', 'messageService', function ($scope, popUpService, util, webService, dataService, messageService) {
    var param = popUpService.getParam();
    var backup;//备份押金列表，用于比较删除了哪些
    if (param.room) {//按照房号退预付
        dataService.initData(['refreshDebtList'], [{condition: 'room_id=' + util.wrapWithBrackets(param.room) + ' and ifnull(deposit,0)>0 and (remark !=\'已退\' or remark is null)'}])
            .then(function () {
                $scope.depositList = dataService.getDebtList();
                backup = angular.copy($scope.depositList);
            })
    }
    $scope.depositList = [];
    $scope.debtFields = [
        {name: '房号', id: 'roomId', width: '70px'},
        {name: '金额', id: 'deposit', width: '70px'},
        {name: '币种', id: 'currency', width: '70px'},
        {name: '时间', id: 'doTime', width: '180px'},
        {name: '备注', id: 'remark', width: '200px'}
    ];
    /*确认退掉这些预付*/
    $scope.confirmDelete = function () {
        var debtList = util.getDeletedArray(backup, $scope.depositList, 'id');//用于提交插入的预付负值
        if (debtList.length == 0) {
            messageService.setMessage({type: 'alert', content: '似乎没有选择任何条目，如果选择了删除请先点确认再提交'});
            popUpService.pop('message');
            return;
        }
        webService.post('cancelDeposit', debtList)
            .then(function () {
                popUpService.close('cancelDeposit');
            })
    };
    /*关闭窗口，并且刷新*/
    $scope.closeThis = function () {
        popUpService.close('cancelDeposit');
    }
}]);
