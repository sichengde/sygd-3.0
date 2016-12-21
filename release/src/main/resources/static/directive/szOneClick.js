/**
 * Created by Administrator on 2016/11/14 0014.
 */
App.directive('szOneClick',['$parse',function ($parse) {
    return {
        link: function(scope, element, iAttrs) {
            element.bind('click', function() {
                element.prop('disabled',true);
                $parse(iAttrs.szOneClick)(scope).finally(function() {
                    element.prop('disabled',false);
                })
            });
        }
    };
}]);