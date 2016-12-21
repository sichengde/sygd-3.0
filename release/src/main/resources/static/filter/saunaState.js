App.filter('saunaState', [function () {
    return function (saunaRingList, sex, state) {
        if (saunaRingList == null) {
            return;
        }
        var out = [];
        for (var i = 0; i < saunaRingList.length; i++) {
            var saunaRing = saunaRingList[i];
            var bool1 = bool2 = false;//分别代表性别状态的筛选判断，数组用indexOf判断，字符串用==判断
            if (sex == saunaRing.sex || (sex == '全部')) {
                bool1 = true;
            }
            if (Array.isArray(state)) {
                if ((state.indexOf(saunaRing.state) > -1) || (state == '全部')) {
                    bool2 = true;
                }
            } else {
                if (state == saunaRing.state || (state == '全部')) {
                    bool2 = true;
                }
            }
            if (bool1 && bool2) {
                out.push(saunaRing);
            }
        }
        return out;
    }
}]);