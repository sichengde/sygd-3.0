/**
 * Created by 舒展 on 2016-06-01.
 * 非空判断
 */
App.directive('szNotNull', ['$compile', 'validateService', function ($compile, validateService) {
    return {
        require: ['?ngModel', '?szTable'],
        link: function (scope, element, attr, ctrl) {
            var order;
            var showWrong;
            /*带的是变量参数*/
            function check() {
                if (order == undefined) {
                    order = validateService.getOrder();
                }
                showWrong = 'showWrong' + order;

                if (ctrl[0]) {
                    if (ctrl[0].$modelValue == '') {//是手动撤销
                        scope[showWrong] = true;
                        validateService.update(order, true);
                    }
                    else {
                        scope[showWrong] = false;
                        validateService.update(order, false);
                    }
                } else if (ctrl[1]) {
                    if (ctrl[1].getLength() == 0) {
                        scope[showWrong] = true;
                        validateService.update(order, true);
                    }
                    else {
                        scope[showWrong] = false;
                        validateService.update(order, false);
                    }
                }
                scope.order = order;
            }

            check();
            var el;
            if (ctrl[0]) {
                ctrl[0].$render = function () {
                    element.attr('value', ctrl[0].$viewValue);
                    if (ctrl[0].$viewValue == '' || ctrl[0].$viewValue == undefined) {
                        validateService.update(order, true);//考虑到可能在submit之后加载，所以也更新一下
                    }
                };
                element.bind('input enterKey change', function () {
                    scope.$apply(check());
                });
                el = $compile('<div class="submitTishi" ng-show="showWrong{{order}}">不可为空</div>')(scope);
            } else if (ctrl[1]) {
                ctrl[1].initCheck(check);
                el = $compile('<div class="submitTishi" ng-show="showWrong{{order}}">不可为空</div>')(scope);
            }
            element.after(el);
        }
    }
}]);