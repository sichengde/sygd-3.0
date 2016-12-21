/**
 * Created by Administrator on 2016-04-25.
 * scope----
 * rightClickParam:加一个参数
 */
App.directive('szRightClick', ['$window', function ($window) {
    return {
        restrict: 'A',
        scope: {
            rightClickParam:'='
        },
        controller: ['$scope','popUpService', function ($scope,popUpService) {
            function mouseCoords(ev) {
                if (ev.pageX || ev.pageY) {
                    return {x: ev.pageX, y: ev.pageY};
                }
                return {
                    x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
                    y: ev.clientY + document.body.scrollTop - document.body.clientTop
                };
            }

            this.rightClick = function (event,popName) {
                if(popName==''||!popName){
                    return;
                }
                event.preventDefault();
                var mousePos = mouseCoords(event);
                popUpService.pop(popName, {
                    left: mousePos.x,
                    top: mousePos.y,
                    display: 'block',
                    position:'absolute',
                    zIndex:'999'
                },null,$scope.rightClickParam);
            };
        }],
        link: function ($scope, element, attr, controller) {
            if(!attr.szRightClick){
                return
            }
            element.bind('contextmenu', function (event) {
                event.preventDefault();
                $scope.$apply(function () {
                    controller.rightClick(event,attr.szRightClick);
                });
                var bodyWidth = document.body.offsetWidth;
                var rdiv = angular.element(".rightClickBox");
                var rleft = rdiv.offset().left;
                var rright = bodyWidth-rleft;
                var num1 = rdiv.find("li").length;
                var num2 = rdiv.find("li.ng-hide").length;
                var num = num1-num2;
                if(num>5){
                    rdiv.css("width","300px");
                    if(rright<300){
                        rdiv.css("margin-left","-300px");
                    }
                }
                else
                {return};
            });
        }
    };
}]);