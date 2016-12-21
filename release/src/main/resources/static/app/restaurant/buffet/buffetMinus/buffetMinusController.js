App.controller('buffetMinusController',['$scope','dataService','LoginService','util','webService','popUpService','messageService',function ($scope,dataService,LoginService,util,webService,popUpService,messageService) {
    var pointOfSale=LoginService.getPointOfSale();
    $scope.buffetMinusList=[];
    $scope.deskInHistoryFields=[
        {name: '结账流水号', id: 'ckSerial'},
        {name: '来店时间', id: 'doTime'},
        {name: '消费总额', id: 'totalPrice'},
        {name: '来店人数', id: 'num'},
        {name: '操作员', id: 'userId'},
        {name: '营业部门', id: 'pointOfSale'},
        {name: '冲减', id: 'minus',boolean:true}
    ];
    dataService.refreshDeskInHistoryList({condition:'desk=\'自助餐\' and ifnull(disabled,false)=false and point_of_sale= '+util.wrapWithBrackets(pointOfSale),orderByListDesc:['done_time']})
        .then(function (r) {
            $scope.buffetMinusList=r;
        });
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
            })
    }
}]);