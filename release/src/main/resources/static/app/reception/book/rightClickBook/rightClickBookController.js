/**
 * Created by Administrator on 2016/7/27 0027.
 * 补交和退订金写在了一个html中，通过传入的参数判断
 */
App.controller('rightClickBookController',['$scope','popUpService','doorInterfaceService','util','messageService','webService',function ($scope,popUpService,doorInterfaceService,util,messageService,webService) {
    $scope.book=popUpService.getParam();
    if($scope.book.bookSerial){
        $scope.category='客房预订';
    }else if($scope.book.deskBookSerial){
        $scope.category='餐饮预订';
    }
    /*安排房间*/
    $scope.arrangeRoom=function () {
        popUpService.close('rightClickBook');
        popUpService.pop('arrangeRoom',null,null,$scope.book);
    };
    /*弹出补交订金页面*/
    $scope.addSubscription=function () {
        popUpService.close('rightClickBook');
        popUpService.pop('addSubscription',null,null,[$scope.book,'add']);
    };
    /*弹出退订金页面*/
    $scope.cancelSubscription=function () {
        popUpService.close('rightClickBook');
        popUpService.pop('addSubscription',null,null,[$scope.book,'cancel']);
    };
    /*补打预订单*/
    $scope.printBook=function () {
        if($scope.category=='客房预订'){
            webService.post('printBookAgain',$scope.book)
                .then(function (r) {
                    webService.openReport(r);
                })
        }else if($scope.category=='餐饮预订'){
            webService.post('printDeskBookAgain',$scope.book)
                .then(function (r) {
                    webService.openReport(r);
                })
        }
        popUpService.close('rightClickBook');
    };
    /*修改订单*/
    $scope.bookUpdate=function () {
        popUpService.close('rightClickBook');
        popUpService.pop('bookInput',null,null,$scope.book);
    };
    /*提前制卡*/
    $scope.writeDoorBefore=function () {
        if($scope.book.bookRoomList.length>0) {
            doorInterfaceService.doorWriteList(util.objectListToString($scope.book.bookRoomList, 'roomId'), $scope.book.leaveTime);
        }else {
            messageService.setMessage({type:'error',content:'没有具体预定的房间，请先安排房间'});
            popUpService.pop('message');
        }
    }
}]);