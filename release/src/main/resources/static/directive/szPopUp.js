/**
 * Created by 舒展 on 2016-05-29.
 * 弹窗控件，通过传入的参数决定具体打开哪个弹窗
 * 参数
 * refresh：关闭之后可以调用的外部方法，
 * noShow:禁止背景显示，优化效率
 * 背景：
 * undefined:自动添加半透明黑色背景，不可背景关闭弹窗  guestOut.html
 * top:弹窗上的弹窗背景，黑色半透明，不可背景关闭弹窗  roomShopIn.html
 * white:全透明背景，可背景关闭弹窗 rightClickRoom.html
 * white2:弹窗上的弹窗背景，全透明，可背景关闭弹窗 szPopChoose.html
 * static:全透明背景，不可背景关闭弹窗 message.html
 * none:没有背景 deskDetail.html
 */
App.directive('szPopUp', ['$compile','$interval', function ($compile,$interval) {
    return {
        restrict: 'E',
        scope: {
            refresh:'&',
            noShow:'=?'
        },
        controller: ['$scope', 'popUpService',function ($scope, popUpService) {
            $scope.pop = false;
            this.init = function (name, ele) {
                popUpService.init(name, $scope, ele);
            };
            $scope.closePop = function (name) {
                $scope.noShow=false;
                popUpService.close(name,true);//点击背景关闭，就不刷新了
            };
        }],
        templateUrl: function (element, attr) {
            return attr.name;
        },
        link: function (scope, element, attr, ctr) {
            var url = attr.name;
            var name = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.html'));
            var el;
            if (attr.back == undefined) {
                el = $compile('<div class="bgblack" ng-show="pop"></div>')(scope);
                element.append(el);
            }else if (attr.back == 'top') {
                el = $compile('<div class="bgblack2" ng-show="pop"></div>')(scope);
                element.after(el);
            }
            else if (attr.back == 'white') {
                el = $compile('<div class="szPopUpBg" ng-click="closePop(\'' + name + '\')" ng-show="pop"></div>')(scope);
                element.after(el);
            }else if (attr.back == 'white2') {
                el = $compile('<div class="szPopUpBg2" ng-click="closePop(\'' + name + '\')" ng-show="pop"></div>')(scope);
                element.after(el);
            }else if(attr.back=='static'){
                el = $compile('<div class="szPopUpBg" ng-show="pop"></div>')(scope);
                element.after(el);
            }else if(attr.back=='none'){
                element.after();
            }
            ctr.init(name, element);

        }
    }
}]);