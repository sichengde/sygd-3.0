/**
 * Created by Administrator on 2016-12-01.
 * 自动模糊，仿制回车，空格等快捷键直接确认
 */
App.directive('szAutoBlur',[function () {
    return{
        restrict:'A',
        link:function(scope,element){
            element.bind('mouseup',function () {
                element.blur()
            });
        }
    }
}]);