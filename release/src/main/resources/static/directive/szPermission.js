/**
 * Created by Administrator on 2016-05-24.
 */
App.directive('szPermission',['LoginService',function (LoginService) {
    return{
        restrict: 'A',
        link:function ($scope, element, attr) {
            function checkPermission(action) {
                return LoginService.getOwnPermissionList().indexOf(action) > -1;
            }
            /*权限没通过，则*/
            if (!checkPermission(attr.szPermission)){
                /*下边是例子*/
                element.bind('mouseenter',function (event) {
                    element.css({disable:true});
                    element.attr('disabled','disabled');
                    /*加一个红字提示*/
                    element.addClass("userTishi");
                    /*........*/
                });
                element.bind('mouseout',function () {
                    element.removeClass("userTishi");
                })
            }
        }
    }
}]);