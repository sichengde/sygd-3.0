/**
 * Created by Administrator on 2016-04-29.
 * 房间过滤器，根据输入的房间选出符合房类和房间状态的房间
 * rooms：传进来的全部房间数组
 * state：房间状态
 * category：房间类别
 * groupAccount：公付帐号，针对于联房筛选(=0:没有公付帐号的房间，也就是散客房)
 */
App.filter("room", [function () {
    return function (rooms, state, category, groupAccount, dirty, area, floor) {
        var out = [];
        if (rooms == null) {
            return;
        }
        if(!area){
            area='全部';
        }
        if(!floor){
            floor='全部';
        }
        var l = rooms.length;
        for (var i = 0; i < l; i++) {
            var bool1=bool2=bool3=bool4=false;//分别代表房态房类楼区楼层的筛选判断，数组用indexOf判断，字符串用==判断
            if (Array.isArray(state)) {
                if ((state.indexOf(rooms[i].state) > -1) || (state == '全部')) {
                    bool1 = true;
                }
            } else {
                if (state == rooms[i].state || (state == '全部')) {
                    bool1 = true;
                }
            }
            if (Array.isArray(category)) {
                if ((category.indexOf(rooms[i].category) > -1) || (category == '全部')) {
                    bool2 = true;
                }
            } else {
                if (category == rooms[i].category || (category == '全部')) {
                    bool2 = true;
                }
            }
            if (Array.isArray(area)) {
                if ((area.indexOf(rooms[i].area) > -1) || (area == '全部')) {
                    bool3 = true;
                }
            } else {
                if (area == rooms[i].area || (area == '全部')) {
                    bool3 = true;
                }
            }
            if (Array.isArray(floor)) {
                if ((floor.indexOf(rooms[i].floor) > -1) || (floor == '全部')) {
                    bool4 = true;
                }
            } else {
                if (floor == rooms[i].floor || (floor == '全部')) {
                    bool4 = true;
                }
            }
            if(bool1&&bool2&&bool3&&bool4){
                out.push(rooms[i]);
            }
        }
        var outGroup = [];
        if (groupAccount == null) {
            outGroup = out;
        }
        else if (groupAccount == 0) {
            for (var i = 0; i < out.length; i++) {
                var obj1 = out[i];
                if (obj1.checkIn) {
                    if (obj1.checkIn.groupAccount == null) {
                        outGroup.push(obj1);
                    }
                }
            }
        } else {
            for (var i = 0; i < out.length; i++) {
                var obj2 = out[i];
                if (obj2.checkIn) {
                    if (obj2.checkIn.groupAccount == groupAccount) {
                        outGroup.push(obj2);
                    }
                }
            }
        }
        var out2 = [];
        if (dirty) {//查找脏房和走客房
            for (i = 0; i < outGroup.length; i++) {
                var obj = outGroup[i];
                if (obj.dirty == dirty) {
                    out2.push(obj);
                } else if (obj.state == '走客房') {
                    out2.push(obj);
                }
            }
        }else {
            out2=outGroup;
        }
        return out2;
    }
}]);