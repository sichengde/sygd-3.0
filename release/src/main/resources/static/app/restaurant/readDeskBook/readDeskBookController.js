/**
 * Created by Administrator on 2016/10/30 0030.
 */
App.controller('readDeskBookController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.deskBookFields = [
        {name: '订单编号', id: 'deskBookSerial',static:'true',filter:'input',width:'148px'},
        {name: '预定桌号', id: 'desk', width: '300px'},
        {name: '来店日期', id: 'reachTime',filter:'date',width:'160px',asc:'true'},
        {name: '姓名', id: 'guestName',width:'160px'},
        {name: '电话', id: 'phone',width:'109px'},
        {name: '订金', id: 'subscription',static:'true',width:'90px'},
        {name: '营业部门', id: 'pointOfSale',filter:'list',width:'160px'},
        {name: '备注', id: 'remark',width:'106px'}
    ];
    $scope.chooseBook=function (item) {
        /*向上广播选择的预定订单*/
        $scope.$emit('chooseDeskBook',item);
        popUpService.close('readDeskBook');
    }
}]);