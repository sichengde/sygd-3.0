/**
 * Created by 舒展 on 2016-06-06.
 * 用户控制器
 */
App.controller('UserController', ['$scope', 'dataService', 'util', 'popUpService', function ($scope, dataService, util, popUpService) {
    $scope.pointOfSaleFields = [
        {name: '一级营业部门', id: 'firstPointOfSale', notNull: 'true', width: '170px'},
        {name: '隶属模块', id: 'module', notNull: 'true', width: '170px'},
        {name: '二级营业部门', id: 'secondPointOfSale', notNull: 'true', width: '170px'},
        {name: '仓库', id: 'house', width: '200px',selectId:'0'}
    ];
    $scope.currencyFields = [
        {name: '币种', id: 'currency', notNull: 'true'},
        {name: '参与积分', id: 'score' ,boolean:true},
        {name: '结账币种', id: 'checkOut' ,boolean:true},
        {name: '押金币种', id: 'checkIn' ,boolean:true},
        {name: '参与结算款统计', id: 'payTotal' ,boolean:true}
    ];
    $scope.selectCurrencyList=[];
    $scope.selectListPointOfSale=[];
    $scope.selectCurrencyList[0]=dataService.getBooleanListEn;
    $scope.userFields = [{name: '姓名', id: 'userId', notNull: 'true', width: '100px'},
        {name: '模块', id: 'moduleArray', width: '300px'},
        {name: '权限', id: 'permissionArray', width: '500px'},
        {name: '营业部门', id: 'pointOfSaleArray', width: '200px'}
    ];
    $scope.updateUser=function () {
        popUpService.pop('userAdd',null,null,{mode:'update'})
    };
    $scope.addUser=function () {
        popUpService.pop('userAdd',null,null,{mode:'add'})
    };
    $scope.otherParamFields = [
        {name: '参数名称', id: 'otherParam', width: '200px'},
        {name: '参数数值', id: 'value', width: '200px'},
        {name: '隶属模块', id: 'module', width: '200px'}
    ];
    $scope.selectSaleCountList = [];
    $scope.saleCountFields = [
        {name: '营业部门', id: 'firstPointOfSale', selectId: '1', copy: true, width: '200px'},
        {name: '统计部门', id: 'secondPointOfSale', selectId: '0', filterContent:'saleCount',filterContentId:'firstPointOfSale',copy: true, width: '200px'},
        {name: '品种类别', id: 'name', width: '200px'},
        {name: '厨房', id: 'cookRoom', selectId: '2',  width: '200px'}
    ];
    /*账单类型*/
    $scope.inCategoryFields=[{name:'账单类型',id:'category'}];
    $scope.initList = function () {
        $scope.selectRoomShopList[0] = util.objectListToString(dataService.getSaleCountList(), 'name');
    };
    $scope.selectRoomShopList = [];
    dataService.initData(['refreshAvailableModuleList', 'refreshPointOfSaleList','refreshCookRoomList','refreshHouseList'])
        .then(function () {
            $scope.availableModuleList = dataService.getAvailableModule();
            $scope.pointOfSaleList = dataService.getPointOfSale();
            var d = dataService.getPointOfSale();
            var done = [];
            var var2 = [];
            for (var i = 0; i < d.length; i++) {
                done = done.concat(d[i].secondPointOfSale.split(' '));
                var2 = var2.concat(d[i].firstPointOfSale.split(' '));
            }
            $scope.selectSaleCountList[0] = done;
            $scope.selectSaleCountList[1] = var2;
            $scope.selectSaleCountList[2] = util.objectListToString(dataService.getCookRoomList(),'cookName');
            $scope.selectRoomShopList[1] = done;
            $scope.selectListPointOfSale[0]=util.objectListToString(dataService.getHouseList(),'houseName');
        });
    var outRefreshUser;
    $scope.outRefreshUserList = function (f) {
        outRefreshUser = f;
    };
    $scope.refresh = function () {
        outRefreshUser();
    };
    $scope.clickPop = function (pop) {
        popUpService.pop(pop);
    };
}]);