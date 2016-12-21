/**
 * Created by Administrator on 2016-06-20.
 * enterMethod会传递一个参数如果readParam存在的话
 */
App.directive('szReadKey', ['$document', '$interval', function ($document, $interval) {
    return {
        restrict: 'A',
        scope: {
            szReadKey: '=',
            enterMethod: '&',
            readParam: '=?'
        },
        link: function (scope, ele, attr, ctr) {
            var on = false;//是否已经绑定了，如果已经绑定了，再点击就不启用
            ele.on('click', click);
            var interval;
            var beforeInterval = '';
            var cannotRefresh;//不能再次读卡

            function keyDown(event) {
                if (event.key == 'Enter') {
                    $interval.cancel(interval);
                    $document.off('keydown', keyDown);
                    ele.on('click', click);
                }
                scope.$apply(function () {
                    if (event.key == 'Enter') {
                        if(scope.szReadKey==''){//啥也没输入，无意义，直接过滤掉
                            on = false;
                            return;
                        }
                        if (scope.szReadKey.length != 10) {
                            scope.szReadKey = beforeInterval + scope.szReadKey;
                        }
                        if (!scope.readParam) {
                            cannotRefresh=scope.enterMethod();
                        } else {
                            cannotRefresh=scope.enterMethod({r: scope.readParam});
                        }
                        on = false;
                        return;
                    }
                    scope.szReadKey = scope.szReadKey + event.key;
                })
            }

            function click() {
                if(cannotRefresh){
                    return;
                }
                if (on) {
                    ele.off('click');
                    return;
                }
                on = true;
                scope.szReadKey = '';
                $document.on('keydown', keyDown);
                interval = $interval(function () {
                    beforeInterval = scope.szReadKey;
                    scope.szReadKey = '';
                }, 1000)
            }
        }
    }
}]);