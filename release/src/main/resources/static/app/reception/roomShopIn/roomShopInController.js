/**
 * Created by 舒展 on 2016/6/18 0018.
 */
App.controller('roomShopInController', ['$scope', 'dataService', 'receptionService', 'util', 'webService', 'popUpService', 'saleService', 'messageService', '$q', function ($scope, dataService, receptionService, util, webService, popUpService, saleService, messageService, $q) {
    $scope.room = receptionService.getChooseRoom();
    var guestList = util.objectListToString($scope.room.checkIn.checkInGuest, 'name');
    if (guestList) {
        $scope.nameString = guestList.join(' ');
    }
    $scope.roomShopFields = [
        {name: '品种', id: 'category', width: '150px'},
        {name: '商品名称', id: 'item', width: '200px'},
        {name: '价格', id: 'price', width: '100px'}
    ];
    $scope.chooseItemFields = [
        {name: '品种', id: 'category', width: '70px', static: 'true'},
        {name: '商品名称', id: 'item', width: '130px', static: 'true'},
        {name: '价格', id: 'price', width: '40px', static: 'true'},
        {name: '数量', id: 'num', width: '40px'}
    ];
    $scope.chooseItemList = [];
    $scope.totalMoney = 0;
    $scope.condition = 'point_of_sale=\'房吧\'';
    $scope.chooseItem = saleService.chooseItem;
    $scope.calculateMoney = saleService.calculateMoney;

    /*确认*/
    $scope.confirm = function () {
        if ($scope.chooseItemList.length > 0) {
            var roomShopIn = {};
            roomShopIn.roomId = $scope.room.roomId;
            roomShopIn.money = saleService.calculateMoney($scope.chooseItemList);
            roomShopIn.description = saleService.calculateDescription($scope.chooseItemList);
            angular.forEach($scope.chooseItemList, function (item) {//设置房吧明细
                item.totalMoney = item.price * item.num;
                item.room=roomShopIn.roomId;
            });
            roomShopIn.roomShopDetailList=$scope.chooseItemList;
            if ($scope.checkInGuest) {//有床位号
                roomShopIn.bed = $scope.checkInGuest.bed;
            }
            return webService.post('roomShopIn', roomShopIn)
                .then(function () {
                    /*关闭该页面*/
                    popUpService.close('roomShopIn');
                    messageService.actionSuccess();
                })
        }else {
            return $q.resolve();
        }
    }
}]);