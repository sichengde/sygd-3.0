App.controller('cookPrintHelperController', ['$scope', 'dataService', 'util', 'dateFilter', 'webService', function ($scope, dataService, util, dateFilter, webService) {
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
        /*var a = [
         {
         group: '中餐厅',
         participants: [
         {
         group: '101',
         participants: [
         {
         group: '海鲜',
         participants: [
         {name: '黄花鱼', num: '1', cooked: false},
         {name: '扇贝', num: '5', cooked: false}
         ]
         },
         {
         group: '酒水',
         participants: [
         {name: '可乐', num: '10', cooked: true},
         {name: '雪碧', num: '10', cooked: true}
         ]
         }
         ]
         },
         {
         group: '102',
         title: '酒水',
         participants: [
         {
         group: '海鲜',
         participants: [
         {name: '黄花鱼', num: '1', cooked: false},
         {name: '扇贝', num: '5', cooked: false}
         ]
         },
         {
         group: '酒水',
         participants: [
         {name: '可乐', num: '10', cooked: true},
         {name: '雪碧', num: '10', cooked: true}
         ]
         }
         ]
         }
         ]
         },
         {
         group: '西餐厅',
         participants: [
         {
         group: '202',
         participants: [
         {
         group: '面点',
         participants: [
         {name: '馒头', num: '1', cooked: true},
         {name: '花卷', num: '5', cooked: false}
         ]
         },
         {
         group: '热菜',
         participants: [
         {name: '小鸡炖蘑菇', num: '1', cooked: false},
         {name: '干豆腐', num: '1', cooked: false}
         ]
         }
         ]
         },
         {
         group: '203',
         participants: [
         {
         group: '面点',
         participants: [
         {name: '馒头', num: '1', cooked: true},
         {name: '花卷', num: '5', cooked: false}
         ]
         },
         {
         group: '热菜',
         participants: [
         {name: '小鸡炖蘑菇', num: '1', cooked: false},
         {name: '干豆腐', num: '1', cooked: false}
         ]
         }
         ]
         }
         ]
         }
         ];
         $scope.gridOptions.api.setRowData(a)*/
    };
    $scope.refresh();
    var columnDefs = [
        {headerName: "索引", cellRenderer: 'group'},
        {headerName: "名称", field: "name"},
        {headerName: "数量", field: "num"},
        {headerName: "备注", field: "remark"},
        {headerName: "下菜时间", field: "doTime", cellRenderer: timeCellRender},
        {
            headerName: "已做", field: "cooked", cellRenderer: innerCellRenderer
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

    function innerCellRenderer(params) {
        if (!params.node.group) {
            if (params.value) {
                return '是';
            } else {
                return '否';
            }
        } else {
            return '';
        }
    }

    function timeCellRender(params) {
        if (!params.node.group) {
            return dateFilter(params.value, 'yyyy-MM-dd HH:mm:ss');
        } else {
            return '';
        }
    }

    $scope.collapseAll = function () {
        $scope.gridOptions.api.collapseAll();
    };
    $scope.expandAll = function () {
        $scope.gridOptions.api.expandAll();
    }
}]);