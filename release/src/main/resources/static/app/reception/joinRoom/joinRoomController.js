/**
 * Created by 舒展 on 2016-04-28.
 */
App.controller('joinRoomController', ['$scope', 'dataService', 'popUpService', 'webService', 'util', 'messageService', 'roomFilter',
    function ($scope, dataService, popUpService, webService, util, messageService, roomFilter) {
        var rOld;//限制只能取同一公付号解除联房
        var groupAccount;//选取的公付账号
        function init() {
            /*要提交的联房对象，是个房号数组*/
            $scope.roomAddList = [];
            $scope.roomUnbindList = [];
            rOld = null;
            var p = {condition: 'leader=\'联房\''};
            dataService.initData(['refreshCheckInGroupList',  'refreshRoomList'], [p])
                .then(function () {
                    $scope.checkInGroupList = dataService.getCheckInGroupList();
                    $scope.checkInList = dataService.getCheckInList();
                    $scope.roomList = dataService.getRoomList();
                    $scope.roomShowList=roomFilter($scope.roomList,['散客房'],'全部',0);
                });
        }

        init();
        /*选择联房的房间*/
        $scope.addOrDeleteRoomId = function (r, shiftKey) {
            if (shiftKey) {
                var j = $scope.roomShowList.indexOf(r);
                for (var i = j; i >= 0; i--) {
                    if ($scope.roomAddList.indexOf($scope.roomShowList[i].roomId)>-1) {
                        break;
                    } else {
                        $scope.addOrDeleteRoomId($scope.roomShowList[i]);
                    }
                }
            } else {
                if (util.deleteFromArray($scope.roomAddList, null, r.roomId)) {
                    r.hover = null;
                } else {
                    $scope.roomAddList.push(r.roomId);
                    r.hover = 'hover';
                }
            }

        };

        /*清除选择*/
        $scope.clearList = function () {
            angular.forEach($scope.roomShowList, function (value) {
                value.hover = null;
            });
            $scope.roomAddList= [];
        };

        /*提交联房请求*/
        $scope.joinRoomAction = function () {
            if ($scope.roomAddList.length < 2) {
                messageService.setMessage({type: 'error', content: '请输入两间或以上的房间来进行联房'});
                popUpService.pop('message');
                return;
            }
            var joinRoomPost={};
            joinRoomPost.roomAddList=$scope.roomAddList;
            joinRoomPost.remark=$scope.remark;
            webService.post('joinRoom', joinRoomPost)
                .then(function () {
                        /*成功画面*/
                        window.location.reload();
                        messageService.actionSuccess();
                    }
                )
        };
        /*选择解除联房的房间，如果不是一列的，则清空重置list*/
        $scope.chooseRoom = function (r, r2) {
            if (!rOld) {
                rOld = r.$$hashKey;
            }
            if (rOld != r.$$hashKey) {
                angular.forEach($scope.roomUnbindList, function (item) {
                    angular.element('#' + item).removeClass("hover");

                });
                $scope.roomUnbindList = [];
                rOld = r.$$hashKey;
            }
            var id = '#' + r2.roomId;
            if (util.deleteFromArray($scope.roomUnbindList, null, r2.roomId)) {
                /*修改样式*/
                angular.element(id).removeClass("hover");
            } else {
                $scope.roomUnbindList.push(r2.roomId);
                angular.element(id).addClass("hover");
            }
            groupAccount = r.groupAccount;
        };

        /*提交解除联房请求*/
        $scope.unBindRoomAction = function () {
            /*如果只剩下一个房间那么也加上*/
            var var1 = roomFilter($scope.roomList, ['散客房'], '全部', groupAccount);
            if ($scope.roomUnbindList.length == var1.length - 1) {
                $scope.roomUnbindList = util.objectListToString(var1, 'roomId');
            }
            webService.post('unBindRoom', $scope.roomUnbindList)
                .then(function () {
                        /*成功画面*/
                        init();
                        messageService.actionSuccess();
                    }
                )
        };
    }]);