/**
 * Created by Administrator on 2016-07-06.
 */
App.controller('retailController', ['$scope', '$interval', 'util', 'popUpService', 'saleService', 'dataService', 'webService', 'messageService', 'host', 'LoginService', function ($scope, $interval, util, popUpService, saleService, dataService, webService, messageService, host, LoginService) {
    /*初始化分单数组，默认不分担，值为1，只有一个币种*/
    $scope.currencyPayList = [];
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.roomShopFields = [
        {name: '销售点', id: 'pointOfSale'},
        {name: '品种', id: 'category'},
        {name: '商品名称', id: 'item'},
        {name: '价格', id: 'price'}
    ];
    $scope.chooseItemFields = [
        {name: '品种', id: 'category', width: '100px', static: 'true'},
        {name: '商品名称', id: 'item', width: '150px', static: 'true'},
        {name: '价格', id: 'price', width: '70px', static: 'true'},
        {name: '数量', id: 'num', width: '70px'}
    ];
    $scope.condition = "pointOfSale='房吧'";
    $scope.chooseItem = saleService.chooseItem;
    $scope.calculateMoney = saleService.calculateMoney;
    $scope.name = '';
    var reset = function () {
        $scope.chooseItemList = [];
        $scope.totalMoney = 0;
    };
    reset();

    /*确认*/
    $scope.retailIn = function () {
        if ($scope.currency == '转房客' || $scope.currency=='转哑房') {
            messageService.setMessage({type: 'error', content: '商品零售不能使用转房客或者转哑房'});
            popUpService.pop('message');
            return;
        }
        if ($scope.chooseItemList.length > 0) {
            /*直接入账到账务历史里*/
            var debtHistory = {};
            debtHistory.doTime = new Date();
            debtHistory.doneTime = debtHistory.doTime;
            debtHistory.pointOfSale = '零售';
            debtHistory.consume = saleService.calculateMoney($scope.chooseItemList);
            debtHistory.currency = $scope.currencyPayList[0].currency;
            debtHistory.currencyAdd = $scope.currencyPayList[0].currencyAdd;
            debtHistory.description = $scope.name + ':' + saleService.calculateDescription($scope.chooseItemList);
            angular.forEach($scope.chooseItemList, function (item) {//设置房吧明细
                item.totalMoney = item.price * item.num;
            });
            debtHistory.userId = LoginService.getUser();
            var retailIn={};
            retailIn.debtHistory=debtHistory;
            retailIn.roomShopDetailList=$scope.chooseItemList;
            webService.post('retailIn', retailIn)
                .then(function (d) {
                    messageService.actionSuccess();
                    reset();
                    /*弹出打印预览界面*/
                    window.open(host + "/receipt/" + d);
                });
        } else {
            messageService.setMessage({type: 'error', content: '您好像没有录入任何商品'});
            popUpService.pop('message');
        }
    }
}]);