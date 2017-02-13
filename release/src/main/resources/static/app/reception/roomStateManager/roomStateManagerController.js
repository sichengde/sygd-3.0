/**
 * Created by Administrator on 2016-06-28.
 */
App.controller('roomStateManagerController', ['$scope', '$interval', 'dataService', 'util', 'roomFilter', 'webService', 'messageService', 'receptionService', 'popUpService', function ($scope, $interval, dataService, util, roomFilter, webService, messageService, receptionService, popUpService) {
    function refresh() {
        $scope.roomChooseList = [];
        $scope.roomStateList = ['可用房', '走客房', '维修房',"自用房","备用房"];
        $scope.roomState = $scope.roomStateList[0];
        dataService.initData(['refreshRoomList'])
            .then(function () {
                $scope.roomList = dataService.getRoomList();
                $scope.roomShowList = roomFilter($scope.roomList, $scope.roomState, '全部');
            });
    }

    refresh();
    $scope.addOrDeleteRoom = function (r, shiftKey) {
        if (shiftKey) {
            var j = $scope.roomShowList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                var obj = $scope.roomShowList[i];
                if (util.getValueByField($scope.roomChooseList, 'roomId', obj.roomId)) {
                    break;
                } else {
                    $scope.addOrDeleteRoom(obj);
                }
            }
        } else {
            if (util.deleteFromArray($scope.roomChooseList, 'roomId', r.roomId)) {
                /*修改样式*/
                r.hover = null;
            } else {
                $scope.roomChooseList.push(r);
                r.hover = 'hover';
            }
        }
    };
    /*清除选择*/
    $scope.clearList = function () {
        angular.forEach($scope.roomShowList, function (value) {
            value.hover = null;
        });
        $scope.roomChooseList.length = 0;
    };
    /*监听操作模式的变化，和筛选条件的变化*/
    $scope.$watchGroup(['action', 'roomState'], function (newValues, oldValues) {
        if ($scope.action == '房态转换') {
            $scope.roomShowList = roomFilter($scope.roomList, $scope.roomState, '全部');
        } else {
            $scope.roomShowList = roomFilter($scope.roomList, '全部', '全部', null, 1);
        }
        /*如果改变的是房态，那么把应该亮的点亮*/
        if (newValues[1] != oldValues[1]) {
            receptionService.hoverChooseRoom($scope.roomShowList, $scope.roomChooseList);
        }
        /*如果改变的是操作，那就先清空选择列表*/
        if (newValues[0] != oldValues[0]) {
            $scope.roomChooseList = [];
        }
    });
    /*提交*/
    $scope.commit = function () {
        if ($scope.action == '房态转换') {
            /*输入验证*/
            if (!$scope.targetState) {
                messageService.setMessage({type: 'error', content: '请输入转换的目标房态'});
                popUpService.pop('message');
                return;
            }
            angular.forEach($scope.roomChooseList, function (item) {
                item.state = $scope.targetState;
                if ($scope.targetState == '维修房') {
                    item.repairReason = $scope.repairReason;
                    item.repairTime = $scope.repairTime;
                }
                if($scope.targetState=='自用房'||$scope.targetState=='备用房'){
                    item.ifRoom=false;
                }else {
                    item.ifRoom=true;
                }
            });
            webService.post('roomUpdate', $scope.roomChooseList)
                .then(function () {
                    messageService.actionSuccess();
                    refresh();
                });
        } else {
            popUpService.pop('cleanRoom',null,null,$scope.roomChooseList);
        }
    };
}]);
App.controller('cleanRoomDetailController',['$scope',function ($scope) {
    /*房间清扫记录*/
    $scope.cleanRoomFields=[
        {name:'数量',id:'num',sum:'true'},
        {name:'清扫类别',id:'category',filter:'list'},
        {name:'房间',id:'room'},
        {name:'清扫人员',id:'userId',filter:'list'},
        {name:'清扫时间',id:'doTime',filter:'date',desc:'0'}
    ]
}]);