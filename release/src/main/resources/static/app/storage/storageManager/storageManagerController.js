App.controller('storageManagerController', ['$scope', 'dataService', 'util', 'webService', 'messageService', 'popUpService', function ($scope, dataService, util, webService, messageService, popUpService) {
    $scope.storageIn = {};
    $scope.storageOut = {};
    dataService.initData(['refreshHouseList', 'refreshApproverList', 'refreshStorageInDeptList', 'refreshStorageOutDeptList', 'refreshStorageInCategoryList', 'refreshStorageOutCategoryList'])
        .then(function () {
            $scope.storageInAndOutDetailSelectList[0] = util.objectListToString(dataService.getHouseList(), 'houseName');
            $scope.approverList = util.objectListToString(dataService.getApproverList(), 'name');
            $scope.storageInDeptList = dataService.getStorageInDeptList();
            $scope.storageOutDeptList = dataService.getStorageOutDeptList();
            $scope.storageInCategory = util.objectListToString(dataService.getStorageInCategoryList(), 'category');
            $scope.storageOutCategory = util.objectListToString(dataService.getStorageOutCategoryList(), 'category');
        });
    $scope.changeDeptIn = function () {
        $scope.buyerList = $scope.storageIn.deptIn.buyer.split(',');
    };
    $scope.changeDeptOut = function () {
        $scope.saveManList = $scope.storageOut.deptOut.saveMan.split(',');
    };
    $scope.storageInAndOutDetailSelectList = [];
    $scope.storageInDetailFields = [
        {name: '仓库', id: 'house', selectId: '0', itemChange: true, notNull: 'true'},
        {name: '货物', id: 'cargo', selectId: '1', itemChange: true, freeSelect: 'true', notNull: 'true',unique:'true'},
        {name: '类别', id: 'category', static: 'true'},
        {name: '单位', id: 'unit', static: 'true'},
        {name: '数量', id: 'num', itemChange: true, notNull: 'zero'},
        {name: '单价', id: 'price', notNull: 'true'},
        {name: '金额合计', id: 'total', exp: 'num*price', static: 'true'},
        {name: '供应商', id: 'supplier', static: 'true'},
        {name: '生产日期', id: 'beginTime',date:'short'},
        {name: '库存期限', id: 'endTime',date:'short'},
        {name: '备注', id: 'remark'}
    ];
    $scope.storageOutDetailFields = [
        {name: '仓库', id: 'house', selectId: '0', itemChange: true, notNull: 'true'},
        {name: '货物', id: 'cargo', selectId: '1', itemChange: true, freeSelect: 'true', notNull: 'true',unique:'true'},
        {name: '类别', id: 'category', static: 'true'},
        {name: '单位', id: 'unit', static: 'true'},
        {name: '数量', id: 'num', itemChange: true, notNull: 'zero'},
        {name: '单价', id: 'price'},
        {name: '金额合计', id: 'total', exp: 'num*price', static: 'true'},
        {name: '用途', id: 'myUsage'}
    ];
    /*入库*/
    $scope.storageInDetailList = [];
    $scope.storageInDetailItemChange = function (item, field) {
        switch (field.id) {
            case 'house'://选择仓库后重新生成货品名称列表
                webService.post('cargoGetByHouse', item[field.id])
                    .then(function (r) {
                        $scope.storageInAndOutDetailSelectList[1] = util.objectListToString(r, 'name');
                    });
                break;
            case 'cargo'://选择货物后生成单位，供应商，入库时间
                if(field.cargo==''){
                    break;
                }
                dataService.refreshCargoList({condition: 'name=' + util.wrapWithBrackets(item.cargo)})
                    .then(function (r) {
                        item.unit = r[0].unit;
                        item.supplier = r[0].supplier;
                        item.category = r[0].category;
                    });
                break;
            case 'num':
                break;
        }
    };
    /*出库*/
    $scope.storageOutDetailList = [];
    var availableCargo;//可以出库的货物
    $scope.storageOutDetailItemChange = function (item, field) {
        switch (field.id) {
            case 'house'://选择仓库后查看该仓库可用的货品列表与数量
                webService.post('getRemainCargo', item[field.id])
                    .then(function (r) {
                        $scope.storageInAndOutDetailSelectList[1] = util.objectListToString(r, 'cargo');
                        availableCargo = r;
                    });
                break;
            case 'cargo':
                if(field.cargo==''){
                    break;
                }
                /*选择货物后生成单位，供应商，入库时间*/
                dataService.refreshCargoList({condition: 'name=' + util.wrapWithBrackets(item.cargo)})
                    .then(function (r) {
                        item.unit = r[0].unit;
                        item.category = r[0].category;
                    });
                break;
            case 'num'://输入数量后判断是否超过可用数量
                if(field.num==''){
                    break;
                }
                var chooseCargo = util.getValueByField(availableCargo, 'cargo', item.cargo);
                if (chooseCargo.remain < item.num) {
                    messageService.setMessage({type: 'error', content: '输入数量超过剩余数量,剩余:' + chooseCargo.remain});
                    popUpService.pop('message');
                    item.num = chooseCargo.remain;
                } else {
                    /*后台统计平均价格*/
                    if (item.num != '') {
                        webService.post('storageParsePrice', item)
                            .then(function (r) {
                                item.price = r;
                            })
                    }
                }
                break;
        }
    };
    /*提交入库*/
    $scope.storageInAction = function () {
        if ($scope.storageInDetailList.length == 0) {
            messageService.setMessage({type: 'error', content: '入库信息没有保存'});
            popUpService.pop('message');
            return;
        }
        var post = {};
        $scope.storageIn.deptIn = $scope.storageIn.deptIn.name;
        post.storageInDetailList = $scope.storageInDetailList;
        post.storageIn = $scope.storageIn;
        return webService.post('storageInAdd', post)
            .then(function (r) {
                messageService.actionSuccess();
                $scope.storageInDetailList = [];
                webService.openReport(r);
            })
    };
    /*提交出库*/
    $scope.storageOutAction = function () {
        if ($scope.storageOutDetailList.length == 0) {
            messageService.setMessage({type: 'error', content: '出库信息没有保存'});
            popUpService.pop('message');
            return;
        }
        var post = {};
        $scope.storageOut.deptOut = $scope.storageOut.deptOut.name;
        post.storageOutDetailList = $scope.storageOutDetailList;
        post.storageOut = $scope.storageOut;
        return webService.post('storageOutAdd', post)
            .then(function (r) {
                messageService.actionSuccess();
                $scope.storageOutDetailList = [];
                webService.openReport(r);
            })
    };
    /*指定入库货品出库*/
    var specifyStorageOutList;
    $scope.storageOutSpecify = function () {
        dataService.refreshStorageInDetailList()
            .then(function (r) {
                specifyStorageOutList=r;
                popUpService.pop('storageOutSpecify', null, recallSpecify, specifyStorageOutList);
            });
    };
    function recallSpecify() {
        for (var i = 0; i < specifyStorageOutList.length; i++) {
            var obj = specifyStorageOutList[i];
            if(obj.out) {
                obj.num = obj.outNum;
                if(obj.num<0){
                    messageService.setMessage({type:'error',content:'数量必须大于0'});
                    popUpService.pop('message');
                    return;
                }
                if(util.getValueByField($scope.storageOutDetailList,'id',obj.id)){
                    messageService.setMessage({type:'error',content:'同一批货物不可以多次出库'});
                    popUpService.pop('message');
                    return;
                }
                $scope.storageOutDetailList.push(obj);
            }
        }
        $scope.storageOutDetailBackUp=angular.copy($scope.storageOutDetailList);
    }
    /*统计各个销售点销售的品种，然后出库*/
    $scope.autoOut=function () {
        webService.post('storageAutoOut',{})
            .then(function (r) {
                messageService.actionSuccess();
                if(r.length>1){
                    messageService.setMessage({type:'alert',content:'存在多笔出库，请仔细核对依次打印'});
                    popUpService.pop('message');
                }
                for (var i = 0; i < r.length; i++) {
                    var obj = r[i];
                    webService.openReport(obj);
                }
            })
    };
    $scope.storageRemainFields=[
        {name:'仓库',id:'house',filter:'list'},
        {name:'货品',id:'cargo',filter:'input'},
        {name:'单位',id:'unit'},
        {name:'单价',id:'price'},
        {name:'余量',id:'remain'},
        {name:'总金额',id:'total',exp:'price*remain',sum:'true'}
    ];
}]);