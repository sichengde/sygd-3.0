/**
 * Created by Administrator on 2016-06-21.
 */
App.filter("roomStateEn", [function () {
    return function (cn) {
        if (cn == '可用房') {
            return 'V';
        }
        if (cn == '团队房') {
            return 'G';
        }
        if (cn == '散客房') {
            return 'S';
        }
        if (cn == '走客房') {
            return 'L';
        }
        if (cn == '维修房') {
            return 'X';
        }
        if (cn == '自用房') {
            return 'M';
        }
        if (cn == '备用房') {
            return 'B';
        }
    }
}]);