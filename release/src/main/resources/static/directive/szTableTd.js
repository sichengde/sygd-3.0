/**
 * Created by 刘丹 on 2016-08-02.
 */
App.directive('szTableTd',[function () {
    return{
        restrict: 'A',
        link:function (scope,element) {
            var data=scope.$eval('item[r.id]');
            var tagP = element.children("p");
            var table = element.parent().parent().parent();
            if(!data){
                return
            }
            element.bind('mouseover',function () {
                var pLeft = tagP.offset().left;
                var tableLeft = table.offset().left;
                var tdWidth = pLeft - tableLeft;
                var spanWidth = element.width()+10;
                var spanWidth2 = element.width()+80;
                var length = tagP.width();
                var length2 = tagP.width()/2-30;
                if(length>spanWidth && length<spanWidth2){
                    /*element.css({overflow:'visible'});*/
                    tagP.css({left:'50%',marginLeft:-length/2+'px',background:'#dfe9cb',zIndex:'888',padding:'0px 10px'});
                }else if(length>spanWidth2){
                    /*element.css({overflow:'visible'});*/
                    tagP.css({left:'50%',width:spanWidth2+'px',whiteSpace:'normal',height:'auto',marginLeft:-spanWidth2/2+'px',background:'#dfe9cb',zIndex:'888',padding:'0px 10px'});
                }
            });
            element.bind('mouseout',function () {
                /*element.css({overflow:'hidden'});*/
                tagP.css({left:'0px',marginLeft:'0px',width:'auto',whiteSpace:'nowrap',background:'none',zIndex:'777',padding:'0px'});
            })

        }
    }
}]);