/**
 * Created by Administrator on 2016-08-03.
 */
App.factory('userService',[function () {
    /**
     * 通过传入的数组，把其中check为true的field字段值拼接成一个字符串
     */
    function getCheckedString(List,field) {
        var out='';
        for (var i = 0; i < List.length; i++) {
            var obj = List[i];
            if (obj.check){
                out+=obj[field]+' ';
            }
        }
        return out;
    }
    return{
        getCheckedString:getCheckedString
    }
}]);