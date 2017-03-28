/**
 * Created by 舒展 on 2016-06-15.
 * 收取预付
 */
App.controller('depositInController',['$scope','dataService','receptionService','util','webService','popUpService','messageService','doorInterfaceService','dateFilter','host',function ($scope, dataService, receptionService, util, webService, popUpService,messageService,doorInterfaceService,dateFilter,host) {
    $scope.room=receptionService.getChooseRoom();
    var backUpLeaveTime=$scope.room.checkIn.leaveTime;//备份离店时间，确认修改时进行比较，判断需不需要重新制作房卡，同户籍维护
    var checkInGuestList;
    var guestList = util.objectListToString($scope.room.checkInGuestList, 'name');
    if (guestList) {
        $scope.nameString = guestList.join(',');
    }
    $scope.currency='人民币';
    $scope.currencyBack='人民币';
    var p1 = {condition: 'check_in=1'};
    dataService.initData(['refreshCurrencyList','refreshCheckInGuestList'],[p1])
        .then(function () {
            $scope.currencyList=util.objectListToString(dataService.getCurrencyList(),'currency');
            checkInGuestList=dataService.getCheckInGuestList();
        });
    /*整退预付*/
    $scope.cancelDeposit=function () {
        popUpService.pop('cancelDeposit',null,null,{room:$scope.room.roomId});
    };
    /*单退押金*/
    $scope.cancelDepositSingle=function () {
        if(!$scope.moneyBack){
            messageService.setMessage({type:'error',content:'退预付不可以为空'});
            popUpService.pop('message');
            return;
        }
        if($scope.moneyBack<=0){
            messageService.setMessage({type:'error',content:'退预付必须大于0'});
            popUpService.pop('message');
            return;
        }
        if($scope.currencyBack=='会员'){
            messageService.setMessage({type:'error',content:'退预付不可以为会员币种，请选择单退预付(整)'});
            popUpService.pop('message');
            return;
        }
        var debtPost={};
        debtPost.roomId=$scope.room.roomId;
        debtPost.pointOfSale='房费';
        debtPost.deposit=-$scope.moneyBack;
        debtPost.currency=$scope.currencyBack;
        debtPost.roomId=$scope.room.roomId;
        debtPost.category='单退押金';
        debtPost.description='单退预付';
        webService.post('cancelDepositSingle',debtPost)
            .then(function () {
                messageService.actionSuccess();
                popUpService.close('depositIn');
            })
    };
    /**
     * 收取预付，暂时不考虑会员卡余额预付
     */
    $scope.depositIn=function () {
        var depositIn={};
        depositIn.roomId=$scope.room.roomId;
        depositIn.money=$scope.money;
        depositIn.currency=$scope.currency;
        if(!$scope.money){
            messageService.setMessage({type:'error',content:'补交押金不可以为空'});
            popUpService.pop('message');
            return;
        }
        if($scope.money<=0){
            messageService.setMessage({type:'error',content:'补交押金必须大于0'});
            popUpService.pop('message');
            return;
        }
        if($scope.currency=='会员'){
            if(!$scope.room.checkIn.vipNumber){
                messageService.setMessage({type:'error',content:'该房间不是会员开房，无法使用会员余额当做押金'});
                popUpService.pop('message');
                return;
            }else {
                depositIn.currencyAdd=$scope.room.checkIn.vipNumber;
            }
        }
        if($scope.checkInGuest){//有床位号
            depositIn.bed=$scope.checkInGuest.bed;
        }
        if(backUpLeaveTime!=$scope.room.checkIn.leaveTime){//下边的判断是做卡，这里的判断是修改押金的同时更新户籍信息，做卡必须在操作成功之后才可以
            depositIn.leaveTime=$scope.room.checkIn.leaveTime;
        }
        webService.post('depositIn',depositIn)
            .then(function (r) {
                if(backUpLeaveTime!=$scope.room.checkIn.leaveTime){
                    var num=1;//做几张房卡
                    if(dataService.getOtherParamMapValue('按人数发卡')=='y'){
                        num=checkInGuestList.length;
                    }
                    doorInterfaceService.doorWrite([$scope.room.roomId], dateFilter($scope.room.checkIn.leaveTime, 'yyyyMMddHHmmss'),[num])
                }
                window.open(host + "/receipt/" + r);
                popUpService.close('depositIn');
            })
    }
}]);