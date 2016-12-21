/**
 * Created by 舒展 on 2016-06-21.
 * 可预定房间过滤，获取到可预定的房间
 * roomList:需要过滤的房间列表
 * category:选择的房类
 * bookList:未来的预订信息（避免重复预定）
 * reachTime:预计抵达时间
 * leaveTime:预计离开时间
 * abortTime:忽略时间
 * field:返回属性
 */
App.filter('bookRoom', ['util', '$parse', function (util, $parse) {
    return function (roomList, category, bookList, reachTime, leaveTime, abortTime, field) {
        var out1 = [];
        var out2 = [];
        var out3 = [];
        if (roomList == null) {
            return;
        }
        if (!reachTime && !leaveTime) {
            return roomList;
        }
        /*先过滤房类*/
        var l = roomList.length;
        for (var i = 0; i < l; i++) {
            if (category==roomList[i].category|| category == '全部') {
                out1.push(roomList[i]);
            }
        }
        /*忽略时间*/
        if (abortTime) {
            return out1;
        }
        /*再过滤订单*/
        l = out1.length;
        for (i = 0; i < l; i++) {
            /*先判断哪个订单包含该房间*/
            for (var j = 0; j < bookList.length; j++) {
                /*var1不为空则表示有该房间*/
                var var1 = util.getValueByField(bookList[j].bookRoomList, 'roomId', out1[i].roomId);
                if (var1) {
                    /*有交集*/
                    if (bookList[j].reachTime < leaveTime && bookList[j].leaveTime > reachTime) {
                        break;
                    }
                }
            }
            if (j == bookList.length) {
                out2.push(out1[i]);
            }
        }
        /*再考虑当前房态*/
        l = out2.length;
        for (i = 0; i < l; i++) {
            /*判断在店房*/
            if (out2[i].checkIn) {
                if (out2[i].checkIn.leaveTime > reachTime) {
                    continue;
                }
            }
            /*判断维修房*/
            if(out2[i].state=='维修房'){
                if (out2[i].repairTime > reachTime) {
                    continue;
                }
            }
            out3.push(out2[i]);
        }
        if (!field) {
            return out3;
        }
        else {
            if (field == 'length') {//如果是返回长度，也就是可预定房数
                var totalRoom=0;//计算不定房数预定的房间
                /*计算该房类该日期已预订房数*/
                for (j = 0; j < bookList.length; j++) {
                    /*有不定房间的预定*/
                    if(bookList[j].bookRoomCategoryList.length>0) {
                        /*有交集*/
                        if (bookList[j].reachTime < leaveTime && bookList[j].leaveTime > reachTime) {
                            for (i = 0; i < bookList[j].bookRoomCategoryList.length; i++) {
                                var obj = bookList[j].bookRoomCategoryList[i];
                                if(obj.roomCategory==category){
                                    totalRoom=totalRoom+obj.num*1;
                                    break;
                                }
                            }
                        }
                    }
                }
                return out3[field] - totalRoom;
            }
        }
    }
}]);
