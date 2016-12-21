/**
 * Created by Administrator on 2016/9/18 0018.
 */
App.controller('deskBookInController', ['$scope', 'dataService', 'util', 'webService', 'popUpService', 'LoginService','messageService','dateFilter', function ($scope, dataService, util, webService, popUpService, LoginService,messageService,dateFilter) {
    $scope.deskBook = {};
    var chooseDeskList = [];
    var p1={condition:'check_in=1'};
    dataService.initData(['refreshCurrencyList','refreshDeskList','refreshTimeNow'],[p1])
        .then(function () {
            $scope.deskList = dataService.getDeskList();
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            $scope.deskBook.currency='人民币';
            $scope.deskBook.reachTime = util.newDateNow(dataService.getTimeNow());
            $scope.deskBook.remainTime = util.newDateNow(dataService.getTimeNow());
        });
    $scope.addOrDeleteDesk = function (r,shiftKey) {
        if (shiftKey) {
            var j = $scope.deskList.indexOf(r);
            for (var i = j; i >= 0; i--) {
                if (util.getValueByField(chooseDeskList, 'name', $scope.deskList[i].name)) {
                    break;
                } else {
                    $scope.addOrDeleteDesk($scope.deskList[i]);
                }
            }
        } else {
            if (util.deleteFromArray(chooseDeskList, 'name', r.name)) {
                r.hover = null;
            } else {
                var desk = {};
                desk.name = r.name;
                chooseDeskList.push(desk);
                r.hover = 'hover';
            }
        }
    };

    /*清除选择*/
    $scope.clearList = function () {
        angular.forEach($scope.deskList, function (value) {
            value.hover = null;
        });
        chooseDeskList.length = 0;
    };
    
    $scope.deskBookIn = function () {
        $scope.deskBook.pointOfSale = LoginService.getPointOfSale();
        $scope.deskBook.userId = LoginService.getUser();
        $scope.deskBook.desk=util.objectListToString(chooseDeskList,'name').join(',');
        var interval=dataService.getOtherParamMapValue('预定间隔');
        if(interval!='n'){//需要判断一下时间间隔，小于的要提示
            var p={condition:'reach_time<'+util.wrapWithBrackets(dateFilter($scope.deskBook.reachTime,'yyyy-MM-dd HH:mm:ss'))+'+INTERVAL '+interval+' HOUR AND reach_time>'+util.wrapWithBrackets(dateFilter($scope.deskBook.reachTime,'yyyy-MM-dd HH:mm:ss'))+'-INTERVAL '+interval+' HOUR '};
            dataService.refreshDeskBookList(p)
                .then(function () {
                    var deskBookList=dataService.getDeskBookList();
                    var repeatDesk="";
                    for (var i = 0; i < deskBookList.length; i++) {//遍历所有这个时间段的预订单
                        var book = deskBookList[i];
                        var bookedDesk=book.desk.split(',');//该订单已经预定的房间
                        for (var j = 0; j < chooseDeskList.length; j++) {//遍历所有选择的房间
                            var desk = chooseDeskList[j];
                            if(bookedDesk.indexOf(desk.name)>-1){//存在冲突
                                repeatDesk+=desk.name+',';
                            }
                        }
                    }
                    if(repeatDesk.length>0){
                        messageService.setMessage({content:repeatDesk+'在'+interval+'小时内有预定，是否确定下单'})
                        messageService.actionChoose()
                            .then(function () {
                                webService.post('deskBookIn', $scope.deskBook)
                                    .then(function () {
                                        popUpService.close('deskBookIn');
                                    })
                            })
                    }else {
                        webService.post('deskBookIn', $scope.deskBook)
                            .then(function (r) {
                                popUpService.close('deskBookIn');
                                webService.openReport(r);
                            })
                    }
                })
        }else {
            webService.post('deskBookIn', $scope.deskBook)
                .then(function () {
                    popUpService.close('deskBookIn');
                })
        }
    };
    
}]);