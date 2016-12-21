/**
 * Created by Administrator on 2016/7/2 0002.
 */
App.directive('szHotKeyEnter', ['$document', '$parse', function ($document, $parse) {
    return {
        restrict: 'A',
        require:'^szHotKeyArea',
        compile: function(element, attributes) {
            return {
                pre: function preLink(scope, element, attributes) {
                },
                post: function postLink(scope, element, attr,ctrl) {
                    var fn = $parse(attr['ngClick'], /* interceptorFn */ null, /* expensiveChecks */ true);
                    var area=ctrl.getElement();
                    area.on('keydown', function (event) {
                        if (event.key == 'Enter') {
                            if (scope.$eval(attr.szHotKeyEnter) != false) {
                                scope.$apply(fn(scope, {$event: event}));
                            }
                        }
                    });
                    scope.$on('$destroy', function() {
                        area.off('keydown');
                    });
                }
            };
        }
    }
}]);