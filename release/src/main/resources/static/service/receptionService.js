/**
 * Created by 舒展 on 2016-05-28.
 * 接待总控制器
 */
App.service('receptionService',['dataService',function (dataService) {
    var chooseRoom;
    var inRoomList=['散客房','团队房'];
    function getChooseRoom() {
        return chooseRoom;
    }
    function setChooseRoom(room) {
        return chooseRoom=room;
    }

    /**
     * 根据选择的点亮按钮
     * @param roomList 房间列表
     * @param chooseList 选择的
     */
    function hoverChooseRoom(roomList, chooseList) {
        for (var i = 0; i < roomList.length; i++) {
            var obj1 = roomList[i];
            for (var j = 0; j < chooseList.length; j++) {
                var obj2 = chooseList[j];
                if(obj1.roomId==obj2.roomId){
                    obj1.hover='hover';
                    break;
                }
            }
        }
    }
    return{
        setChooseRoom:setChooseRoom,
        getChooseRoom:getChooseRoom,
        hoverChooseRoom:hoverChooseRoom,
        inRoomList:inRoomList
    }
}]);