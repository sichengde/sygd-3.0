/**
 * Created by 刘丹 on 2016-08-04.
 */
App.directive("szTopScroll", ['szTopScrollService', function (szTopScrollService) {
    return {
        restrict: 'A',
        link: function (scope, element) {
            var num = scope.moduleButtons.length;
            var scrollWidth = num * 87;
            var width = element.width();
            if (width > scrollWidth) {
                angular.element(".lefta").css("display", "none")
            }
            else {
                angular.element(".lefta").css("display", "block")
            }
            $(window).resize(function () {
                var winWidth = document.body.clientWidth;
                var menuWidth = winWidth - 630;
                if (menuWidth > scrollWidth) {
                    angular.element(".lefta").css("display", "none");
                    var step=szTopScrollService.getStep();
                    var var1 = Math.abs(step) % num;
                    for (var i = 0; i < var1; i++) {
                        if (step > 0) {
                            var lq = angular.element(".menubox button:last");
                            lq.insertBefore(".menubox a:first")
                        } else if (step < 0) {
                            var fq = angular.element(".menubox button:first");
                            fq.insertAfter(".menubox button:last")
                        }
                    }
                }
                else {
                    angular.element(".lefta").css("display", "block")
                }
            })

        }
    }
}]);

App.directive("szTopScrollLeft", ['szTopScrollService', function (szTopScrollService) {
    return {
        restrict: 'A',
        link: function (scope, element) {
            element.bind("mousedown", function () {
                szTopScrollService.stepPlus();
                var fq = angular.element(".menubox button:first");
                fq.insertAfter(".menubox button:last")
            })
        }
    }
}]);
App.directive("szTopScrollRight", ['szTopScrollService', function (szTopScrollService) {
    return {
        restrict: 'A',
        link: function (scope, element) {
            element.bind("mousedown", function () {
                szTopScrollService.stepMinus();
                var lq = angular.element(".menubox button:last");
                lq.insertBefore(".menubox button:first")
            })
        }
    }
}]);
App.factory('szTopScrollService',[function () {
    var step=0;
    function stepPlus() {
        step++;
    }
    function stepMinus() {
        step--;
    }
    function getStep() {
        var var1=step;
        step=0;
        return var1;
    }
    return{
        stepPlus:stepPlus,
        stepMinus:stepMinus,
        getStep:getStep
    }
}]);