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
        return $http.get('http://' + ip + ':8081/writeDoor?roomId='+roomIdList.join(',')+'&doorId=' + doorIdList.join(',') + '&leaveTimeStr=' + leaveTime + '&num=' + num)
    }

    function doorRead() {
        var ip = localStorage.getItem('ip');
        if (!ip) {
            messageService.setMessage({type: 'error', content: '没有获取到本机ip，制卡失败，请重新登陆，或者手动在localStorage中添加ip'});
            popUpService.pop('message');
            return $q.reject();
        }
        $http.get('http://' + ip + ':8081/readDoor')
            .then(function (r) {
                var doorIdMap = util.listToMapByField(dataService.getInterfaceDoorList(), 'doorId');
                var doorId=r.data.doorId;
                var date=r.data.date;
                var roomId=doorIdMap[doorId].roomId;
                /*说明门锁接口读不出来时间，所以只能通过在店户籍粗略查找*/
                if(date=='false'){
                    var query={condition:'room_id='+util.wrapWithBrackets(roomId)}
                    dataService.refreshCheckInList(query)
                        .then(function (ci) {
                            if(ci[0]){
                                date=dateFilter(ci[0].leaveTime,'yyyy-MM-dd HH:00:00');
                                messageService.setMessage({type:'alert',content:'房号:'+roomId+'||有效期:'+date});
                                popUpService.pop('message');
                            }else {
                                messageService.setMessage({type:'alert',content:'房号:'+roomId});
                                popUpService.pop('message');
                            }
                        })
                }else {
                    messageService.setMessage({type:'alert',content:'房号:'+roomId+'||有效期:'+date});
                    popUpService.pop('message');
                }
            })
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
