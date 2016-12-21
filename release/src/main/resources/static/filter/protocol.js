/**
 * Created by 舒展 on 2016-06-21.
 * 协议价过滤器，根据当前选择的房类和房租类型显示相应的协议价格（注意：显示的是字符串数组不是对象数组）
 *  roomPriceCategory：房租类型
 *  category：房间类别
 *  company:单位
 *  vip:会员对象
 */
App.filter("protocol", ['dataService', function (dataService) {
    return function (protocols, roomPriceCategory, category, company, vip) {
        if (!protocols) {
            return;
        }
        /*过滤掉临时协议*/
        var withOutTemp = [];
        if (dataService.getOtherParamMapValue("可编辑房价") == 'y') {
            var l = protocols.length;
            for (var i = 0; i < l; i++) {
                var obj = protocols[i];
                if (!obj.temp) {
                    withOutTemp.push(protocols[i]);
                }
            }
        } else {
            withOutTemp = protocols;
        }
        var out = [];
        var l = withOutTemp.length;
        for (var i = 0; i < l; i++) {
            if (roomPriceCategory == withOutTemp[i].roomPriceCategory && category == withOutTemp[i].roomCategory) {
                out.push(withOutTemp[i]);
            }
        }
        /*如果没有单位和会员筛选，直接返回没有特殊协议的字段*/
        if (!company && !vip) {
            var noSpecial = [];
            for (i = 0; i < out.length; i++) {
                var obj = out[i];
                if (obj.special != 'y') {
                    noSpecial.push(obj);
                }
            }
            return noSpecial;
        }
        /*筛选一波单位*/
        var out2 = [];
        if (company) {
            for (i = 0; i < out.length; i++) {
                if (out[i].protocol == company.protocol) {
                    out2.push(out[i]);
                }
            }
        }
        else {
            out2 = out;
        }
        /*筛选一波会员*/
        var out3 = [];
        if (vip) {
            for (i = 0; i < out2.length; i++) {
                if (out2[i].protocol == vip.protocol) {
                    out3.push(out2[i]);
                }
            }
            if(out3.length==0){//会员没有一个可用的房价，重新赋值为之前的
                out3=out2;
            }
        } else {
            out3 = out2;
        }
        return out3;
    }
}]);
