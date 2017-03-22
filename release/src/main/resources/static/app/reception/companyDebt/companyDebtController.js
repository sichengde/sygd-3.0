/**
 * Created by Administrator on 2016-07-15.
 */
App.controller('companyDebtController', ['$scope', 'popUpService', 'dataService', 'util', '$route', 'fieldService', 'messageService', 'dateFilter', 'agGridService', function ($scope, popUpService, dataService, util, $route, fieldService, messageService, dateFilter, agGridService) {
    if ($route.current.params.mode == 'edit') {
        $scope.editable = true;
    }
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    //应收应付
    $scope.companyFields = [
        {name: '序号', id: 'id', width: '70px'},
        {name: '单位名称', id: 'name', width: '265px'},
        {name: '别名', id: 'alias', width: '100px'},
        {name: '单位类别', id: 'category', selectId: '1', width: '130px'},
        {name: '价格协议', id: 'protocol', selectId: '0', width: '170px'},
        {name: '挂账款', id: 'debt', static: 'true', width: '100px', default: '0'},
        /*        {name:'预付款',id:'deposit',static:'true',width:'100px'},
         {name:'余额',exp:'deposit-debt',static:'true',width:'100px'},*/
        {name: '有效日期', id: 'limitTime', width: '100px', date: 'short'},
        {name: '销售员', id: 'saleMan', width: '100px'},
        {name: '联系方式', id: 'phone', width: '150px'},
        {name: '允许挂账', id: 'ifDebt', width: '100px', boolean: 'true'}
    ];

    //单位签单人
    $scope.companyLordFields = [
        {name: '单位名称', id: 'company', selectId: '0', width: '40%'},
        {name: '签单人名称', id: 'name', width: '40%'},
        {name: '挂账款', id: 'debt', static: 'true', default: '0'}
    ];

    //账务明细
    $scope.companyDebtFields = [
        {name: '单位名称', id: 'company', width: '230px'},
        {name: '签单人', id: 'lord', width: '100px'},
        {name: '结账时间', id: 'doTime', date: 'true', width: '100px', desc: '0', filter: 'date'},
        {name: '挂账款', id: 'debt', width: '100px'},
        {name: '剩余挂账', id: 'currentRemain', width: '100px'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '备注', id: 'description', width: '150px'},
        {name: '模块', id: 'pointOfSale', width: '150px'},
        {name: '营业部门', id: 'secondPointOfSale', width: '150px'},
        {name: '操作员', id: 'userId', width: '120px'}
    ];
    $scope.companyDebtHistoryFields = $scope.companyDebtFields;
    /*结算明细*/
    $scope.companyPayFields = [
        {name: '单位结账序列号', id: 'companyPaySerial', filter: 'input'},
        {name: '结挂账款', id: 'debt'},
        {name: '实收金额', id: 'pay'},
        {name: '币种', id: 'currency', filter: 'list'},
        {name: '币种额外信息', id: 'currencyAdd'},
        {name: '备注', id: 'remark'},
        {name: '结账时间', id: 'doneTime', desc: '0', filter: 'date'}
    ];
    //宴请签单人
    $scope.freemanFields = [
        {name: '宴请签单人', id: 'freeman', width: '120px'},
        {name: '累计宴请', id: 'consume', width: '100px'},
        {name: '电话', id: 'phone', width: '180px'},
        {name: '备注', id: 'remark', width: '200px'}
    ];

    //宴请明细
    $scope.freeDetailFields = [
        {name: '宴请签单人', id: 'freeman'},
        {name: '宴请理由', id: 'reason'},
        {name: '结账序列号', id: 'paySerial'},
        {name: '营业部门', id: 'pointOfSale'},
        {name: '宴请金额', id: 'consume'},
        {name: '操作时间', id: 'doTime', desc: '0'},
        {name: '操作员号', id: 'userId'}
    ];
    $scope.condition = 'if_debt=true';
    $scope.companySelectList = [];
    $scope.companyLordSelectList = [];
    /*挂账明细默认不显示*/
    $scope.initConditionCompany = 'id=-1';
    $scope.initConditionFreeman = 'id=-1';
    $scope.initConditionCompanyDebtHistory = 'id=-1';
    dataService.initData(['refreshProtocolList', 'refreshCompanyCategoryList'])
        .then(function () {
            $scope.companySelectList[0] = util.objectListToString(dataService.getProtocolList(), 'protocol');
            $scope.companySelectList[1] = util.objectListToString(dataService.getCompanyCategoryList(), 'category');
            $scope.companySelectList[2] = dataService.getBooleanList;
        });
    /*单位刷新完之后初始化签单人可选单位*/
    $scope.lordInit = function (d) {
        $scope.companyLordSelectList[0] = util.objectListToString(d, 'name');
    };
    /*选择单位*/
    $scope.chooseCompany = function (d) {
        $scope.initConditionCompany = 'company=' + util.wrapWithBrackets(d.name);
        $scope.initConditionCompanyPay = 'company=' + util.wrapWithBrackets(d.name);
    };
    /*选择结算*/
    $scope.chooseCompanyPay = function (companyPay) {
        $scope.initConditionCompanyDebtHistory = 'company_pay_serial=' + util.wrapWithBrackets(companyPay.companyPaySerial);
    };
    /*选择宴请签单人*/
    $scope.chooseFreeman = function (d) {
        $scope.initConditionFreeman = 'freeman=' + util.wrapWithBrackets(d.freeman);
    };
    /*进入单位结算界面*/
    $scope.pop = function (d) {
        popUpService.pop(d);
    };

    $scope.refresh = function () {
        window.location.reload();
    };
    /*单位欠款明细，这里用到ag-grid*/
    var columnDefs = [
        {headerName: "单位代码", field: "company"},
        {headerName: "签单人代码", field: "lord"},
        {headerName: "结账序列号", field: "paySerial"},
        {headerName: "单位挂账", field: "debt"},
        {headerName: "剩余挂账", field: "currentRemain"},
        {headerName: "操作种类", field: "category"},
        {headerName: "操作时间", field: "doTime"},
        {headerName: "操作员号", field: "userId"},
        {headerName: "单位结算用的", field: "currency"},
        {headerName: "币种额外信息", field: "currencyAdd"},
        {headerName: "描述", field: "description"},
        {headerName: "营业部门", field: "pointOfSale"}
    ];
    $scope.gridOptions = {
        columnDefs: columnDefs
    };
    /*单位挂账明细*/
    $scope.companyDebtRichFields = fieldService.getCompanyDebtRichFields();
    var getSzTableItem;
    $scope.getItem = function (getItem) {
        getSzTableItem = getItem;
    };
    /*结算这些明细*/
    $scope.companyPayThis = function () {
        var companyDebtList = getSzTableItem();
        var post = {};
        post.debtList = [];
        post.paySerialMap = {};
        post.debt = 0.0;
        /*先对选择的明细进行筛选，必须是同一个单位的*/
        for (var i = 0; i < companyDebtList.length; i++) {
            var companyDebt = companyDebtList[i];
            if (companyDebt.szPick) {
                if (!post.company) {
                    post.company = companyDebt.company;
                }
                if (post.company != companyDebt.company) {
                    messageService.setMessage({type: 'error', content: '所有单位名称必须一致'});
                    popUpService.pop('message');
                    return
                }
                if (companyDebt.companyPaid) {
                    messageService.setMessage({type: 'error', content: '请仔细检查，不能有含有已结标志的数据'});
                    popUpService.pop('message');
                    return
                }
                post.debt += companyDebt.debt;
                post.debtList.push(companyDebt);
                /*餐饮和桑拿不支持按照二级明细结算，所以这里就是一级明细*/
                if (companyDebt.paySerial) {
                    if (!post.paySerialMap[companyDebt.paySerial]) {
                        post.paySerialMap[companyDebt.paySerial] = companyDebt.debt;
                    } else {
                        post.paySerialMap[companyDebt.paySerial] += companyDebt.debt;
                    }
                }
            }
        }
        if (post.debtList.length == 0) {
            messageService.setMessage({type: 'error', content: '没有选择具体明细'});
            popUpService.pop('message');
            return
        }
        popUpService.pop('companyPay', null, null, post);
    };
    /*单位挂账汇总*/
    $scope.setInit = function () {
        $scope.showCompanySummaryReport = false;
    };
    var companySummaryFields = [
        {headerName: "单位名称", field: 'company', rowGroupIndex: 0},
        {headerName: "签单人", field: "lord"},
        {headerName: "宾客", field: "name"},
        {headerName: "金额", field: "debt", aggFunc: 'sum'},
        {headerName: "挂账时间", field: "companyDoTime", cellRenderer: agGridService.stdTimeRender},
        {headerName: "备注", field: "description"},
        {headerName: "模块", field: "pointOfSale"},
        {headerName: "发生时间", field: "debtDoTime", cellRenderer: agGridService.stdTimeRender},
        {headerName: "统计部门", field: "secondPointOfSale"},
        {headerName: "房号", field: "roomId"},
        {headerName: "接待员", field: "userId"},
        {headerName: "类型", field: "category"},
        {headerName: "已结标志", field: "companyPaid", cellRenderer: agGridService.stdBoolRender}
    ];

    $scope.companySummaryGridOptions = {
        columnDefs: companySummaryFields,
        animateRows: true,
        enableColResize: true,
        rowData: null,
        groupUseEntireRow: true,
        groupRowInnerRenderer: groupRowInnerRendererFunc,
        getContextMenuItems: getContextMenuItems,
        localeText: agGridService.getLocalText
    };
    function getContextMenuItems() {
        var params = {};
        params.processCellCallback = function (params) {
            if(params.column.cellRenderer){
                return params.column.cellRenderer(params);
            }else {
                return params.value;
            }
        };
        var result = [
            { // custom item
                name: '导出excel ',
                action: function () {
                    $scope.companySummaryGridOptions.api.exportDataAsExcel(params);
                }
            }
        ];

        return result;
    }

    function groupRowInnerRendererFunc(params) {
        var html = '';
        html += '<span> COUNTRY_NAME</span>'.replace('COUNTRY_NAME', params.node.key);
        html += '<span> 项目: COUNT</span>'.replace('COUNT', params.node.allChildrenCount);
        html += '<span> 欠款: GOLD_COUNT</span>'.replace('GOLD_COUNT', params.data.debt);

        return html;
    }

    $scope.companySummaryReport = function (module, company, beginTime, endTime, range) {
        var query = {};
        query.orderByList = ['company'];
        query.condition = ' company_paid=false';
        if (module != '全部') {
            query.condition += ' and point_of_sale=' + util.wrapWithBrackets(module);
        }
        if (range == '发') {
            if (beginTime) {
                query.condition += ' and debt_do_time>' + util.wrapWithBrackets(dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss'))
            }
            if (endTime) {
                query.condition += ' and debt_do_time<' + util.wrapWithBrackets(dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss'))
            }
        }
        if (range == '挂') {
            if (beginTime) {
                query.condition += ' and company_do_time>' + util.wrapWithBrackets(dateFilter(beginTime, 'yyyy-MM-dd HH:mm:ss'))
            }
            if (endTime) {
                query.condition += ' and company_do_time<' + util.wrapWithBrackets(dateFilter(endTime, 'yyyy-MM-dd HH:mm:ss'))
            }
        }
        dataService.refreshCompanyDebtRichList(query)
            .then(function (r) {
                $scope.companySummaryGridOptions.api.setRowData(r);
                $scope.showCompanySummaryReport = true;
            })
    }
}]);