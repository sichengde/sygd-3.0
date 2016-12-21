App.controller('guestInfoController', ['$scope', 'webService', 'dataService','util', function ($scope, webService, dataService,util) {
    /*在店宾客*/
    $scope.checkInGuestFields = [
        {name: '姓名', id: 'name', width: '100px'},
        {name: '性别', id: 'sex', width: '60px'},
        {name: '房号', id: 'roomId', width: '100px'},
        {name: '国籍', id: 'country', width: '100px'},
        {name: '客源', id: 'guestSource', width: '100px'},
        {name: '到达时间', id: 'reachTime', width: '100px'},
        {name: '预离时间', id: 'leaveTime', width: '100px'},
        {name: '预付金额', id: 'deposit', width: '100px', sum: 'true'},
        {name: '消费金额', id: 'consume', width: '100px', sum: 'true'},
        {name: '欠款状态', id: 'minus', exp: 'consume-deposit', width: '100px'}
    ];
    $scope.checkInAndGuestList = [];
    dataService.initData(['refreshCheckInList', 'refreshCheckInGuestList'])
        .then(function () {
            var checkInList = dataService.getCheckInList();
            var checkInGuestList = dataService.getCheckInGuestList();
            for (var i = 0; i < checkInList.length; i++) {
                var checkIn = checkInList[i];
                var roomId = checkIn.roomId;
                for (var j = 0; j < checkInGuestList.length; j++) {
                    var checkInGuest = checkInGuestList[j];
                    if (roomId == checkInGuest.roomId) {
                        var extended = angular.extend(checkIn, checkInGuest);
                        if (extended.bed != 1) {//随行客人不计算押金和消费
                            extended.consume = 0;
                            extended.deposit = 0;
                        }
                        $scope.checkInAndGuestList.push(extended);
                    }
                }
            }
        });
    /*客史信息*/
    $scope.initConditionCheckInHistoryLog='id=-1';
    $scope.checkInHistoryFields=[
        {name:'身份证号',id:'cardId'},
        {name:'证件种类',id:'cardType'},
        {name:'姓名',id:'name'},
        {name:'生日',id:'birthdayTime', date: 'short'},
        {name:'性别',id:'sex'},
        {name:'民族',id:'race'},
        {name:'地址',id:'address'},
        {name:'联系电话',id:'phone'},
        {name:'最近消费',id:'lastTime',filter:'date',desc:'0'}
    ];
    $scope.checkInHistoryLogFields=[
        {name:'来店时间',id:'reachTime'},
        {name:'离店时间',id:'leaveTime'},
        {name:'房号',id:'roomId'},
        {name:'消费',id:'consume',sum:'true'},
        {name: '主账号', exp: 'groupAccount?groupAccount:selfAccount'}
    ];
    /*选中某条客史，下边显示客史明细*/
    $scope.chooseCheckInHistory=function (guest) {
        $scope.initConditionCheckInHistoryLog='card_id = '+util.wrapWithBrackets(guest.cardId);
    };
    /*餐饮在店桌台*/
    $scope.deskInFields=[
        {name:'桌号',id:'desk'},
        {name:'来店时间',id:'doTime'},
        {name:'人数',id:'num'},
        {name:'当前消费',id:'consume',sum:'true'},
        {name:'操作员',id:'userId'},
        {name:'营业部门',id:'pointOfSale'}
    ]
}]);