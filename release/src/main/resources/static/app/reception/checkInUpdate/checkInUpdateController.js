/**
 * Created by Administrator on 2016/8/4 0004.
 */
App.controller('checkInUpdateController',['$scope','receptionService','dataService','util','popUpService','webService','doorInterfaceService','dateFilter',function ($scope,receptionService,dataService,util,popUpService,webService,doorInterfaceService,dateFilter) {
    $scope.room=receptionService.getChooseRoom();
    $scope.checkIn=$scope.room.checkIn;
    $scope.editableRoomPrice=dataService.getOtherParamMapValue('可编辑房价')=='y';
    var backUpLeaveTime=$scope.checkIn.leaveTime;//备份离店时间，确认修改时进行比较，判断需不需要重新制作房卡
    var checkInGuestList;
    $scope.checkInGuestFields=[
        {name:'房号',id:'roomId',width:'100px',static:'true',default:$scope.room.roomId},
        {name:'姓名',id:'name',width:'100px',notNull:'true'},
        {name:'生日',id:'birthdayTime',date:'short',width:'140px'},
        {name:'性别',id:'sex',selectId:'0',width:'60px',default: '男'},
        {name:'民族',id:'race',width:'50px',default:'汉'},
        {name:'证件类型',id:'cardType',selectId:'1',width:'100px',notNull:'true',default: '身份证'},
        {name:'证件号码',id:'cardId',width:'200px',notNull:'true'},
        {name:'地址',id:'address',width:'200px'},
        {name: '床位', id: 'bed', width: '50px'}
    ];
    $scope.checkInGuestSelect=[];
    $scope.checkInGuestSelect[0]=dataService.getSexList;
    $scope.checkInGuestSelect[1]=dataService.getCardTypeList;
    $scope.checkInGuestCondition='room_id='+util.wrapWithBrackets($scope.room.roomId);
    dataService.initData(['refreshCheckInGuestList','refreshGuestSourceList'],[{condition:$scope.checkInGuestCondition}])
        .then(function () {
            $scope.guestSourceList = util.objectListToString(dataService.getGuestSourceList(), 'guestSource')
            checkInGuestList=dataService.getCheckInGuestList();
        });
    /*提交修改*/
    $scope.submitCheckInUpdate=function () {
        webService.post('checkInUpdate',$scope.checkIn)
            .then(function () {
                if(backUpLeaveTime!=$scope.checkIn.leaveTime){
                    var num=1;//做几张房卡
                    if(dataService.getOtherParamMapValue('按人数发卡')=='y'){
                        num=checkInGuestList.length;
                    }
                    doorInterfaceService.doorWrite([$scope.room.roomId], dateFilter($scope.checkIn.leaveTime, 'yyyyMMddHHmmss'),[num])
                }
                popUpService.close('checkInUpdate');
            });
    };
    /*补做房卡*/
    $scope.writeDoorAgain=function () {
        var num=1;//做几张房卡
        if(dataService.getOtherParamMapValue('按人数发卡')=='y'){
            num=checkInGuestList.length;
        }
        doorInterfaceService.doorWrite([$scope.room.roomId], dateFilter($scope.checkIn.leaveTime, 'yyyyMMddHHmmss'),[num]);
    };
    /*/!*转为团队*!/
    $scope.changeToGroup=function () {

    }*/
}]);