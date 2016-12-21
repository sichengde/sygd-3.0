/**
 * Created by Administrator on 2016-08-08.
 */
App.factory('doorInterfaceService', ['messageService', 'dateFilter', '$q', 'popUpService', function (messageService, dateFilter, $q, popUpService) {
    function doorWrite(roomId, leaveTime, num) {
        var x = document.getElementById("Control");
        x.roomId = roomId;
        x.leaveTime = leaveTime;
        return write(x, num);
    }

    function doorWriteList(roomList, leavetime) {
        if (!roomList || roomList.length == 0) {
            return
        }
        messageService.setMessage({content: '即将制作' + roomList[0] + '房卡'});
        messageService.actionChoose()
            .then(function () {
                doorWrite(roomList[0], dateFilter(leavetime, 'yyyyMMddHHmmss'),1);
                roomList.splice(0, 1);
                if (roomList.length > 0) {
                    doorWriteList(roomList, leavetime);
                }
            }, function () {//点是点否都要继续
                roomList.splice(0, 1);
                if (roomList.length > 0) {
                    doorWriteList(roomList, leavetime);
                }
            });
    }

    function write(x, num) {
        if (x.door == 'false') {
            messageService.setMessage({content: '发卡失败，是否重新发卡'});
            return messageService.actionChoose()
                .then(function () {
                    write(x);
                }, function (r) {
                    return r;
                })
        } else {
            if (num == 1) {
                messageService.actionSuccess();
                return $q(function (resolve, reject) {
                    return resolve();
                });
            } else {
                messageService.setMessage({content: '是否制作第二张房卡'});
                messageService.actionChoose()
                    .then(function () {
                        num--;
                        write(x, num);
                    }, function () {
                        return $q(function (resolve, reject) {
                            return resolve();
                        });
                    })
            }
        }
    }

    function doorRead() {
        var x = document.getElementById("Control");
        var msg = x.readDoor.split(' ');
        if (msg.length == 1) {
            messageService.setMessage({type: 'error', content: '读卡失败,错误代码:' + msg})
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
    function getDoorIdNumber() {
        var x = document.getElementById("Control");
        //return x.doorCardId;//测试时
        return 3333;
    }

    return {
        doorWrite: doorWrite,
        doorRead: doorRead,
        doorWriteList: doorWriteList,
        doorClear: doorClear,
        getDoorIdNumber:getDoorIdNumber
    }
}]);
