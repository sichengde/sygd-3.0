/**
 * Created by 舒展 on 2016/6/13 0013.
 * 集合参数表1，包括：客源，币种，餐饮营业部门
 */
App.controller('paramList1Controller', ['$scope', 'dataService', 'util', function ($scope, dataService, util) {
    $scope.companyCategoryFields = [{name: '单位种类', id: 'category', notNull: 'true'}];
    $scope.roomCategoryFields = [{name: '房间类别', id: 'category', notNull: 'true'}];
    $scope.guestSourceFields = [
        {name: '客源', id: 'guestSource', notNull: 'true'},
        {name: '客源归类', id: 'countCategory',notNull: 'true'}
    ];
    $scope.roomPriceAddFields = [
        {name: '时间节点', id: 'timeLimit', width: '120px'},
        {name: '房价倍率', id: 'roomAddByMultiple', width: '120px'},
        {name: '静态房价', id: 'roomAddStatic', width: '120px'},
        {name: '会员时间', id: 'vip', width: '120px',boolean:'true'}
    ];
    $scope.exchangeUserFields = [
        {name: '班次名称', id: 'className', width: '150px'},
        {name: '开始时间', id: 'beginTime', width: '150px'},
        {name: '结束时间', id: 'endTime', width: '150px'}
    ];
    /*房吧*/
    $scope.roomShopFields = [
        {name: '品种名称', id: 'item', width: '150px'},
        {name: '品种类别', id: 'category', selectId: '0', copy: true, width: '100px'},
        {name: '单位', id: 'unit',width:'100px'},
        {name: '品种单价', id: 'price',width:'100px'},
        {name: '库房货物', id: 'cargo', filter: 'list', boolean: true,width:'100px'},
        {id: 'pointOfSale', default: '房吧', width: '100px'}
    ];

    /*房间类别*/
    $scope.roomFields = [
        {name: '房号', id: 'roomId', notNull: 'true', width: '130px'},
        {name: '楼区', id: 'area', width: '80px', copy: true},
        {name: '楼层', id: 'floor', width: '80px', copy: true},
        {name: '房类', id: 'category', selectId: '0', width: '180px', copy: true},
        {name: '房价', id: 'price', width: '130px', copy: true},
        {name: '早餐', id: 'breakfast', width: '80px', copy: true},
        {name: '床位数量', id: 'totalBed', width: '100px', copy: true},
        {name: '属于房间', id: 'ifRoom', width: '100px', copy: true,boolean:'true'},
        {id: 'state', width: '80px', copy: true, default: '可用房'}
    ];
    /*0是房间类别，1是房租方式*/
    $scope.selectList = [];
    $scope.selectList[1] = dataService.getRoomStateList;
    /*0是房吧种类*/
    $scope.selectRoomShopList = [];

    /*房租方式*/
    $scope.protocolFields = [{name: '协议名称', id: 'protocol', permission: 'notNull', width: '205px'},
        {name: '房类', id: 'roomCategory', selectId: '0', permission: 'notNull', width: '175px'},
        {name: '房租方式', id: 'roomPriceCategory', selectId: '1', permission: 'notNull', width: '175px'},
        {name: '房价', id: 'roomPrice', permission: 'notNull', width: '120px', float: 'true'},
        {name: '特殊协议', id: 'special', width: '80px'},
        {name: '基本时段', id: 'base', width: '200px'},
        {name: '超时单位', id: 'step', width: '200px'},
        {name: '超时计费', id: 'stepPrice', width: '120px'},
        {name: '最高价格', id: 'maxPrice', width: '120px'},
        {name: '早餐份数', id: 'breakfast', width: '100px'}
    ];
    $scope.selectList[1] = dataService.getRoomPriceCategory;

    $scope.cleanRoomManFields=[
        {name:'名称',id:'user'}
    ];
    $scope.otherParamFields = [
        {name: '参数名称', id: 'otherParam', width: '200px'},
        {name: '参数数值', id: 'value', width: '200px'},
        {name: '隶属模块', id: 'module', width: '200px', desc: '0'}
    ];
    $scope.interfaceDoorFields=[
        {name:'房间号码',id:'roomId'},
        {name:'门锁号码',id:'doorId'},
        {name:'分机号码',id:'telId'}
    ];
    $scope.otherParamCondition = 'module=\'公共\' or module = \'接待\'';
    dataService.initData(['refreshSaleCountList', 'refreshRoomCategoryList'], [{condition: 'second_point_of_sale=\'房吧\''}])
        .then(function () {
            $scope.selectList[0] = util.objectListToString(dataService.getRoomCategoryList(), 'category');
            $scope.selectRoomShopList[0] = util.objectListToString(dataService.getSaleCountList(), 'name');
        });
    /*批量增加房号弹窗*/
    $scope.addMany=function () {

    }
}]);