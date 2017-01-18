/**
 * Created by Administrator on 2016-08-22.
 */
App.directive('szAutoClick', ['$interval','dataService','$parse', function ($interval,dataService,$parse) {
    return {
        restrict: 'A',
        link: function (scope, element,attr,ctrl) {
            var fn = $parse(attr['ngClick']);
            if(!fn){
                fn=$parse(attr['szOneClick']);
            }
            var stop;
            dataService.refreshOtherParamList()
                .then(function () {
                    stop = $interval(function () {
                        fn(scope);
                    }, dataService.getOtherParamMapValue('自动刷新间隔'));
                });
            element.on('$destroy', function () {
                $interval.cancel(stop);
            });
        }
    }
}]);
