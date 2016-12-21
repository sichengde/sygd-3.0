/**
 * Created by 舒展 on 2016-08-22.
 * null变为字符'空'
 */
App.filter('nullToString',[function () {
    return function (list) {
        var out=[];
        for (var i = 0; i < list.length; i++) {
            var obj = list[i];
            if(!obj){
                obj='--空--';
            }
            out.push(obj);
        }
        return out;
    }
}]);