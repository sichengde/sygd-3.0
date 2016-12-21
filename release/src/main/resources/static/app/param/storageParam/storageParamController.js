App.controller('storageParamController', ['$scope', 'dataService', 'util', function ($scope, dataService, util) {
    $scope.selectListHouse = [];
    $scope.selectListCargo = [];
    /*仓库名称*/
    $scope.houseFields = [
        {name: '仓库名称', id: 'houseName',notNull:"true",width:'300px'},
        {name: '备注', id: 'remark',width:'300px'},
        {name: '下属类别', id: 'includeCategory', popChoose: 'true', selectId: '0',notNull:"true",width:'500px'}
    ];
    /*品种类别*/
    $scope.storageCategoryFields = [
        {name: '货品类别', id: 'category',width:'200px'},
        {name: '备注', id: 'remark',width:'300px'}
    ];
    /*货品定义*/
    $scope.cargoFields = [
        {name: '名称', id: 'name',notNull:"true"},
        {name: '类别', id: 'category', selectId: '0',notNull:'true'},
        {name: '别名', id: 'alias'},
        {name: '供应商', id: 'supplier',notNull:'true',selectId:'3'},
        {name: '单位', id: 'unit', selectId: '2', freeSelect: 'true',notCheckSzSelect:'true',notNull:'true'},
        {name: '规格', id: 'specification'},
        {name: '型号', id: 'model'},
        {name: '包装单位', id: 'groupUnit'},
        {name: '包装数量', id: 'groupNum'},
        {name: '库存上限', id: 'upperLimit'},
        {name: '库存下限', id: 'lowerLimit'},
        {name: '出库计价', id: 'calculateStrategy', selectId: '1'},
        {name: '备注', id: 'remark'}
    ];
    /*领用部门*/
    $scope.storageOutDeptFields = [
        {name: '部门名称', id: 'name',width:'200px'},
        {name: '领用人', id: 'saveMan',width:'300px'}
    ];
    /*购买部门*/
    $scope.storageInDeptFields = [
        {name: '部门名称', id: 'name',width:'200px'},
        {name: '采购员', id: 'buyer',width:'300px'}
    ];
    /*批准人*/
    $scope.approverFields = [
        {name: '姓名', id: 'name'}
    ];
    /*供应商*/
    $scope.supplierFields=[
        {name:'名称',id:'name'}
    ];
    /*入库类型*/
    $scope.storageInCategoryFields = [
        {name: '入库类型', id: 'category'}
    ];
    /*出库类型*/
    $scope.storageOutCategoryFields = [
        {name: '出库类型', id: 'category'}
    ];
    /*其他参数*/
    $scope.otherParamFields = [
        {name: '参数名称', id: 'otherParam', width: '200px'},
        {name: '参数数值', id: 'value', width: '200px'},
        {name: '隶属模块', id: 'module', width: '200px', desc: '0'}
    ];
    $scope.otherParamCondition = 'module=\'公共\' or module = \'库存\'';
    dataService.refreshStorageCategoryList()
        .then(function (r) {
            var categoryList = util.objectListToString(r, 'category');
            $scope.selectListHouse[0] = categoryList;
            $scope.selectListCargo[0] = categoryList;
            $scope.selectListCargo[1] = dataService.storageCalculateStrategy;
        });
    dataService.refreshCargoList()
        .then(function (r) {
            $scope.selectListCargo[2] = util.objectListToString(r, 'unit');
        });
    dataService.refreshSupplierList()
        .then(function (r) {
            $scope.selectListCargo[3] = util.objectListToString(r, 'name');
        })
}]);