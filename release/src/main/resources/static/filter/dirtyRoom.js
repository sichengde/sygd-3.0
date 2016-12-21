/**
 * Created by Administrator on 2016-06-28.
 */
App.filter('dirtyRoom',[function () {
    return function (dirty) {
        if (dirty==1){
            return '脏房'
        }else {
            return '净房'
        }
    }
}]);