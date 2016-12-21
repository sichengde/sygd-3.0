/**
 * Created by Administrator on 2016-07-14.
 */
App.controller('restaurantParamController', ['$scope', 'dataService', 'util', 'LoginService', 'webService', function ($scope, dataService, util, LoginService, webService) {
    var p = {condition: 'module=\'餐饮\''};
    dataService.initData(['refreshPointOfSaleList'], [p])
        .then(function () {
            $scope.pointOfSaleList = dataService.getPointOfSale();
        });
    dataService.refreshCookRoomList()
        .then(function (r) {
            $scope.selectListMenu[1]=util.objectListToString(r,'cookName');
        });
    $scope.selectListMenu = [];
    $scope.selectListFoodSet = [];
    $scope.selectListCookRoom = [];
    /*获取所有可用的打印机*/
    webService.post('getAllPrinter')
        .then(function (r) {
            $scope.selectListCookRoom[0] = r;
        });
    $scope.otherParamFields = [
        {name: '参数名称', id: 'otherParam', width: '200px'},
        {name: '参数数值', id: 'value', width: '200px'},
        {name: '隶属模块', id: 'module', width: '200px'}
    ];
    $scope.deskDiscountFields = [
        {name: '折扣名称', id: 'discountName', width: '200px'},
        {name: '折扣率', id: 'discountValue', width: '200px'}
    ];
    $scope.cookRoomFields = [
        {name: '厨房名称', id: 'cookName'},
        {name: '打印机名称', id: 'printer', selectId: '0'},
        {name: '打印机ip', id: 'printerIp'},
        {name: '一菜一切', id: 'cut', boolean: 'true'},
        {name: '打印张数', id: 'num'},
        {name: 'u口打印', id: 'usbPort', boolean: 'true'}
    ];
    $scope.otherParamCondition = 'module=\'公共\' or module = \'餐饮\'';
    /*侦听切换，随时更新szTable内容，桌台定义*/
    $scope.chooseItemDesk = function (pointOfSale) {
        $scope.deskFields = [
            {name: '桌台号', id: 'name', notNull: 'true'},
            {name: '座位数', id: 'seat', notNull: 'true'},
            {id: 'pointOfSale', default: pointOfSale}
        ];
        $scope.deskCondition = 'pointOfSale=' + util.wrapWithBrackets(pointOfSale);
    };
    /*侦听切换，随时更新szTable内容，菜谱定义*/
    $scope.chooseItemMenu = function (pointOfSale) {
        $scope.menuFields = [
            {id: 'pointOfSale', default: pointOfSale},
            {name: '菜名', id: 'name', notNull: 'true', width: '200px'},
            {name: '种类', id: 'category', selectId: '0', notNull: 'true', width: '200px', copy: 'true', filter: 'list'},
            {name: '单价', id: 'price', notNull: 'true', width: '200px'},
            {name: '单位', id: 'unit', notNull: 'true', width: '200px', copy: 'true'},
            {name: '别名', id: 'alias', notNull: 'true', width: '200px'},
            {name: '参与折扣', id: 'ifDiscount', width: '200px', filter: 'list', boolean: true, copy: 'true'},
            {name: '套餐', id: 'foodSet', width: '200px', filter: 'list', boolean: true},
            {name: '成本', id: 'cost', width: '200px'},
            {name: '库房货物', id: 'cargo', width: '200px', filter: 'list', boolean: true},
            {name: '厨房', id: 'cookRoom', width: '200px', filter: 'input', copy: 'true',popChoose: 'true', selectId: '1'}
        ];
        $scope.menuCondition = 'pointOfSale=' + util.wrapWithBrackets(pointOfSale);
        var p2 = {condition: 'first_point_of_sale=' + util.wrapWithBrackets(pointOfSale)};
        dataService.initData(['refreshSaleCountList'], [p2])
            .then(function () {
                $scope.selectListMenu[0] = util.objectListToString(dataService.getSaleCountList(), 'name');
            });
    };
    /*套餐定义*/
    $scope.chooseFoodSet = function (pointOfSale) {
        $scope.foodSetFields = [
            {id: 'pointOfSale', default: pointOfSale},
            {id: 'setName', name: '套餐名称', selectId: '0'},
            {id: 'foodName', name: '菜品', selectId: '1', freeSelect: 'true', selectListField: 'name'},
            {id: 'foodNum', name: '数量', notNull: 'true'}
        ];
        $scope.foodSetCondition = 'pointOfSale=' + util.wrapWithBrackets(pointOfSale);
        dataService.refreshMenuList({condition: 'point_of_sale=' + util.wrapWithBrackets(pointOfSale) + ' and food_set=true'})
            .then(function (r) {
                $scope.selectListFoodSet[0] = util.objectListToString(r, 'name');
            });
        dataService.refreshMenuList({condition: 'point_of_sale=' + util.wrapWithBrackets(pointOfSale)})
            .then(function (r) {
                $scope.selectListFoodSet[1] = r;
            })
    };
    $scope.menuRemarkFields = [
        {id: 'remark', name: '备注'}
    ]
}]);
