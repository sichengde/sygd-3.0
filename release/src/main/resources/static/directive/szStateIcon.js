App.directive('szStateIcon', ['roomStateEnFilter', 'util', 'dateFilter', function (roomStateEnFilter, util, dateFilter) {
    function calculateRoomStateIcon(room) {
        var out = '<ul class="rigtb">';
        if (room.bookList) {
            out += '<li class="icon-lock"><span>预定</span></li>'
        }
        if (room.birthday) {
            out += '<li class="icon-heart"><span>生日</span></li>'
        }
        if (room.checkIn) {
            if (room.checkIn.important) {
                out += '<li class="icon-exclamation-sign"><span>特殊要求</span></li>'
            }
            if (room.checkIn.vip) {
                out += '<li class="icon-star"><span>贵宾标志</span></li>';
            }
            if (room.checkIn.roomPriceCategory == '小时房') {
                out += '<li class="icon-time"><span>小时房</span></li>'
            }
        }
        if (room.checkInGroup && room.checkInGroup.leaderRoom) {
            out += '<li class="icon-flag"><span>领队标志</span></li>'
        }
        if (room.todayLeave) {
            out += '<li class="icon-ylbz"><span>预离标志</span></li>'
        }
        if (room.dirty) {
            out += '<li class="icon-zfbz"><span>脏房标志</span></li>'
        }
        if (room.todayLock) {
            out += '<li class="icon-lock" style="color:#c60000"><span>锁房</span></li>'
        }
        if (room.longRoom) {
            out += '<li class="icon-home" style="color:#ff542e"><span>长包房</span></li>'
        }
        out += '</ul>';
        return out;
    }

    function calculateHover(room) {
        var out = '';
        if (room.checkIn) {
            var guestName = util.objectListToString(room.checkInGuestList, 'name').join(',');
            var var1 = room.checkIn.consume * 1 - room.checkIn.deposit * 1;
            out += '<ul style="margin-bottom: 12px;">' +
                '<li class="krName"><b>' + guestName + '</b></li>' +
                '<li class="kyName"><b>' + room.checkIn.guestSource + '</b></li>' +
                '<li class="qkNumber"><b>房间欠款：￥' + var1 + '</b></li>';
            if (room.checkInGroup) {
                var var2 = room.checkInGroup.consume * 1 - room.checkInGroup.deposit * 1;
                out += '<li class="qkNumber"><b>团队欠款：￥' + var2 + '</b></li>';
            }
            out += '<div class="clear"></div></ul>';
        }
        var out2 = '';
        if (room.state == '走客房') {
            var leaveTime = dateFilter(room.checkOutTime, 'yyyy-MM-dd HH:mm:ss');
            out2 += '<li><b>离店时间：</b>' + leaveTime + '</li>';
        }
        if (room.checkIn) {
            var reachTime = dateFilter(room.checkIn.reachTime, 'yyyy-MM-dd HH:mm:ss');
            out2 += '<li><b>抵店日期：</b>' + reachTime + '</li>';
        }
        if (room.checkIn) {
            var leaveTime = dateFilter(room.checkIn.leaveTime, 'yyyy-MM-dd HH:mm:ss');
            out2 += '<li><b>预离日期：</b>' + leaveTime + '</li>';
        }
        if (room.checkInGroup) {
            out2 += '<li><b>团名：</b>' + room.checkInGroup.name + '</li>';
        }
        if (room.bookList) {
            for (var i = 0; i < room.bookList.length; i++) {
                var book = room.bookList[i];
                var reachTime = dateFilter(book.reachTime, 'yyyy-MM-dd HH:mm:ss');
                out2 += '<li style="width: 520px"><b style="color: #ef9d00">预订号：</b>' + book.bookSerial + ' &nbsp; <b>来期：</b>' + reachTime + ' &nbsp; <b>团名：</b>' + book.name + '</li>';
            }
        }
        if (room.state == '维修房') {
            var reachTime = dateFilter(room.repairTime, 'yyyy-MM-dd HH:mm:ss');
            out2 += '<li><b>维修原因：' + room.repairReason + '}}</b></li>' +
                '<li><b>预计完工：' + repairTime + '</b></li>';
        }
        if (room.todayLock) {
            out2 += '<li><b>锁房原因：</b>' + room.todayLock + '</li>';
        }
        if (out2 != '') {
            out2 += '<div class="clear"></div>';
            out2 = '<ul>' + out2 + '</ul>'
        }
        /*只要有一个不是''就包裹起来*/
        if (out || out2) {
            return '<div class="hoverDiv">' + out + out2 + '</div>';
        } else {
            return '';
        }
    }

    return {
        restrict: 'A',
        controller: ['$scope', '$compile', function ($scope, $compile) {
            this.room = $scope.r;
        }],
        link: function (scope, element, attr, ctr) {
            var room = ctr.room;
            var roomStateEn = roomStateEnFilter(room.state);
            var appendStr = '<ul>' +
                '<li>' + room.roomId + '</li>' +
                '<li>' + room.category + '</li>' +
                '<li></li>' +
                '<li class="' + roomStateEn + '"></li>';
            /*房态小图标可选项*/
            appendStr += calculateRoomStateIcon(room);
            appendStr += '</ul>';
            appendStr += calculateHover(room);
            element.append(appendStr);
        }
    }
}]);