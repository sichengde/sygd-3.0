/**
 * Created by Administrator on 2016-07-15.
 */
App.controller('companyDebtController',['$scope','popUpService','dataService','util','$route',function ($scope,popUpService, dataService, util,$route) {
    if($route.current.params.mode=='edit'){
        $scope.editable=true;
    }
    //应收应付
    $scope.companyFields=[
        {name:'序号',id:'id',width:'70px'},
        {name:'单位名称',id:'name',width:'265px'},
        {name:'别名',id:'alias',width:'100px'},
        {name:'单位类别',id:'category',selectId:'1',width:'130px'},
        {name:'价格协议',id:'protocol',selectId:'0',width:'170px'},
        {name:'挂账款',id:'debt',static:'true',width:'100px'},
/*        {name:'预付款',id:'deposit',static:'true',width:'100px'},
        {name:'余额',exp:'deposit-debt',static:'true',width:'100px'},*/
        {name:'有效日期',id:'limitTime',width:'100px',date:'short'},
        {name:'销售员',id:'saleMan',width:'100px'},
        {name:'联系方式',id:'phone',width:'150px'},
        {name:'允许挂账',id:'ifDebt',width:'100px',default:'y',selectId:'2'}
    ];

    //单位签单人
    $scope.companyLordFields=[
        {name:'单位名称',id:'company',selectId:'0',width:'40%'},
        {name:'签单人名称',id:'name',width:'40%'},
        {name:'挂账款',id:'debt',static:'true'}
    ];

    //账务明细
    $scope.companyDebtFields=[
        {name:'单位名称',id:'company',width:'230px'},
        {name:'签单人',id:'lord',width:'100px'},
        {name:'结账时间',id:'doTime',date:'true',width:'100px',desc:'0',filter:'date'},
        {name:'挂账款',id:'debt',width:'100px'},
        {name:'剩余挂账',id:'currentRemain',width:'100px',sum:'true'},
        /*{name:'预付款',id:'deposit',width:'100px'},*/
        /*{name:'支付款',id:'pay',width:'100px',sum:'true'},*/
        {name:'币种',id:'currency',width:'150px',filter:'list'},
        {name:'币种信息',id:'currencyAdd',width:'150px',filter:'list'},
        {name:'操作类别',id:'category',width:'150px',filter:'list'},
        {name:'结账流水号',id:'paySerial',width:'150px'},
        {name:'备注',id:'description',width:'150px'},
        {name:'营业部门',id:'pointOfSale',width:'150px'},
        {name:'操作员',id:'userId',width:'120px'}
    ];

    //宴请签单人
    $scope.freemanFields=[
        {name:'宴请签单人',id:'freeman',width:'120px'},
        {name:'累计宴请',id:'consume',width:'100px'},
        {name:'电话',id:'phone',width:'180px'},
        {name:'备注',id:'remark',width:'200px'}
    ];

    //宴请明细
    $scope.freeDetailFields=[
        {name:'宴请签单人',id:'freeman'},
        {name:'宴请理由',id:'reason'},
        {name:'结账序列号',id:'paySerial'},
        {name:'营业部门',id:'pointOfSale'},
        {name:'宴请金额',id:'consume'},
        {name:'操作时间',id:'doTime',desc:'0'},
        {name:'操作员号',id:'userId'}
    ];
    $scope.condition='ifDebt=\'y\'';
    $scope.companySelectList=[];
    $scope.companyLordSelectList=[];
    /*挂账明细默认不显示*/
    $scope.initConditionCompany='id=-1';
    $scope.initConditionFreeman='id=-1';
    dataService.initData(['refreshProtocolList','refreshCompanyCategoryList'])
        .then(function () {
            $scope.companySelectList[0]=util.objectListToString(dataService.getProtocolList(),'protocol');
            $scope.companySelectList[1]=util.objectListToString(dataService.getCompanyCategoryList(),'category');
            $scope.companySelectList[2]=dataService.getBooleanList;
        });
    /*单位刷新完之后初始化签单人可选单位*/
    $scope.lordInit=function (d) {
        $scope.companyLordSelectList[0]=util.objectListToString(d,'name');
    };
    /*选择单位*/
    $scope.chooseCompany=function (d) {
        $scope.initConditionCompany='company='+util.wrapWithBrackets(d.name);
    };
    /*选择宴请签单人*/
    $scope.chooseFreeman=function (d) {
        $scope.initConditionFreeman='freeman='+util.wrapWithBrackets(d.freeman);
    };
    /*进入单位结算界面*/
    $scope.pop=function (d) {
        popUpService.pop(d);
    };

    $scope.refresh=function () {
        window.location.reload();
    }
}]);