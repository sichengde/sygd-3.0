App.controller('cookPrintHelperController', ['$scope', 'dataService', 'util', 'dateFilter', 'webService','agGridService', function ($scope, dataService, util, dateFilter, webService,agGridService) {
    var rowData;
    $scope.refresh = function () {
        rowData = [];
        dataService.initData(['refreshPointOfSaleList', 'deskInGetWithDetail'], [{condition: 'module=\'餐饮\''}, {orderByList: ['pointOfSale']}])
            .then(function () {
                var pointOfSaleList = dataService.getPointOfSale();
                var deskInList = dataService.getDeskInList();
                for (var i = 0; i < pointOfSaleList.length; i++) {
                    var pointOfSale = pointOfSaleList[i];
                    var pointOfSaleLeaf = {
                        group: pointOfSale.firstPointOfSale,
                        children: []
                    };
                    rowData.push(pointOfSaleLeaf);
                    for (var j = 0; j < deskInList.length; j++) {
                        var deskIn = deskInList[j];
                        if (deskIn.pointOfSale != pointOfSale.firstPointOfSale) {
                            continue;
                        }
                        var deskInLeaf = {
                            group: deskIn.desk,
                            children: []
                        };
                        pointOfSaleLeaf.children.push(deskInLeaf);
                        var lastCookRoom=null;
                        var cookRoomLeaf=null;
                        for (var k = 0; k < deskIn.deskDetailList.length; k++) {
                            var deskDetail = deskIn.deskDetailList[k];
                            if (!deskDetail.cookRoom) {
                                continue;
                            }
                            if (deskDetail.cookRoom != lastCookRoom) {
                                cookRoomLeaf = {
                                    group: deskDetail.cookRoom,
                                    children: []
                                };
                                deskInLeaf.children.push(cookRoomLeaf);
                                lastCookRoom = deskDetail.cookRoom;
                            }
                            cookRoomLeaf.children.push({
                                name: deskDetail.foodName,
                                num: deskDetail.num,
                                doTime: deskDetail.doTime,
                                remark: deskDetail.remark,
                                cooked: deskDetail.cooked,
                                id: deskDetail.id
                            })
                        }
                    }
                }
                $scope.gridOptions.api.setRowData(rowData);
            });
    };
    $scope.refresh();
    var columnDefs = [
        {headerName: "索引", cellRenderer: 'group'},
        {headerName: "名称", field: "name"},
        {headerName: "数量", field: "num"},
        {headerName: "备注", field: "remark"},
        {headerName: "下菜时间", field: "doTime", cellRenderer: agGridService.stdTimeRender},
        {
            headerName: "已做", field: "cooked", cellRenderer: agGridService.stdBoolRender
        }
    ];
    $scope.gridOptions = {
        columnDefs: columnDefs,
        enableColResize: true,
        enableSorting: true,
        getNodeChildDetails: getNodeChildDetails,
        getContextMenuItems: getContextMenuItems,
        onGridReady: function (params) {
            params.api.sizeColumnsToFit();
        }
    };

    function getNodeChildDetails(rowItem) {
        if (rowItem.group) {
            return {
                group: true,
                // provide ag-Grid with the children of this group
                children: rowItem.children,
                // this is not used, however it is available to the cellRenderers,
                // if you provide a custom cellRenderer, you might use it. it's more
                // relavent if you are doing multi levels of groupings, not just one
                // as in this example.
                field: 'group',
                // the key is used by the default group cellRenderer
                key: rowItem.group
            };
        } else {
            return null;
        }
    }

    function getContextMenuItems(params) {
        if (!params.node.group) {
            var result = [
                { // custom item
                    name: '设为已做 ',
                    action: function () {
                        webService.post('deskDetailCooked', {id: params.node.data.id, cooked: true})
                            .then(function () {
                                params.node.data.cooked=true;
                                params.api.refreshCells([params.node], ['cooked']);
                            })
                    }
                },
                { // custom item
                    name: '设为未做',
                    action: function () {
                        webService.post('deskDetailCooked', {id: params.node.data.id, cooked: false})
                            .then(function () {
                                params.node.data.cooked=false;
                                params.api.refreshCells([params.node], ['cooked']);
                            })
                    }
                }
            ];

            return result;
        }
    }

    $scope.collapseAll = function () {
        $scope.gridOptions.api.collapseAll();
    };
    $scope.expandAll = function () {
        $scope.gridOptions.api.expandAll();
    }
}]);