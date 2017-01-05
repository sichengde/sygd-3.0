/**
 * Created by Administrator on 2016/6/19 0019.
 */
App.controller('companyController',['$scope','popUpService','dataService','util',function ($scope,popUpService, dataService, util) {
    $scope.companyFields=[
        {name:'序号',id:'id',width:'70px'},
        {name:'单位名称',id:'name',width:'250px'},
        {name:'别名',id:'alias',width:'100px'},
        {name:'单位类别',id:'category',selectId:'1',width:'140px'},
        {name:'房价协议',id:'protocol',selectId:'0',width:'170px'},
        {name:'单位消费',id:'consume',static:'true',width:'140px'},
        {name:'有效日期',id:'limitTime',date:'short',width:'150px'},
        {name:'销售员',id:'saleMan',width:'100px'},
        {name:'联系方式',id:'phone',width:'150px'},
        {name:'允许挂账',id:'ifDebt',width:'100px',default:'n',selectId:'2'}
    ];
    $scope.debtPayFields=[
        {name:'单位名称',id:'company',width:'230px'},
        {name:'结账时间',id:'doneTime',date:'true',width:'120px'},
        {name:'结账金额',id:'debtMoney',width:'100px'},
        {name:'币种',id:'currency',width:'100px'},
        {name:'结账流水号',id:'paySerial',width:'150px'},
        {name:'离店流水号',id:'checkOutSerial',width:'150px'},
        {name:'结账种类',id:'debtCategory',width:'150px'},
        {name:'操作员',id:'userId',width:'100px'}
    ];
    $scope.companySelectList=[];
    /*账务明细默认不显示*/
    $scope.initCondition='id=-1';
    /*外部刷新结账明细*/
    var refreshDebtPayList;
    $scope.refreshData=function (f) {
        refreshDebtPayList=f;
    };
    /*选择单位*/
    $scope.chooseCompany=function (d) {
        $scope.initCondition='company='+util.wrapWithBrackets(d.name);
    };
    dataService.initData(['refreshProtocolList','refreshCompanyCategoryList'])
        .then(function () {
            $scope.companySelectList[0]=util.objectListToString(dataService.getProtocolList(),'protocol');
            $scope.companySelectList[1]=util.objectListToString(dataService.getCompanyCategoryList(),'category');
            $scope.companySelectList[2]=dataService.getBooleanList;
        });
}]);