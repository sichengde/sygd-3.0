/**
 * Created by 舒展 on 2016/7/2 0002.
 * 因为szNotNull加载往往在szSubmit之前，所以初始化的判断无法通过update来进行，因此增加了set/readMessage方法来保存szNotNull加载的数据给szSubmit初始化用，用完即删除
 * szNotNull先加载的时候主要要考虑到初始化判断的问题
 */
App.factory('validateService', ['$location', function ($location) {
    var messageList = [];
    var wrongCheckList = [];
    var fun;
    var order;
    var path;

    function init(check) {
        fun = check;
        wrongCheckList = [];
        order = 0;
    }


    function update(i, bool) {
        wrongCheckList[i] = bool;
        if (fun) {
            fun(wrongCheckList);
        }
    }

    function getOrder() {
        if (!order) {
            order = 0;
            return order++;
        } else {
            return ++order;
        }
    }

    return {
        update: update,
        init: init,
        getOrder: getOrder
    }
}]);