/**
 * Created by 舒展 on 2016-06-21.
 * 输入框带有下拉提示
 * szSelect 数组对象
 * szDown 向下还是向上
 * szField 数组是字符串数组则忽略，对象数组填写字段名称
 */
App.directive('szSelect', ['$compile', '$filter', 'util', '$timeout', function ($compile, $filter, util, $timeout) {
    return {
        require: 'ngModel',
        restrict: 'A',
        scope: {
            szSelect: '=?',
            szDown: '@',
            szField: '=?',
            notCheckSzSelect:'=?'
        },
        link: function ($scope, element, attr, ctrl) {
            var fdiv = element.parent();
            element.on('click', function () {
                $scope.$apply(function () {
                    $scope.showSelect = true;
                });
                fdiv.css({position: 'relative'})
            });
            element.on('input enterKey keypress', keypress);
            if(!$scope.notCheckSzSelect) {
                element.on('blur', checkInput);
            }
            function keypress(event) {
                $scope.$apply(function () {
                    $scope.showSelect = true;
                    $scope.filterItem = ctrl.$viewValue;
                    $scope.showSelect = ctrl.$viewValue != '';
                    if (event.key == 'Enter') {
                        var filteredItem = $filter('filter')($scope.szSelect, $scope.filterItem);
                        if (filteredItem[0]) {//如果只有一个备选条件，则选择
                            $scope.chooseItem(filteredItem[0]);
                        }
                        $scope.showSelect = false;
                    }
                })
            }

            function checkInput() {
                $scope.$apply(function () {
                    timer.push($timeout(
                        function () {
                            if ($scope.szField) {//传进来的是对象数组
                                if (util.objectListToString($scope.szSelect, $scope.szField).indexOf(ctrl.$viewValue) == -1) {
                                    cancel();
                                } else {
                                    $scope.showSelect = false;
                                }
                            } else {//传进来的是字符串数组
                                if ($scope.szSelect.indexOf(ctrl.$viewValue) == -1) {//自己乱输入的
                                    cancel();
                                } else {
                                    $scope.showSelect = false;
                                }
                            }
                        },
                        500
                    ));
                })
            }

            var timer = [];

            function cancel() {
                ctrl.$setViewValue('');
                element.attr('value', ctrl.$viewValue);//更新显示的数值
                $scope.filterItem = '';
                $scope.showSelect = false;//延时关闭，否则点击事件无法生效
            }

            $scope.chooseItem = function (r) {
                if ($scope.szField) {
                    ctrl.$setViewValue(r[$scope.szField]);//为了触发ngChange
                } else {
                    ctrl.$setViewValue(r);//为了触发ngChange
                }
                element.attr('value', ctrl.$viewValue);//更新显示的数值
                $scope.showSelect = false;
            };
            var a = element.css("width");
            var el;
            el = $compile('<ul ng-show="showSelect" class="{{szDown?\'selectUlDown\':\'selectUl\'}}" style="width:' + a + '"><li ng-repeat=" r in szSelect | filter:filterItem" ng-click="chooseItem(r)">{{szField?r[szField]:r}}</li></ul>')($scope);
            element.after(el);
        }
    }
}]);