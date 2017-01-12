/**
 * Created by Administrator on 2016-08-08.
 */
App.factory('doorInterfaceService', ['messageService', 'dateFilter', '$q', 'popUpService','$http', function (messageService, dateFilter, $q, popUpService,$http) {

    /**
     * 新版本客户端发卡
     * @param roomId 可以是逗号分隔的房号数组
     * @param leaveTime
     * @param num 如果房号是逗号分隔，这个也要逗号分隔
     * @returns {*}
     */
    function doorWrite(roomId, leaveTime, num) {
        var ip=localStorage.getItem('ip');
        if(!ip){
            messageService.setMessage({type: 'error', content: '没有获取到本机ip，制卡失败，请重新登陆，或者检查localStorage当中'});
            popUpService.pop('message');
            return $q.reject();
        }
        return $http.get('http://'+ip+':8080/writeDoor?roomId='+roomId+'&leaveTimeStr='+leaveTime+'&num='+num)
    }

    function doorRead() {
        var x = document.getElementById("Control");
        var msg = x.readDoor.split(' ');
        if (msg.length == 1) {
            messageService.setMessage({type: 'error', content: '读卡失败,错误代码:' + msg});
            popUpService.pop('message');
        } else {
            var time = msg[1];
            messageService.setMessage({
                type: 'alert',
                content: '房间号:' + msg[0] + '有效期:' + time.substring(0, 4) + '-' + time.substring(4, 6) + '-' + time.substring(6, 8) + ' ' + time.substring(8, 10) + ':' + time.substring(10, 12)
            });
            popUpService.pop('message');
        }
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
        doorClear: doorClear,
    }
}]);
