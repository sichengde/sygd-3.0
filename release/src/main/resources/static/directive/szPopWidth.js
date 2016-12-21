/**
 * Created by Administrator on 2016-09-22.
 */
App.directive('szPopWidth', [function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            var pLength = scope.message.content.length;
            if (pLength > 100) {
                element.css({width:'40%',left:'28%',right:'28%'});
                }
            if (pLength > 450){
                element.css({width:'40%',left:'28%',right:'28%',top:'20%',bottom:'20%'});
            }
        }
    }
}]);