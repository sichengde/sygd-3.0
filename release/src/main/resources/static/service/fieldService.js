/**
 * Created by Administrator on 2017/3/4 0004.
 * 用来保存各种通用的fields
 */
App.factory('fieldService', [function () {
    var companyDebtRichFields = [
        {name: "单位名称", id: 'company', filter: 'input'},
        {name: "签单人", id: "lord", filter: 'input'},
        {name: "宾客", id: "name",filter: 'input'},
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
    function getCompanyDebtRichFields() {
        return companyDebtRichFields;
    }
    return {getCompanyDebtRichFields: getCompanyDebtRichFields}
}]);
