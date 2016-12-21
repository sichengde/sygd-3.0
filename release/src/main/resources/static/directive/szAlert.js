/**
 * Created by 刘丹 on 2016-07-01.
 */
App.directive('szAlert',[function () {
    return{
        restrict:'A',
        link:function(scope,element){
            element.bind('mousedown',function () {
                var tsBox=angular.element(document.getElementsByClassName('tswz'));
                var choose = angular.element(document.getElementsByClassName('chooseRoom'));
                tsBox.fadeOut("fast");
                choose.fadeIn("fast");
            });
        }
    }
}]);