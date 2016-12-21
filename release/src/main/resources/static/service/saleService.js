/**
 * Created by 舒展 on 2016/7/6 0006.
 * 售卖服务，主要适用于从一个商品列表中点商品到另一个列表里
 */
App.factory('saleService',['util',function (util) {
    /*选择商品*/
    function chooseItem(list,item,num) {
        if(!num){
            num=1;
        }
        var var1 = util.getValueByField(list, '$$hashKey', item.$$hashKey);
        if (!var1) {
            item.num = num;
            list.push(item);
        } else {
            var1.num=var1.num*1+num*1;
        }
    }
    /*计算金额*/
    function calculateMoney(list) {
        var money = 0;
        for (var i = 0; i < list.length; i++) {
            money = money + list[i].price * list[i].num;
        }
        return money;
    }
    /*计算描述*/
    function calculateDescription(list) {
        var description = '';
        var l = list.length;
        for (var i = 0; i < l; i++) {
            var item = list[i];
            description += item.item + ':' + item.num + '*' + item.price + '/';
        }
        return description;
    }
    return {
        chooseItem:chooseItem,
        calculateMoney:calculateMoney,
        calculateDescription:calculateDescription
    }
}]);