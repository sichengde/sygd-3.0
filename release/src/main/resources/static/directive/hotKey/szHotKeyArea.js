/**
 * Created by Administrator on 2016-07-06.
 */
App.directive('szHotKeyArea',[function () {
    return{
        restrict:'A',
        controller:['$scope',function ($scope) {
            var element;
            this.init=function (ele) {
                element=ele;
            }  ;
            this.getElement=function () {
                return element;
            };
        }],
        compile: function() {
            return {
                pre: function preLink(scope, element, attributes,ctrl) {
                    ctrl.init(element);
                },
                post: function postLink(scope, element, attributes) {
                }
            };
        }
    }
}]);