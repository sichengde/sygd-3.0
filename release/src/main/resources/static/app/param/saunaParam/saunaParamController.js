App.controller('saunaParamController',['$scope','dataService','util',function ($scope,dataService,util) {
    /*手牌定义*/
    $scope.saunaRingSelectList=[];
    $scope.saunaRingSelectList[0]=dataService.getSexList;
    $scope.saunaRingFields=[
        {name:'手牌号',id:'number'},
        {name:'男女牌',id:'sex',selectId:'0',copy:'true'},
        {name:'维修中',id:'repair',boolean:true}
    ];
    /*项目定义*/
    $scope.saunaMenuFields=[
        {name:'菜名',id:'name', notNull: 'true'},
        {name:'种类',id:'category', selectId: '0', notNull: 'true', copy: 'true', filter: 'list'},
        {name:'单价',id:'price', notNull: 'true'},
        {name:'多重单价',id:'manyPrice', boolean: true},
        {name:'别名',id:'alias', notNull: 'true'},
        {name:'单位',id:'unit', notNull: 'true', copy: 'true'},
        {name:'库存货品',id:'cargo', filter: 'list', boolean: true},
        {name:'成本',id:'cost'},
        {name:'参与折扣',id:'ifDiscount', filter: 'list', boolean: true, copy: 'true'}
    ];
    $scope.saunaMenuSelectList=[];
    var p2 = {condition: 'first_point_of_sale=\'桑拿\''};
    dataService.refreshSaleCountList(p2)
        .then(function (r) {
            $scope.saunaMenuSelectList[0]=util.objectListToString(r, 'name');
        });
    /*技师定义*/
    $scope.saunaUserFields=[
        {name:'技师',id:'name'},
        {name:'id卡号',id:'idNumber'}
    ];
    /*账单分类定义*/
    $scope.saunaMenuDetailSelectList=[];
    $scope.saunaMenuDetailFields=[
        {name:'项目名称',id:'menu',notNull:'true',selectId:'1',freeSelect:'true'},
        {name:'账单类型',id:'inCategory',selectId:'0',notNull:'true'},
        {name:'价格',id:'price',notNull:'true'}
    ];
    dataService.refreshInCategoryList()
        .then(function (r) {
            $scope.saunaMenuDetailSelectList[0]=util.objectListToString(r,'category');
        });
    dataService.refreshSaunaMenuList({condition:'many_price=true'})
        .then(function (r) {
            $scope.saunaMenuDetailSelectList[1]=util.objectListToString(r,'name');
        });
    /*折扣定义*/
    $scope.saunaDiscountFields = [
        {name: '折扣名称', id: 'discountName', width: '200px'},
        {name: '折扣率', id: 'discountValue', width: '200px'}
    ];
    /*杂散参数*/
    $scope.otherParamFields = [
        {name: '参数名称', id: 'otherParam', width: '200px'},
        {name: '参数数值', id: 'value', width: '200px'},
        {name: '隶属模块', id: 'module', width: '200px'}
    ];
    $scope.otherParamCondition = 'module=\'公共\' or module = \'桑拿\'';
}]);