/**
 * Created by 舒展 on 2016/6/18 0018.
 * 简单的表格处理，不涉及数据库，不涉及增删改查
 * fields中参数
 * name:每列的标题
 * id:对应数据库中的字段名
 * copy：新增之后该字段值是否默认保留，例如添加房号的时候，房类默认保留
 * selectId:是否启用下拉列表
 * width：宽度
 * static:字段内容无论如何都不可编辑
 * filter:针对下拉列表，是否需要过滤，如果需要则填上相关的field的id（例子：房吧设计）
 * default:默认值，新增的时候会直接出现
 * date:采用日历控件，如果其中值为short，则不显示时间
 * length:字段长度必须等于他
 * ---------------------------------------------------------------
 * scope中参数
 * alwaysAdd:持续添加（点完保存后可以继续添加）
 * state:初始化状态，有些空表，例如散客开房的宾客，初始化就应该是新增状态add
 */
App.directive('szTableEasy',['util','dateFilter',function (util,dateFilter) {
    return{
        restrict:'E',
        scope:{
            items:'=',
            fields: '=',
            selectList: '=',
            button: '@',
            filter: '@',
            prefix:'@',
            alwaysAdd:'@',
            state:'@'
        },
        controller:['$scope','messageService','popUpService',function ($scope,messageService,popUpService) {
            var checkFn;
            var wrongList = {};//保存所有不满足输入条件的出错的字段，必须全部验证通过方可提交;
            if (!$scope.button) {
                $scope.button = [];
            }
            if (!$scope.filter) {
                $scope.filter = 'allPass';
            }
            $scope.addItem = {};//新增时需要提交的对象
            $scope.state='none';
            var deleteList=[];
            /*看看有没有需要格式化日期的字段*/
            angular.forEach($scope.items,function (value) {
                angular.forEach(value,function (field, key) {
                    if(key.indexOf('Time')>-1||key.indexOf('time')>-1){
                        value[key]=dateFilter(value[key],'yyyy-MM-dd HH:mm:ss');
                    }
                })
            });
            $scope.deleteBegin = function () {
                $scope.state = 'delete';
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
                        $scope.addItem[obj.id]=obj.default;
                    }
                    /*检验有没有非空字段*/
                    if (obj.notNull && ($scope.addItem[obj.id] == '' || !$scope.addItem[obj.id])) {
                        $scope.wrongMsg = '不可为空';
                        wrongList[obj.$$hashKey] = false;
                    }
                }
            };
            $scope.deleteChoose = function (item) {
                if (item.ifDelete) {
                    deleteList.push(item);
                } else {
                    util.deleteFromArray(deleteList, null, item.id);
                }
            };
            $scope.deleteAll = function () {
                var i;
                var obj;
                if (deleteList.length > 0) {
                    deleteList.length = 0;
                    for (i = 0; i < $scope.items.length; i++) {
                        obj = $scope.items[i];
                        obj.ifDelete = false;
                    }
                } else {
                    for (i = 0; i < $scope.items.length; i++) {
                        obj = $scope.items[i];
                        deleteList.push(obj);
                        obj.ifDelete = true;
                    }
                }
            };
            $scope.cancel = function () {
                $scope.state = 'none';
                $scope.addItem = {};
                deleteList = [];
            };
            $scope.add = function () {
                /*输入验证*/
                for (var i = 0; i < $scope.fields.length; i++) {
                    var obj = $scope.fields[i];
                    if(obj.length && $scope.addItem[obj.id].length!=obj.length){
                        messageService.setMessage({type:'error',content:obj.name+'长度必须为'+obj.length});
                        popUpService.pop('message');
                        return;
                    }
                }
                $scope.items.push(angular.copy($scope.addItem));
                if(!$scope.alwaysAdd){
                    $scope.state='none';
                }
                $scope.addItem=null;
            };
            $scope.addChoose = function (field) {
                var var1 = field.$$hashKey;
                if (field.notNull && $scope.addItem[field.id] == '') {
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
            };
            $scope.delete = function () {
                for (var i = 0; i < deleteList.length; i++) {
                    util.deleteFromArray($scope.items,'$$hashKey',deleteList[i].$$hashKey)
                }
                $scope.state='none';
            };
            $scope.$watch('items.length',function () {
                if(checkFn) {
                    checkFn();
                }
            });
            /**
             * 控制器方法
             */
            this.getLength = function () {
                return $scope.items.length;
            };
            this.initCheck=function (fn) {
                checkFn=fn;
            }
        }],
        templateUrl:function () {
            return '../directive/szTableEasy.html';
        }
    }
}]);

App.directive("szEasyWidth",function () {
    return{
        restrict:'A',
        link:function (scope,element) {
                var w = element.width();
                var w2 = w+17;
                angular.element(".tableEasyDiv").css({width:w2+'px'});
                angular.element(".easy").css({width:w+'px'});



        }
    }
});