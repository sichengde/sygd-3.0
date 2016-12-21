/**
 * Created by Administrator on 2016-05-03.
 */
App.controller('OtherConsumeController',['$scope','webService','dataService','receptionService','popUpService','host',function($scope,webService,dataService,receptionService,popUpService,host){
    var debt={};
    $scope.room=receptionService.getChooseRoom();
    $scope.pointOfSaleList=dataService.getRoomPointOfSale;
    $scope.pointOfSale=$scope.pointOfSaleList[0];
    $scope.remark='';
    /*未赋值的变量：deposit,currency,paySerial,protocol
    服务器端赋值的变量：doTime，selfAccount,groupAccount*/


    $scope.addDebt=function(){
        debt.pointOfSale=$scope.pointOfSale;
        if ($scope.manualConsume=='冲账'){
            debt.consume=-Math.abs($scope.consume);
            debt.currency='冲账';
        }else {
            debt.consume=Math.abs($scope.consume);
            debt.currency='杂单';
        }
        debt.description=$scope.manualConsume+':'+$scope.remark;
        debt.roomId=$scope.room.roomId;
        webService.post('otherConsume',debt)
            .then(
            function(r){
                popUpService.close('otherConsume');
                /*弹出打印预览界面*/
                window.open(host+"/receipt/" + r);
            }
        )
    };

}]);