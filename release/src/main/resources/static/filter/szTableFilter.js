/**
 * Created by Administrator on 2016-07-29.
 * 凡是带有时间的，过滤
 */
App.filter('szTableFilter', ['dateFilter', function (dateFilter) {
    return function (item, id, format) {
        if (id) {
            if (id.indexOf('Time') > -1 || id.indexOf('time') > -1) {//判断时间
                if (format == 'short') {
                    return dateFilter(item, 'yyyy-MM-dd');
                } else {
                    return dateFilter(item, 'yyyy-MM-dd HH:mm:ss');
                }
            } else if (item === false) {
                return '否';
            } else if (item === true) {
                return '是';
            } else {
                return item
            }
        } else {
            return item;
        }
    }
}]);
/*
App.filter('szTrueFalse', [function () {
    return function (list) {
        var out = [];
        for (var i = 0; i < list.length; i++) {
            var obj = list[i];
            if (obj === true) {
                out.push("是");
            } else if (obj === false) {
                out.push("否");
            }else {
                out.push(out);
            }
        }
        return out;
    }
}]);*/
