/**
 * Created by 舒展 on 2016-05-09.
 * 页面管理，table增删改查
 * items:之前的szTableEasy中的items，用于链接外部的数据，设置了他，就不再向数据库提交内容了
 * fields:表头和数据源对应的数据库中的名字
 * itemsPerPage:每页显示多少条数据
 * selectList:下拉框列表
 * permission:编辑该表格需要拥有的权限
 * button:增删改哪个不要
 * deepSearch:深度搜索，当数据量特别大时需要启用
 * head:是否需要表头
 * outerRefresh:刷新后执行的外部方法
 * rowClick:点击行事件
 * itemClick:点击表格
 * alwaysAdd:持续添加（点完保存后可以继续添加）
 * searchCondition:固定搜索条件
 * initRefresh:将初始化方法赋值到外部，可以在外部刷新,调用时需要加个参数f，为传递出去的参数，然后自行赋值//现在已经不能传递筛选条件，只能单纯刷新(例如有items时外部刷新赋值item后必须要手动调用一下刷新方法，否则数据不会显示)，改用监听searchCondition实现
 * rightClick:右键弹出画面的文件名
 * state:初始化状态，有些空表，例如散客开房的宾客，初始化就应该是新增状态add
 * maxNum:最大值，如果数据较多的话，可以先设置一个最大值
 * scroll:是否滚动
 * print:szTable是否打印
 * printNow:立即预览-针对手机端，自动获取的一般表格宽度生成之后数据还有有生成，手动获取的一般数据生成时表格宽度还没有生成，所以有两处打印
 * title:打印时的标题
 * queryMessage:查询信息，也就是这个报表的筛选条件
 * itemChange:修改之后调用的外部方法
 * remark:外部生成的备注，描述一些总结，例如菜品统计表中统计一下各个品种的总和
 * initState:初始状态（增加，删除，修改）
 * backUp:在外部直接生成数据时，并且可以进一步增删改查，用这个属性对table中的备份数据赋值(后改为方法，在外部进行备份，标准用法在团队开房里)
 * ---------------------------------------------------------
 * field中内容
 * name:每列的标题
 * id:对应数据库中的字段名
 * exp:表达式，
 * copy：新增之后该字段值是否默认保留，例如添加房号的时候，房类默认保留
 * selectId:是否启用下拉列表
 * freeSelect:使用sz-select方式读取list，适用于某些大数据，例如菜品太多了，定义套餐时就用这个
 * width：宽度
 * notNull:输入验证非空，填写为zero则为不能等于0
 * static:字段内容不可编辑,填写的是表达式
 * filter:标题过滤插件，如果该值为date，则弹出时间段选择，否则弹出下拉列表//可选值：date,list,input
 * filterFather:父级过滤，属性值为父对象id，父对象变化时，可选值下拉列表也会经过重新筛选，注意，每一个field只能添加一个父级菜单
 * filterInit:过滤器初始值
 * filterContent:内容过滤器，例如营业部门切换时可选的销售点也要跟着切换
 * filterContentId:内容过滤器条件
 * date:采用日历控件，如果其中值为short，则不显示时间
 * default:默认值，新增的时候会直接出现
 * length:字段长度必须等于他
 * asc:升序排列，属性值为第几个
 * desc:降序排列，属性值为第几个
 * sum:加起来，最后出现在表末尾
 * float:需要转换为float的字段，否则排序时会按照字符串排序
 * boolean:修改时input的type变为checkbox类型
 * selectListField:下拉框表如果是个对象数组，则该值为对应的字段
 * popChoose:下拉列表为复选，因此会弹出一个复选框
 * itemChange:配合上边的相同参数，看看哪些域在改变时需要调用外部方法
 * unique:不可重复
 * -----------------------------------------------------------
 * 用法：
 * 1.不想显示的字段，但添加到数据库中时需要有默认值，可不设置name属性，并且把default设置为默认值
 * 2.搜索分为内部条件和外部条件，内部条件为在列表头筛选，外部条件则是在controller里设置，刷新数据的时候会综合内部条件与外部条件
 * 3.外部刷新的话如果只是单纯的刷新可以通过initRefresh实现，如果需要改变查询条件则需要通过修改searchCondition实现
 * 4.深度搜索只针对于数据库，静态数组直接左上角模糊查询吧
 */
App.directive('szTable', ['$filter', function ($filter) {
    return {
        restrict: 'E',
        scope: {
            items: '=?',
            fields: '=',
            itemsPerPage: '@',
            selectList: '=',
            permission: '@',
            button: '@',
            filter: '@',
            deepSearch: '@',
            head: '=',
            outerRefresh: '&',
            rowClick: '&',
            itemClick: '&',
            alwaysAdd: '@',
            searchCondition: '=?',
            initRefresh: '&',
            rightClick: '@',
            state: '@',
            maxNum: '@',
            scroll: '=?',
            print: '@',
            printNow: '=?',
            queryMessage: '=?',
            itemChange: '&',
            remark: '=?',
            initState: '@',
            backUp: '&'
        },
        controller: ['$scope', 'webService', 'util', 'dataService', '$parse', '$attrs', 'messageService', 'popUpService', 'nullToStringFilter', 'dateFilter', function ($scope, webService, util, dataService, $parse, $attrs, messageService, popUpService, nullToStringFilter, dateFilter) {
            var backUpItem;//备用数据，用于比较修改了哪些数据
            var checkFn;//输入验证用到
            var wrongList = {};//保存所有不满足输入条件的出错的字段，必须全部验证通过方可提交;不可为空的
            var wrongList2 = {};//保存所有不满足输入条件的出错的字段，必须全部验证通过方可提交;不能重复的
            var condition = '';//内部查询条件
            var orderByList = [];//内部查询排序方式
            var orderByListDesc = [];//内部查询排序方式(降序)
            var floatField = [];//刷新出来数据后需要转换为float的字段，否则排序时会按照字符串排序
            $scope.debug = dataService.getOtherParamMapValue('调试模式');
            if (!$scope.button) {
                $scope.button = [];
            }
            if (!$scope.itemsPerPage) {
                $scope.itemsPerPage = 10;
            }
            if (!$scope.scroll) {
                $scope.scroll = false;
            }
            if (!$scope.print) {
                $scope.print = false;
            }
            /*初始化时间map*/
            $scope.beginTime={};
            $scope.endTime={};
            $scope.prefix = $attrs.fields.substring(0, $attrs.fields.indexOf('Fields'));
            $scope.parse = $parse;//分析表达式
            $scope.reverse = false;
            $scope.filteredItems = [];
            $scope.groupedItems = [];
            $scope.pagedItems = [];
            $scope.currentPage = 0;
            var updateList = [];
            var deleteList = [];
            $scope.addItem = {};//新增时需要提交的对象
            $scope.state = 'none';//初始状态设置为none
            $scope.showAllPage = false;
            /*点击之后显示所有页面*/
            $scope.showAllPageClick = function () {
                $scope.showAllPage = true;
            };
            /**
             * 页面刷新查找排序等功能模块
             */
            var searchMatch = function (haystack, needle) {
                if (needle == undefined || needle == '') {
                    return true;
                }
                return String(haystack).indexOf(needle) !== -1;
            };

            // init the filtered items
            function search() {
                $scope.filteredItems = $filter('filter')($scope.items, function (item) {
                    if ($scope.query != undefined) {
                        var query = $scope.query.split(' ');
                        var queryResult;
                        for (var i = 0; i < query.length; i++) {
                            for (var attr in $scope.fields) {
                                if (searchMatch(item[$scope.fields[attr].id], query[i])) {
                                    queryResult = true;
                                    break;
                                } else {
                                    queryResult = false;
                                }
                            }
                            if (queryResult == false) {
                                return false;
                            }
                        }
                    }
                    return true;
                });
                // take care of the sorting order在界面上用orderBy实现了
                /*if ($scope.sortingOrder !== '') {
                 $scope.filteredItems = $filter('orderBy')($scope.filteredItems, $scope.sortingOrder, $scope.reverse);
                 }*/
                $scope.currentPage = 0;
                // now group by pages
                $scope.groupToPages();
            }


            // calculate page in place
            $scope.groupToPages = function () {
                $scope.pagedItems = [];
                if ($scope.filteredItems && $scope.itemsPerPage != '') {
                    for (var i = 0; i < $scope.filteredItems.length; i++) {
                        if (i % $scope.itemsPerPage === 0) {
                            $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)] = [$scope.filteredItems[i]];
                        } else {
                            $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)].push($scope.filteredItems[i]);
                        }
                    }
                }
            };

            $scope.range = function (start, end) {
                var ret = [];
                if (!end) {
                    end = start;
                    start = 0;
                }
                for (var i = start; i < end; i++) {
                    ret.push(i);
                }
                return ret;
            };

            $scope.prevPage = function () {
                if ($scope.currentPage > 0) {
                    $scope.currentPage--;
                }
            };

            $scope.nextPage = function () {
                if ($scope.currentPage < $scope.pagedItems.length - 1) {
                    $scope.currentPage++;
                }
            };

            $scope.setPage = function (n) {
                $scope.currentPage = n;
            };

            // change sorting order
            $scope.sort_by = function (newSortingOrder) {
                $('th i').each(function () {
                    // icon reset
                    $(this).removeClass()
                });
                if ($scope.sortingOrder == newSortingOrder) {
                    if ($scope.reverse == false) {
                        $scope.reverse = true;
                        $('th.' + newSortingOrder + ' i').addClass('icon-chevron-up');
                    }
                    else if ($scope.reverse == true) {
                        $scope.sortingOrder = null;
                        $('th.' + newSortingOrder + ' i').removeClass();
                    }
                } else {
                    $scope.sortingOrder = newSortingOrder;
                    $scope.reverse = false;
                    $('th.' + newSortingOrder + ' i').addClass('icon-chevron-down');
                }
                search();
            };

            /*刷新数据，重新搜索*/
            $scope.refreshAndSearch = function () {
                if ($attrs.items) {
                    if (!$scope.items) {//数据还没产生，例如通过异步获取
                        return
                    }
                    backUpItem = angular.copy($scope.items);
                    $scope.totalNum = $scope.items.length;
                    if ($attrs.outerRefresh) {
                        $scope.outerRefresh({d: $scope.items});
                    }
                    search();
                } else {
                    var p = {};
                    /*拼接外部刷新条件与内部刷新条件*/
                    if (condition && !$scope.searchCondition) {
                        p.condition = util.parseSearch(condition, $scope.fields);
                    } else if ($scope.searchCondition && !condition) {
                        p.condition = $scope.searchCondition;
                    }
                    if (condition && $scope.searchCondition) {
                        p.condition = condition + ' and ' + $scope.searchCondition;
                    }
                    if ($scope.maxNum) {
                        p.num = $scope.maxNum;
                    }
                    if (orderByList.length > 0) {
                        p.orderByList = orderByList;
                    }
                    if (orderByListDesc.length > 0) {
                        p.orderByListDesc = orderByListDesc;
                    }
                    var f = 'refresh' + $scope.prefix.replace(/(\w)/, function (v) {
                            return v.toUpperCase()
                        }) + 'List';
                    dataService[f](p)
                        .then(function (d) {
                            $scope.items = d;
                            angular.forEach($scope.items, function (item) {
                                angular.forEach(floatField, function (field) {
                                    item[field] = parseFloat(item[field]);
                                })
                            });
                            backUpItem = angular.copy(d);
                            $scope.totalNum = d.length;
                            if ($attrs.outerRefresh) {
                                $scope.outerRefresh({d: d});
                            }
                            search();
                        })
                }
            };

            /**
             * 通过表头的下拉框进行过滤
             * @param r 所在的field
             * @param r2 选择的过滤项
             * nowFilter:表示该field正处于过滤阶段（选上了），如果是全部则该字段为null
             */
            $scope.filterFieldList = function (r, r2) {
                r.nowFilter = r2;
                condition = '';
                angular.forEach($scope.fields, function (field) {
                    if (field.nowFilter && field.filter == 'list') {
                        /*生成查询条件*/
                        if (field.nowFilter != '--空--')
                            if (isNaN(field.nowFilter)) {
                                condition += field.id + '=\'' + field.nowFilter + '\' and ';
                            } else {
                                condition += field.id + '=' + field.nowFilter + ' and ';
                            }
                        else if (field.nowFilter == '--空--') {
                            condition += field.id + ' is null and ';
                        }
                    } else if (field.nowFilter && field.filter == 'input') {
                        condition += field.id + ' like \'%' + field.nowFilter + '%\' and ';
                    } else if (field.filter == 'date') {
                        if (field.beginTime) {
                            condition += field.id + '>\'' + $filter('date')(field.beginTime, 'yyyy-MM-dd 00:00:00') + '\' and ';
                        }
                        if (field.endTime) {
                            condition += field.id + '<\'' + $filter('date')(field.endTime, 'yyyy-MM-dd 24:00:00') + '\' and '
                        }
                    }
                    /*缩小关联下拉表的查询范围*/
                    if (field.filterFather == r.id && r.nowFilter) {
                        if (field.filter == 'list') {//没有选择的情况下才会刷新，如果已经选择完了，则不刷新了
                            var baseSql = 'select distinct ' + util.parseSearch(field.id) + ' from ' + util.parseSearch($scope.prefix) + ' where ' + util.parseSearch(r.id);
                            if (isNaN(field.nowFilter)) {
                                baseSql += '=\'' + r2 + '\'';
                            } else {
                                baseSql += '=' + r2;
                            }
                            webService.post('sql', baseSql)
                                .then(function (d) {
                                    field.filterList = nullToStringFilter(d);
                                })
                        }
                    }
                });
                condition = condition.substring(0, condition.length - 5);//刨除最后一个and
                condition = util.parseSearch(condition, $scope.fields);
                $scope.refreshAndSearch();
            };
            /**
             * 跟上边一样，只不过这个是先处理一下时间，再调用上边的方法
             */
            $scope.filterFieldDate = function (r, beginTime, endTime) {
                r.beginTime = beginTime;
                r.endTime = endTime;
                $scope.filterFieldList(r, true);
            };
            /**
             * 数据访问模块
             */
            /*初始化过滤下拉列表，排序，循环处理fields*/
            $scope.haveFilter = false;
            var haveInit = false;//有初始化值
            var prev;
            var next;
            angular.forEach($scope.fields, function (field) {
                if (field.filter == 'list') {
                    webService.post('sql', 'select distinct ' + util.parseSearch(field.id) + ' from ' + util.parseSearch($scope.prefix))
                        .then(function (d) {
                            field.filterList = nullToStringFilter(d);
                        })
                }
                if (field.filter) {
                    $scope.haveFilter = true;
                }
                if (field.asc) {
                    orderByList[field.asc] = field.id;
                }
                if (field.desc) {
                    orderByListDesc[field.desc] = field.id;
                }
                if (field.filterInit) {//初始化
                    if (field.filter == 'list') {
                        field.nowFilter = field.filterInit;
                    } else {//时间类型初始化
                        if (field.filterInit == 'today') {
                            field.beginTime = util.getTodayMin();
                            $scope.beginTime[field.id] = util.getTodayMin();
                            field.endTime = util.getTodayMax();
                            $scope.endTime[field.id] = util.getTodayMax();
                        }
                    }
                    haveInit = true;
                }
                if (field.float) {
                    floatField.push(field.id);
                }
                /*看看有没有在浏览器记录的宽度，有的话初始化*/
                var width = localStorage.getItem($scope.prefix + '-' + field.id);
                if (width) {
                    field.width = width + 'px';
                }
                /*初始化next和prev*/
                if (prev) {
                    prev.next = field;
                    field.prev = prev;
                }
                prev = field;
            });
            if (haveInit) {//有初始化值就要执行一遍
                $scope.filterFieldList({}, null);
            } else {
                $scope.refreshAndSearch()
            }

            if ($attrs.initRefresh) {
                $scope.initRefresh({f: $scope.refreshAndSearch});
            }

            $scope.change = function () {
                search();
            };
            $scope.addBegin = function () {
                $scope.state = 'add';
                if (!$scope.addItem) {
                    $scope.addItem = {};
                }
                for (var j = 0; j < $scope.fields.length; j++) {
                    var obj = $scope.fields[j];
                    /*看看有没有默认值*/
                    if (obj.default) {
                        /*表达式值*/
                        $scope.addItem[obj.id] = obj.default;
                    }
                    /*检验有没有非空字段*/
                    if (obj.notNull && ($scope.addItem[obj.id] == '' || !$scope.addItem[obj.id])) {
                        $scope.wrongMsg = '不可为空';
                        wrongList[obj.$$hashKey] = false;
                    }
                }
            };
            $scope.updateBegin = function () {
                $scope.state = 'update';
            };
            $scope.deleteBegin = function () {
                $scope.state = 'delete';
            };
            $scope.addChoose = function (field) {
                var var1 = field.$$hashKey;
                if ((field.notNull && $scope.addItem[field.id] == '') || (field.notNull == 'zero' && $scope.addItem[field.id] == 0)) {
                    $scope.wrongMsg = '不可为空';
                    wrongList[var1] = false;
                }
                else if (field.notNull) {
                    delete wrongList[var1];
                    var i = 0;
                    angular.forEach(wrongList, function (value, key) {
                        i++;
                    });
                    if (i == 0) {
                        $scope.wrongMsg = null;
                    }
                }
                /*不能重复*/
                if (field.unique) {
                    if (util.getValueByField($scope.items, field.id, $scope.addItem[field.id])) {
                        $scope.wrongMsg2 = true;
                        wrongList2[var1] = false;
                    } else {
                        delete wrongList2[var1];
                        var i = 0;
                        angular.forEach(wrongList2, function (value, key) {
                            i++;
                        });
                        if (i == 0) {
                            $scope.wrongMsg2 = null;
                        }
                    }
                }
                if (field.itemChange) {
                    $scope.itemChange({item: $scope.addItem, field: field});
                }
            };
            $scope.deleteChoose = function (item) {
                if (item.ifDelete) {
                    deleteList.push(item);
                } else {
                    util.deleteFromArray(deleteList, 'id', item.id);
                }
            };
            $scope.updateChoose = function (item, field) {
                var var1 = item.$$hashKey + field.$$hashKey;
                if ((field.notNull && item[field.id] == '') || (field.notNull == 'zero' && item[field.id] == 0)) {
                    $scope.wrongMsg = '不可为空';
                    wrongList[var1] = false;
                }
                else if (field.notNull) {
                    delete wrongList[var1];
                    var i = 0;
                    angular.forEach(wrongList, function (value, key) {
                        i++;
                    });
                    if (i == 0) {
                        $scope.wrongMsg = null;
                    }
                }
                /*不能重复*/
                if (field.unique) {
                    if (util.getValueByField(backUpItem, field.id, item[field.id])) {
                        $scope.wrongMsg2 = true;
                        wrongList2[var1] = false;
                    } else {
                        delete wrongList2[var1];
                        var i = 0;
                        angular.forEach(wrongList2, function (value, key) {
                            i++;
                        });
                        if (i == 0) {
                            $scope.wrongMsg2 = null;
                        }
                    }
                }
                if (!util.getValueByField(updateList, 'id', item.id)) {
                    updateList.push(item);
                } else {
                    util.deleteFromArray(updateList, 'id', item.id);
                    updateList.push(item);
                }
                item.needUpdate = true;//提交后需要在外部重新刷新数据否则一直是true
                if (field.itemChange) {
                    $scope.itemChange({item: item, field: field});
                }
            };
            $scope.deleteAll = function () {
                var i;
                var obj;
                if (deleteList.length > 0) {
                    deleteList.length = 0;
                    for (i = 0; i < $scope.pagedItems[$scope.currentPage].length; i++) {
                        obj = $scope.pagedItems[$scope.currentPage][i];
                        obj.ifDelete = false;
                    }
                } else {
                    for (i = 0; i < $scope.pagedItems[$scope.currentPage].length; i++) {
                        obj = $scope.pagedItems[$scope.currentPage][i];
                        deleteList.push(obj);
                        obj.ifDelete = true;
                    }
                }
            };
            $scope.cancel = function () {
                $scope.state = 'none';
                $scope.addItem = {};
                $scope.wrongMsg = null;
                deleteList = [];
                updateList = [];
                if(!$scope.backUp) {
                    $scope.items = angular.copy(backUpItem);
                }else {
                    $scope.backUp();
                }
                //$scope.refreshAndSearch();
            };
            $scope.add = function () {
                for (var i = 0; i < $scope.fields.length; i++) {
                    /*输入验证*/
                    var obj = $scope.fields[i];
                    if (obj.length && $scope.addItem[obj.id].length != obj.length) {
                        messageService.setMessage({type: 'error', content: obj.name + '长度必须为' + obj.length});
                        popUpService.pop('message');
                        return;
                    }
                    /*生成exp*/
                    if (obj.exp) {
                        $scope.addItem[obj.id] = $parse(obj.exp)($scope.addItem)
                    }
                }
                if ($attrs.items) {
                    $scope.items.push(angular.copy($scope.addItem));
                    backUpItem = angular.copy($scope.items);
                    if (!$scope.alwaysAdd) {
                        $scope.state = 'none';
                    }
                    /*没有copy属性的字段清零*/
                    for (var i = 0; i < $scope.fields.length; i++) {
                        if (!$scope.fields[i].copy) {
                            $scope.addItem[$scope.fields[i].id] = null;
                        }
                    }
                    search();
                    if (checkFn) {
                        checkFn();
                    }
                } else {
                    webService.post($scope.prefix + 'Add', $scope.addItem)
                        .then(function () {
                            if (!$scope.alwaysAdd) {
                                $scope.state = 'none';
                                for (var i = 0; i < $scope.fields.length; i++) {
                                    if (!$scope.fields[i].copy) {
                                        $scope.addItem[$scope.fields[i].id] = null;
                                    }
                                }
                            } else {
                                /*没有copy属性的字段清零*/
                                for (var i = 0; i < $scope.fields.length; i++) {
                                    var obj = $scope.fields[i];
                                    if (!$scope.fields[i].copy) {
                                        $scope.addItem[$scope.fields[i].id] = null;
                                    }
                                    /*非空字段判断*/
                                    if (obj.notNull && ($scope.addItem[obj.id] == '' || !$scope.addItem[obj.id])) {
                                        $scope.wrongMsg = '不可为空';
                                        wrongList[obj.$$hashKey] = false;
                                    }
                                }
                            }
                            $scope.refreshAndSearch();
                            if (checkFn) {//非空判断，有了的话要判断一下然后置为y
                                checkFn();
                            }
                        });
                }
            };
            $scope.delete = function () {
                if ($attrs.items) {//只刷新数据，不计表
                    for (var i = 0; i < deleteList.length; i++) {
                        util.deleteFromArray($scope.items, '$$hashKey', deleteList[i].$$hashKey)
                    }
                    $scope.state = 'none';
                    backUpItem = angular.copy($scope.items);
                    search();
                    deleteList = [];
                } else {
                    webService.post($scope.prefix + 'Delete', deleteList)
                        .then(function () {
                            $scope.state = 'none';
                            $scope.refreshAndSearch();
                            deleteList = [];
                        })
                }
            };
            /**
             * 更新时会在服务器端比较新旧数据的不同
             */
            $scope.update = function () {
                if ($attrs.items) {
                    $scope.state = 'none';
                    backUpItem = angular.copy($scope.items);
                    search();
                    updateList = [];
                } else {
                    var submit = [];
                    for (var i = 0; i < updateList.length; i++) {
                        submit = submit.concat(util.getValueByField(backUpItem, 'id', updateList[i].id));
                    }
                    webService.post($scope.prefix + 'Update', updateList.concat(submit))
                        .then(function () {
                            $scope.state = 'none';
                            $scope.refreshAndSearch();
                            updateList = [];
                        })
                }
            };
            $scope.$watchCollection('items', function (newItems, oldItems) {
                if (!newItems) {
                    return
                }
                if (!oldItems) {
                    oldItems = [];
                }
                if (newItems.length != oldItems.length && checkFn) {
                    checkFn();
                }
                search();
            });
            /*$scope.$watch('backUp', function (value) {
                backUpItem = value;
            });*/
            $scope.$watch('searchCondition', function (value) {
                if (value) {
                    $scope.refreshAndSearch();
                }
            });
            $scope.$watch('pagedItems[currentPage]', function () {
                $scope.sumMessage = '合计 ';
                angular.forEach($scope.fields, function (field) {
                    var total = 0.0;
                    if (field.sum == 'true') {
                        angular.forEach($scope.pagedItems[$scope.currentPage], function (value) {
                            if (field.exp) {
                                total += $parse(field.exp)(value) * 1;
                            } else {
                                total += value[field.id] * 1;
                            }
                        });
                        $scope.sumMessage += field.name + '：' + total + ' ';
                    }
                });
                /*看看有没有自动打印，自动获取的此时打印*/
                if ($scope.printNow&&$scope.items) {
                    $scope.szTableReport('pdf');
                }
            });
            /**
             * 页面事件，选择某行
             */
            $scope.chooseRow = function (r) {
                if ($attrs.rowClick) {
                    $scope.rowClick({d: r});
                }
                angular.forEach($scope.pagedItems[$scope.currentPage], function (value) {//添加hover样式 刘丹
                    value.selected = false;
                });
                r.selected = true;
            };
            /**
             * 页面事件，选择某单元
             */
            $scope.chooseItem = function (r, r2) {
                if ($attrs.itemClick) {
                    $scope.itemClick({item: r, id: r2.id});
                }
            };
            /*打印报表*/
            $scope.szTableReport = function (format) {
                var szTable = {};
                /*计算表头数组，宽度数组,计算总宽度*/
                var columnHeaders = [];
                var widthList = [];
                var totalWidth = 0;
                angular.forEach($scope.fields, function (field) {
                    columnHeaders.push(field.name);
                    var element=angular.element(document.getElementById($scope.prefix + field.id));
                    var currentWidth;
                    if(element.context) {
                        currentWidth = element.width();
                    }else {
                        currentWidth=100;
                    }
                    widthList.push(currentWidth);
                    totalWidth = totalWidth + currentWidth;
                });
                /*计算数据模块*/
                var fieldBase = 'field';
                var templateList = [];
                angular.forEach($scope.items, function (item) {
                    var index = 1;
                    var fieldTemplate = {};
                    angular.forEach($scope.fields, function (field) {
                        if (field.exp) {//如果字段是计算的
                            fieldTemplate[fieldBase + index] = $parse(field.exp)(item);
                        } else {
                            if (field.id.indexOf('Time') > -1 || field.id.indexOf('time') > -1) {
                                fieldTemplate[fieldBase + index] = dateFilter(item[field.id], 'yyyy-MM-dd HH:mm:ss');
                            } else {
                                fieldTemplate[fieldBase + index] = item[field.id];
                            }
                        }
                        index++;
                    });
                    templateList.push(fieldTemplate);
                });
                /*增加固定参数*/
                var params = [];
                params.push($scope.print);
                /*查询条件分为外部和内部，外部是固定的，内部每次重新生成*/
                var backUpQueryMessage = $scope.queryMessage;
                if ($scope.deepSearch) {//没有深度搜索，快速查找并不会影响报表
                    if (!$scope.queryMessage) {//没有外部条件的话，设置为''
                        $scope.queryMessage = '';
                    }
                    for (var i = 0; i < $scope.fields.length; i++) {
                        var field = $scope.fields[i];
                        if ((field.filter == 'list' || field.filter == 'input') && field.nowFilter) {
                            $scope.queryMessage += ' ' + field.name + ':' + field.nowFilter;
                        }
                        if (field.filter == 'date') {
                            if (field.beginTime) {
                                $scope.queryMessage += ' ' + field.name + '(起始):' + dateFilter(field.beginTime, 'yyyy-MM-dd HH:mm:ss');
                            }
                            if (field.endTime) {
                                $scope.queryMessage += ' ' + field.name + '(结束):' + dateFilter(field.endTime, 'yyyy-MM-dd HH:mm:ss');
                            }
                        }
                    }
                }
                params.push($scope.queryMessage);
                var remark = '';
                if ($scope.sumMessage != '合计 ' && $scope.remark) {//只有一个合计就是没有需要统计的列，就不加这个参数了
                    remark = $scope.sumMessage + '\\n' + $scope.remark;
                } else if ($scope.sumMessage != '合计 ') {
                    remark = $scope.sumMessage;
                } else if ($scope.remark) {
                    remark = $scope.remark;
                }
                params.push(remark);
                szTable.templateList = templateList;
                szTable.widthList = widthList;
                szTable.totalWidth = totalWidth;
                szTable.columnHeaders = columnHeaders;
                szTable.parameters = params;
                szTable.format = format;
                webService.post('szTableReport', szTable)
                    .then(function (r) {
                        webService.openReport(r);
                        $scope.queryMessage = backUpQueryMessage;
                    })
            };
            /*弹出复选框*/
            var tempItem;
            var tempField;
            $scope.popChoose = function (item, field) {
                tempField = field;
                tempItem = item;
                $scope.chooseSelectList = [];
                var existList;
                if (item[field.id]) {
                    existList = item[field.id].split(',');
                } else {
                    existList = '';
                }
                var nameList = $scope.selectList[field.selectId];
                for (var i = 0; i < nameList.length; i++) {
                    var check = existList.indexOf(nameList[i]) > -1;
                    $scope.chooseSelectList.push({name: nameList[i], check: check});
                }
                popUpService.pop('szPopChoose', null, $scope.popChooseOver, $scope.chooseSelectList);
            };
            /*处理选择结果*/
            $scope.popChooseOver = function () {
                tempItem[tempField.id] = '';
                for (var i = 0; i < $scope.chooseSelectList.length; i++) {
                    var obj = $scope.chooseSelectList[i];
                    if (obj.check) {
                        tempItem[tempField.id] += obj.name + ',';
                    }
                }
                tempItem[tempField.id] = tempItem[tempField.id].substring(0, tempItem[tempField.id].length - 1);
                if (tempItem === $scope.addItem) {
                    $scope.addChoose(tempField);
                } else {
                    $scope.updateChoose(tempItem, tempField);
                }
            };

            /**
             * 关闭事件，暂时不启用
             */
            /*$scope.$on('$destroy',function () {
             alert('close');
             });*/

            /*页面加载完成之后，赋值table每个列的宽度*/
            $scope.$on('szTableComplete', function (event, data) {
                if (data.context) {//有定义，说明是网页的
                    while (data.length != 0) {
                        var id = data.attr('class');
                        util.getValueByField($scope.fields, 'id', id).width = data.width();
                        data = data.prev();
                    }
                }else {//说明是ionic的，都初始化为100完事
                    angular.forEach($scope.fields,function (value) {
                        value.width=100;
                    })
                }

                /*看看有没有初始状态*/
                switch ($attrs.initState) {
                    case 'add':
                        $scope.addBegin();
                        break;
                    case 'update':
                        $scope.updateBegin();
                        break;
                    case 'delete':
                        $scope.deleteBegin();
                        break;
                }
                /*看看需不需要立即打印，手动生成的此时打印*/
                if ($scope.printNow&&$scope.items) {
                    $scope.szTableReport('pdf');
                }
            });
            /**
             * 控制器方法
             */
            this.getLength = function () {
                return $scope.items.length;
            };
            /*szNotNull中的验证方法，这个是判断整个szTable不能为空的*/
            this.initCheck = function (fn) {
                checkFn = fn;
            }
        }],
        templateUrl: function () {
            return '../directive/szTable.html';
        }
    }
}]);
App.directive('szTableInitWidth', ['$timeout', function ($timeout) {
    return {
        restrict: 'A',
        compile: function () {
            return {
                pre: function preLink(scope, element, attributes, ctrl) {
                },
                post: function postLink(scope, element, attributes) {
                    if (scope.$last === true) {
                        $timeout(function () {
                            scope.$emit('szTableComplete', element);
                        });
                    }
                }
            };
        }
    }
}]);
App.directive('szResizeable', ['$document', function ($document) {
    return {
        restrict: 'A',
        link: function (scope, ele) {
            var beginX;
            var beginWidth;
            var eleDiv = ele.find('div');
            var finalWidth;
            var nextFinalWidth;
            var beginMove;
            var nextBeginWidth;
            eleDiv.bind('mousedown', function (event) {//Liu alter
                beginX = event.pageX;
                beginWidth = scope.r.width;
                nextBeginWidth = scope.r.next.width;
                $document.on('mousemove', mousemove);
                $document.on('mouseup', mouseup);
                beginMove = true;
            });
            function mousemove(event) {
                var x = (event.pageX - beginX);
                scope.$apply(function () {
                    scope.r.width = x + beginWidth;
                    scope.r.next.width = nextBeginWidth - x;
                });
                finalWidth = x + beginWidth;
                nextFinalWidth = nextBeginWidth - x;
            }

            function mouseup() {
                if (beginMove) {
                    localStorage.setItem(scope.prefix + '-' + scope.r.id, finalWidth);
                    localStorage.setItem(scope.prefix + '-' + scope.r.next.id, nextFinalWidth);
                    beginMove = false;
                }
                $document.off('mousemove', mousemove);
                $document.off('mouseup', mouseup);
            }
        }
    }
}]);
App.directive('szFocus', [function () {
    return {
        link: function (scope, element, attrs) {
            if (scope.$eval(attrs.szFocus)) {
                element[0].focus();
            }
        }
    }
}]);

