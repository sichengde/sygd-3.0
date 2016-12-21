/**
 * Created by Administrator on 2016-07-28.
 */
App.controller('futureRoomStateController', ['$scope', 'dataService', 'util', function ($scope, dataService, util) {
    $scope.bookList = dataService.getBookList();
    dataService.initData(['refreshRoomList', 'refreshCheckInList'])
        .then(function () {
            $scope.roomList = dataService.getRoomList();
            google.charts.setOnLoadCallback($scope.drawChart);
        });
    $scope.a = '舒展';
    $scope.days=100;
    $scope.drawChart = function () {
        var container = document.getElementById('timeline');
        var chart = new google.visualization.Timeline(container);
        var dataTable = new google.visualization.DataTable();
        dataTable.addColumn({type: 'string', id: 'Position'});
        dataTable.addColumn({type: 'string', id: 'Name'});
        dataTable.addColumn({type: 'date', id: 'Start'});
        dataTable.addColumn({type: 'date', id: 'End'});
        var rows = [];
        for (var i = 0; i < $scope.roomList.length; i++) {
            var room = $scope.roomList[i];
            if (room.checkIn) {
                /*如果当前时间已经超过预计离店时间，则前后都设置为当前时间*/
                if (Date.parse(new Date()) > room.checkIn.leaveTime) {
                    rows.push([room.roomId, '在住', new Date(), new Date()])
                } else if (Date.parse(new Date()) > room.checkIn.reachTime) {
                    rows.push([room.roomId, '在住', new Date(), new Date(room.checkIn.leaveTime)])
                } else {
                    rows.push([room.roomId, '在住', new Date(room.checkIn.reachTime), new Date(room.checkIn.leaveTime)])
                }
            }
            if (room.bookList) {
                for (var j = 0; j < room.bookList.length; j++) {
                    var book = room.bookList[j];
                    if (Date.parse(new Date()) > book.reachTime) {
                        /*来期小于当前时间且离期也小于当前时间*/
                        if (Date.parse(new Date()) > book.leaveTime) {

                        } else {///*来期小于当前时间且离期大于当前时间*/
                            rows.push([room.roomId, '预定', new Date(), new Date(book.leaveTime)])
                        }
                    } else {//正常情况
                        rows.push([room.roomId, '预定', new Date(book.reachTime), new Date(book.leaveTime)])
                    }
                }
            }
            if (room.state == '维修房') {
                /*如果当前时间已经超过预计离店时间，则前后都设置为当前时间*/
                if (Date.parse(new Date()) > room.repairTime) {
                    rows.push([room.roomId, '维修', new Date(), new Date()])
                } else {
                    rows.push([room.roomId, '维修', new Date(), new Date(room.repairTime)])
                }
            }
        }
        rows.push(['区间','范围',new Date(Date.parse(new Date())+$scope.days*24*60*60*1000),new Date(Date.parse(new Date())+$scope.days*24*60*60*1000)]);
        dataTable.addRows(rows);
        var options = {
            hAxis: {
                format: 'yy/M/d'
            },
            width: 10000
        };
        chart.draw(dataTable, options);
    };
    $scope.resizeChart=function () {
        google.charts.setOnLoadCallback($scope.drawChart);
    }
}]);