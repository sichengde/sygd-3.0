/**
 * Created by 刘丹 on 2016-06-22.
 */
App.directive('szHoverRoom',['$interval' , function ($interval) {
    return{
        restrict:'A',
        link:function(scope,element){
            var hover = element.children(".hoverDiv");
            var roomStateTop = element.offset().top;
            var stop;
            element.bind('mouseenter',function () {
                var bodyWidth = document.body.offsetWidth;
                var roomStateLeft = element.offset().left;
                var right = bodyWidth-roomStateLeft;
                var topHeight = hover.height();
                var rightWidth = hover.width();
                stop=$interval(function(){
                        hover.fadeIn("fast");
                        hover.css({top:-topHeight-25+'px'});
                        if(topHeight>roomStateTop ){
                            hover.css({top:-roomStateTop+'px'});
                        }
                        if(rightWidth>right){
                            hover.css({left:'auto',right:'0px'});
                            hover.addClass("rigb");
                        }
                    
                },500);
            });
            element.bind('mouseleave',function () {
                hover.hide();
                $interval.cancel(stop);
            });
            element.on('$destroy', function() {
                $interval.cancel(stop);
            });
        }
    }
}]);