/**
 * 未启用
 */
App.directive('szRoomStateRepeat', ['$compile',function ($compile) {
    return {
        restrict: 'A',
        link: function (scope, element, attr, ctr) {
            scope.$watch('roomList',function (item) {
                element.empty();
                var roomList = scope.roomList;
                var appendStr='';
                for (var i = 0; i < roomList.length; i++) {
                    var room = roomList[i];
                    appendStr+='<li class="roomState" ng-class="r.hover" ng-dblclick="clickGuestIn(r)" ng-click="receptionClick(r)" sz-right-click="rightClickRoom" sz-right-click-room-state sz-hover-room ng-mousedown="chooseSource(r)" ng-mouseup="chooseTarget(r)" sz-state-icon></li>'
                }
                var el = $compile(appendStr)(scope);
                element.append(el);
            });
        }
    }
}]);