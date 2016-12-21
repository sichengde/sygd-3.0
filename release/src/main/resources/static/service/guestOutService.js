/**
 * Created by Administrator on 2016-08-18.
 */
App.factory('guestOutService', ['dataService','util', function (dataService,util) {
    /**
     * 初始化数据，分为团队结算的数据和单人结算的数据
     * 传入的参数为房间对象，如果含有groupAccount则为团队房，否则是散客房
     */
    function initData(room) {
        var p2 = {orderByListDesc: ['timeLimit']};
        var p;
        if (room.checkInGroup) {
            /*是会员开房就要刷新会员数据*/
            var refreshList=['refreshDebtList', 'refreshCheckInList', 'refreshCheckInGroupList', 'refreshRoomPriceAddList',  'refreshTimeNow',  'refreshProtocolList'];
            p = {
                condition: 'group_account=' + util.wrapWithBrackets(room.checkInGroup.groupAccount),
                orderByListDesc:['deposit']
            };
            return dataService.initData(refreshList, [p, p, p, p2])
        } else {
            var refreshList=['refreshDebtList', 'refreshCheckInList', 'refreshCheckInGuestList', 'refreshRoomPriceAddList', 'refreshTimeNow',  'refreshProtocolList'];
            p = {
                condition: 'room_id=' + util.wrapWithBrackets(room.roomId),
                orderByListDesc:['deposit']
            };
            return dataService.initData(refreshList, [p, p, p, p2])
        }
    }

    /**
     * 在录入房吧，退押金，录入杂单之后重新刷新账务数据，注意只有账务！！！
     */
    function refreshDebtData(room) {
        var p;
        if (room.checkInGroup) {
            p = {condition: 'group_account=' + util.wrapWithBrackets(room.checkInGroup.groupAccount)};
            dataService.initData(['refreshCheckInGroupList', 'refreshDebtList', 'refreshTimeNow'], [p, p])
        }else {
            p = {condition: 'room_id=' + util.wrapWithBrackets(room.roomId)};
            dataService.initData(['refreshDebtList', 'refreshCheckInList', 'refreshTimeNow'], [p, p])
        }
    }
    return{
        initData:initData
    }
}]);