/**
 * Created by Administrator on 2016/6/19 0019.
 */
App.controller('companyController',['$scope','popUpService','dataService','util','agGridService','webService','dateFilter',function ($scope,popUpService, dataService, util,agGridService,webService,dateFilter) {
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
        {name:'允许挂账',id:'ifDebt',width:'100px',boolean:'true'}
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
    $scope.beginTime = util.getTodayMin();
    $scope.endTime = util.getTodayMax();
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
    /*初始化*/
    $scope.setInitCompanyConsume=function () {
        $scope.showCompanyConsumeReport=false;
    };
    /*查询单位发生额*/
    var companyConsumeFields = [
        {headerName: "协议单位", field: "company",rowGroupIndex: 0},
        {headerName: "房号", field: 'roomId'},
        {headerName: "消费", field: "consume",aggFunc: 'sum'},
        {headerName: "押金", field: "deposit"},
        {headerName: "入账时间", field: "doTime",cellRenderer: agGridService.stdTimeRender},
        {headerName: "类型", field: "category"},
        {headerName: "部门", field: "pointOfSale",rowGroupIndex: 1},
        {headerName: "操作员", field: "userId"},
        {headerName: "备注", field: "remark"}
    ];
    $scope.companyConsumeGridOptions = {
        columnDefs: companyConsumeFields,
        animateRows: true,
        enableColResize: true,
        rowData: null,
        getContextMenuItems: getContextMenuItems,
        localeText: agGridService.getLocalText
    };
    function getContextMenuItems(param) {
        var exportParams = {};
        exportParams.processCellCallback = function (params) {
            if(params.column.cellRenderer){
                return params.column.cellRenderer(params);
            }else {
                return params.value;
            }
        };
        return [
            { // custom item
                name: '导出excel ',
                action: function () {
                    param.api.exportDataAsExcel(exportParams);
                }
            }
        ];
    }
    $scope.companyConsumeReport=function (beginTime, endTime) {
        var query={};
        query.condition='company is not null and do_time>'+util.wrapWithBrackets(dateFilter(beginTime,'yyyy-MM-dd HH:mm:ss'))+' and do_time<'+util.wrapWithBrackets(dateFilter(endTime,'yyyy-MM-dd HH:mm:ss'));
        query.orderByList=['company'];
        webService.post('debtIntegrationGet',query)
            .then(function (r) {
                $scope.companyConsumeGridOptions.api.setRowData(r);
                $scope.showCompanyConsumeReport = true;
            })
    }
}]);