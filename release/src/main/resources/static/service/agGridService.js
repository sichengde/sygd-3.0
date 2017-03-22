App.factory('agGridService',['dateFilter',function (dateFilter) {
    var stdTimeRender=function (params) {
        if (!params.node.group) {
            return dateFilter(params.value, 'yyyy-MM-dd HH:mm:ss');
        } else {
            return '';
        }
    };
    var stdBoolRender=function (params) {
        if (!params.node.group) {
            if (params.value) {
                return '是';
            } else {
                return '否';
            }
        } else {
            return '';
        }
    };
    var getColumnDef=function (defs,allColumnIds) {
        defs.forEach(function (columnDef) {
            if (columnDef.children) {
                getColumnDef(columnDef.children, allColumnIds)
            } else {
                if (columnDef.field) {
                    allColumnIds.push(columnDef.field);
                } else {
                    allColumnIds.push(columnDef.colId);
                }
            }
        });
    };
    var localeText = {
        export: '导出',
        csvExport: '导出为CSV',
        excelExport: '导出XLS',
        copy: '复制',
        paste: '粘贴',
        copyWithHeaders: '复制标题',
        autosizeAllColumns: '自适应',
        expandAll: '展开全部',
        collapseAll:'收起全部',
        toolPanel: '工具栏'
    };
    return {
        stdTimeRender: stdTimeRender,
        stdBoolRender: stdBoolRender,
        getColumnDef: getColumnDef,
        getLocalText:localeText
    }
}]);
