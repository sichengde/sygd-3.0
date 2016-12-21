/**
 * Created by Administrator on 2016-08-24.
 */
App.directive('szHoverDisplay', [function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            element.bind("mouseenter", function () {
                element.next(".fdjy").fadeIn("fast");
            });
            element.bind("mouseleave", function () {
                element.next(".fdjy").fadeOut("fast");
            })
        }
    }
}]);
App.directive('szRightBox', [function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            var num = element.find("li").length;
            if (num > 5) {
                element.css("width", "300px");
            }
        }
    }
}]);

App.directive('szWidth', [function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            var cdtag = angular.element(".cdBox");
            var cButton = angular.element(".cpButton span");
            var cytag = angular.element(".cyStateContent");
            element.bind('mousedown',function(){
                cdtag.toggleClass("cdBoxWidth");
                cButton.toggleClass("jspan");
                cytag.toggleClass("cySCwidth");

            })
        }
    }
}]);
App.directive('szHeight', [function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            var ctBox = angular.element(".chartBox");
            element.bind('mousedown',function(){
                ctBox.toggleClass("chartBoxTop");
            })
        }
    }
}]);