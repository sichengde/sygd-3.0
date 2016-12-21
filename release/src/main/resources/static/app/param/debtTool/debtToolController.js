/**
 * Created by Administrator on 2016-09-09.
 */
App.controller('debtToolController',['$scope','webService','messageService','dataService','popUpService','LoginService','util',function ($scope,webService,messageService,dataService,popUpService,LoginService,util) {
    $scope.debtPayFields = [
        {name: '结账时间', id: 'doneTime', date: 'true', width: '120px', filter: 'date', desc: '0'},
        {name: '结账金额', id: 'debtMoney', width: '100px' ,sum:'true'},
        {name: '币种', id: 'currency', width: '100px', filter: 'list'},
        {name: '单位名称', id: 'company', width: '230px', filter: 'list'},
        {name: '主账号', exp: 'groupAccount?groupAccount:selfAccount', width: '150px'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '离店流水号', id: 'checkOutSerial', width: '150px'},
        {name: '结账类型', id: 'debtCategory', width: '150px', filter: 'list'},
        {name: '操作员', id: 'userId', width: '100px'}
    ];
    $scope.deskInHistoryCondition='desk!=\'自助餐\'';
    /*手动夜审*/
    $scope.manualNightAction=function () {
        if(Math.abs(new Date-new Date(dataService.getOtherParamMapValue("上次夜审")))>24*60*60*1000){
            webService.post('manualNightAction')
                .then(function () {
                    messageService.actionSuccess();
                })
        }else {
            messageService.setMessage({content:'系统在24小时内已经进行过一次夜审，是否继续？'});
            messageService.actionChoose()
                .then(function () {
                    webService.post('manualNightAction')
                        .then(function () {
                            messageService.actionSuccess();
                        })
                })
        }
    };
    /*冲账*/
    $scope.minusBuffet=function () {
        popUpService.pop('buffetMinus');
    };
    var pointOfSale=LoginService.getPointOfSale();
    $scope.buffetMinusList=[];
    $scope.deskInHistoryFields=[
        {name: '结账流水号', id: 'ckSerial',filter:'input'},
        {name: '桌号', id: 'desk',filter:'list'},
        {name: '来店时间', id: 'doTime',desc:'0',filter:'date'},
        {name: '消费总额', id: 'totalPrice'},
        {name: '来店人数', id: 'num'},
        {name: '操作员', id: 'userId',filter:'list'},
        {name: '营业部门', id: 'pointOfSale',filter:'list'},
        {name: '冲减', id: 'minus',boolean:true}
    ];
    function refreshDeskInHistory() {
        dataService.refreshDeskInHistoryList({condition:'desk=\'自助餐\' and ifnull(disabled,false)=false',orderByListDesc:['doTime']})
            .then(function (r) {
                $scope.buffetMinusList=r;
            });
    }
    refreshDeskInHistory();
    $scope.submit=function () {
        var post=[];
        for (var i = 0; i < $scope.buffetMinusList.length; i++) {
            var buffetMinus = $scope.buffetMinusList[i];
            if(buffetMinus.minus){
                post.push(buffetMinus);
            }
        }
        webService.post('buffetCancel',post)
            .then(function (r) {
                messageService.actionSuccess();
                popUpService.close('buffetMinus');
                webService.openReport(r);
                refreshDeskInHistory();
            })
    }
}]);