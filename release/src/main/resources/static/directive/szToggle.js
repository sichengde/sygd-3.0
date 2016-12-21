/**
 * Created by 刘丹 on 2016-07-01.
 */
App.directive('szToggle',[function () {
    return{
        restrict:'A',
        link:function (scope,element) {
            var lp = element.children(".lpul");
            var lpbutton = element.children(".lpan");
            element.bind("mousedown",function () {
                lp.removeClass('contentToggle');
            });
            lpbutton.bind("mousedown",function () {
                lp.addClass('contentToggle');
            })

        }
    }
}]);