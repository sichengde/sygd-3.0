/**
 * Created by Administrator on 2016-09-18.
 */
App.controller('deskOtherToolsController', ['$scope','popUpService','webService','LoginService', function ($scope,popUpService,webService,LoginService) {
    $scope.deskBookFields = [
        {name: '订单编号', id: 'deskBookSerial',static:'true',filter:'input',width:'148px'},
        {name: '预定桌号', id: 'desk', width: '300px'},
        {name: '来店日期', id: 'reachTime',filter:'date',width:'160px'},
        {name: '保留日期', id: 'remainTime',filter:'date',width:'160px'},
        {name: '姓名', id: 'guestName',width:'160px'},
        {name: '电话', id: 'phone',width:'109px'},
        {name: '订金', id: 'subscription',sum:'true',static:'true',width:'90px'},
        {name: '操作员', id: 'userId',filter:'list',width:'106px'},
        {name: '营业部门', id: 'pointOfSale',filter:'list',width:'160px'},
        {name: '备注', id: 'remark',width:'106px'}
    ];
    /*初始化szTable刷新方法*/
    var deskBookRefresh;
    $scope.deskBookInitRefresh = function (f) {
        deskBookRefresh = f;
    };
    $scope.deskBookInput = function () {
        popUpService.pop('deskBookIn');
    };
    $scope.refresh = function () {
        deskBookRefresh();
    };
    /*预定预览信息初始化*/
    $scope.refreshFeature=function (date) {
        var pointOfSale=LoginService.getPointOfSale();
        var post={};
        post.pointOfSale=pointOfSale;
        post.date=date;
        webService.post('deskGetByDate',post)
            .then(function (r) {
                $scope.deskList = r;
            });
    }
}]);