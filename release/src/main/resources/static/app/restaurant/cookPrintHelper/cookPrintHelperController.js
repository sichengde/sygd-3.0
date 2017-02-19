App.controller('cookPrintHelperController', ['$scope', 'dataService', 'util', function ($scope, dataService, util) {
    var rowData = [];
    $scope.refresh = function () {
        dataService.refreshDeskInList({orderByList: ['pointOfSale']})
            .then(function (deskInList) {
                //TODO:究竟怎么构建分类（厨打划单）
                /*var lastPOS='';
                 var lastDesk='';
                 var lastCookRoom='';
                 var nowPointOfSale;
                 var nowDesk;
                 var nowCookRoom;
                 for (var i = 0; i < deskInList.length; i++) {
                 var deskIn = deskInList[i];
                 if(lastPOS!=deskIn.pointOfSale){//新的营业部门
                 nowPointOfSale={
                 group:deskIn.pointOfSale,
                 children:[]
                 };
                 rowData.push(nowPointOfSale)
                 }
                 nowDesk={
                 group:deskIn.desk,
                 children:[]
                 };
                 nowPointOfSale.children.push(nowDesk);
                 for (var j = 0; j < deskIn.deskDetailList.length; j++) {
                 var deskdetail = deskIn.deskDetailList[j];

                 }
                 }*/
                var a = [
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
                $scope.gridOptions.api.setRowData(a)
            });
    };
    $scope.refresh();
    var columnDefs = [
        {headerName: "索引", cellRenderer: 'group'},
        {headerName: "名称", field: "name"},
        {headerName: "数量", field: "num"},
        {
            headerName: "已做", field: "cooked",  cellRenderer: innerCellRenderer
        }
    ];
    $scope.gridOptions = {
        columnDefs: columnDefs,
        enableColResize: true,
        getNodeChildDetails: getNodeChildDetails,
        onGridReady: function (params) {
            params.api.sizeColumnsToFit();
        }
    };

    function getNodeChildDetails(rowItem) {
        if (rowItem.group) {
            return {
                group: true,
                // provide ag-Grid with the children of this group
                children: rowItem.participants,
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

    function innerCellRenderer(params) {
        if (!params.node.group) {
            return '<input type="checkbox" /> '
        }else {
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