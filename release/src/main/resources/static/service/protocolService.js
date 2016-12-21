/**
 * Created by Administrator on 2016/6/19 0019.
 */
App.factory('protocolService', ['dataService', function (dataService) {
    /**
     * 获得一条房价协议
     * @param protocol 协议名称
     * @param category 房类
     * @param roomPriceCategory 房租方式
     * @returns {*}
     */
    function getProtocolObj(protocol, category, roomPriceCategory) {
        var protocolList = dataService.getProtocolList();
        for (var i = 0; i < protocolList.length; i++) {
            var obj = protocolList[i];
            if (obj.protocol == protocol && obj.roomCategory == category && obj.roomPriceCategory == roomPriceCategory) {
                return obj;
            }
        }
    }

    return {
        getProtocolObj: getProtocolObj
    }
}]);