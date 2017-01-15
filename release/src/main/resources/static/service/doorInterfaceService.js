/**
 * Created by Administrator on 2016-08-08.
 */
App.factory('doorInterfaceService', ['messageService', 'dateFilter', '$q', 'popUpService', '$http','util','dataService', function (messageService, dateFilter, $q, popUpService, $http,util,dataService) {

    /**
     * 新版本客户端发卡
     * @param roomIdList 房号list
     * @param leaveTime
     * @param numList 如果房号是逗号分隔，这个也要逗号分隔
     * @returns {*}
     */
    function doorWrite(roomIdList, leaveTime, numList) {
        var roomIdMap = util.listToMapByField(dataService.getInterfaceDoorList(), 'roomId');
        var doorIdList = [];
        for (var i = 0; i < roomIdList.length; i++) {
            var roomId = roomIdList[i];
            if (!roomIdMap[roomId]) {
                messageService.setMessage({type: 'error', content: '开房成功，但interfaceDoor表中没有定义房号，无法制卡'});
                popUpService.pop('message');
                return $q.reject();
            } else {
                doorIdList.push(roomIdMap[roomId].doorId);
            }
        }
        var num = numList.join(',');
        var ip = localStorage.getItem('ip');
        if (!ip) {
            messageService.setMessage({type: 'error', content: '没有获取到本机ip，制卡失败，请重新登陆，或者手动在localStorage中添加ip'});
            popUpService.pop('message');
            return $q.reject();
        }
        return $http.get('http://' + ip + ':8080/writeDoor?roomId=' + doorIdList.join(',') + '&leaveTimeStr=' + leaveTime + '&num=' + num)
    }

    function doorRead() {
        var ip = localStorage.getItem('ip');
        if (!ip) {
            messageService.setMessage({type: 'error', content: '没有获取到本机ip，制卡失败，请重新登陆，或者手动在localStorage中添加ip'});
            popUpService.pop('message');
            return $q.reject();
        }
        $http.get('http://' + ip + ':8080/readDoor');
    }

    function doorClear(roomId) {
        var x = document.getElementById("Control");
        x.roomId = roomId;
        if (x.cancel == 'true') {
            return $q(function (resolve, reject) {
                return resolve();
            });
        } else {
            messageService.setMessage({type: 'error', content: '房卡注销失败，房号不符'});
            popUpService.pop('message');
            return $q(function (resolve, reject) {
                return reject();
            });
        }
    }

    return {
        doorWrite: doorWrite,
        doorRead: doorRead,
        doorClear: doorClear
    }
}]);
