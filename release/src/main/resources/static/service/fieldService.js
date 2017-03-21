/**
 * Created by Administrator on 2017/3/4 0004.
 * 用来保存各种通用的fields
 */
App.factory('fieldService', [function () {
    var companyDebtRichFields = [
        {name: "单位名称", id: 'company', filter: 'input'},
        {name: "签单人", id: "lord", filter: 'input'},
        {name: "宾客", id: "name", filter: 'input'},
        {name: "金额", id: "debt"},
        {name: "挂账时间", id: "companyDoTime", filter: 'date'},
        {name: "备注", id: "description"},
        {name: "模块", id: "pointOfSale", filter: 'list'},
        {name: "发生时间", id: "debtDoTime", filter: 'date'},
        {name: "统计部门", id: "secondPointOfSale"},
        {name: "房号", id: "roomId", filter: 'input'},
        {name: "接待员", id: "userId"},
        {name: "类型", id: "category", filter: 'list'},
        {name: "已结标志", id: "companyPaid", boolean: 'true', filter: 'list'}
    ];
    var debtIntegrationFields = [
        {name: '时间', id: 'doTime'},
        {name: '房号', id: 'roomId'},
        {name: '统计部门', id: 'pointOfSale'},
        {name: '类型', id: 'category'},
        {name: '单位', id: 'company'},
        {name: '金额', id: 'consume', sum: 'true'},
        {name: '币种', id: 'currency'},
        {name: '说明', id: 'description'},
        {name: '操作员', id: 'userId'}
    ];
    var deskPayRichFields = [
        {name: '餐台', id: 'desk', width: '69px', filter: 'input'},
        {name: '币种', id: 'currency', width: '69px', filter: 'list'},
        {name: '额外币种信息', id: 'currencyAdd', width: '89px', filter: 'input'},
        {name: '金额', id: 'payMoney', sum: 'true', width: '69px'},
        {name: '开单时间', id: 'doTime', width: '129px', desc: '0', filter: 'date'},
        {name: '结账时间', id: 'doneTime', width: '129px', desc: '0', filter: 'date', filterInit: 'today'},
        {name: '结算序列号', id: 'ckSerial', width: '159px', filter: 'input'},
        {name: '操作员', id: 'userId', width: '69px', filter: 'list'},
        {name: '营业部门', id: 'pointOfSale', width: '109px', filter: 'list'},
        {name: '被取消', id: 'disabled', width: '69px', boolean: 'true', filter: 'list'}
    ];

    var deskPayRichFieldsRead = [
        {name: '餐台', id: 'desk'},
        {name: '币种', id: 'currency'},
        {name: '额外币种信息', id: 'currencyAdd'},
        {name: '金额', id: 'payMoney', sum: 'true'},
        {name: '开单时间', id: 'doTime'},
        {name: '结账时间', id: 'doneTime'},
        {name: '结算序列号', id: 'ckSerial'},
        {name: '操作员', id: 'userId'},
        {name: '营业部门', id: 'pointOfSale'},
        {name: '被取消', id: 'disabled'}
    ];

    function getCompanyDebtRichFields() {
        return companyDebtRichFields;
    }

    function getDebtIntegrationFields() {
        return debtIntegrationFields;
    }

    function getDeskPayRichFields(read) {
        if (read) {
            return deskPayRichFieldsRead;
        } else {
            return deskPayRichFields;
        }
    }

    return {
        getCompanyDebtRichFields: getCompanyDebtRichFields,
        getDeskPayRichFields: getDeskPayRichFields,
        getDebtIntegrationFields: getDebtIntegrationFields
    }
}]);
