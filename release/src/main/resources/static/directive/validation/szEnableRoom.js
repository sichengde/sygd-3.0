/**
 * Created by 舒展 on 2016/7/2 0002.
 * 检查房号是否可用
 */
App.controller('szEnableRoom',['$compile',function ($compile) {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ctrl) {
            /*带的是变量参数*/
            function check() {
                if (!scope.wrongCheck) {
                    scope.wrongCheck = [];
                }
                var order = attr.szNotNull;
                if (ctrl.$viewValue == '') {//是手动撤销
                    scope.wrongMsg = '不可为空';
                    scope.wrongCheck[order] = true;
                } else if (!ctrl.$dirty) {//null是初始化，不提示字符，但不能提交
                    scope.wrongCheck[order] = true;
                }
                else {
                    scope.wrongMsg = false;
                    scope.wrongCheck[order] = false;
                }
            }

            check();
            element.bind('input enterKey', function () {
                scope.$apply(check());
            });
            var el = $compile('<div class="submitTishi" ng-show="wrongMsg">{{wrongMsg}}</div>')(scope);
            element.after(el);
        }
    }
}]);