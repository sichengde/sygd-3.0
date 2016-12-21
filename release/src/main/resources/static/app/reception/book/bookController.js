/**
 * Created by Administrator on 2016-04-29.
 */
App.controller("BookController",['$scope','popUpService','dataService','$route','util','dateFilter',function($scope, popUpService, dataService,$route, util, dateFilter){
    if($route.current.params.mode=='edit'){
        $scope.editable=true;
    }
    $scope.bookFields=[
        {name:'预定单号',id:'bookSerial',width:'89px',static:'true'},
        {name:'订单名称',id:'name',width:'89px',static:'true',filter:'list'},
        {name:'抵达时间',id:'reachTime',width:'129px',date:'true',filter:'date'},
        {name:'预离时间',id:'leaveTime',width:'129px',date:'true'},
        {name:'保留时间',id:'remainTime',width:'129px',date:'true'},
        {name:'单位名称',id:'company',width:'99px',static:'true',filter:'list'},
        {name:'房价协议',id:'protocol',width:'89px',static:'true'},
        {name:'订金',id:'subscription',width:'49px',static:'true',sum:'true'},
        {name:'备注',id:'mark',width:'69px'},
        {name:'电话',id:'phone',width:'89px'},
        {name:'总房数',id:'totalRoom',width:'49px',static:'true',sum:'true'},
        {name:'已开房数',id:'bookedRoom',width:'69px',static:'true'},
        {name:'操作员',id:'userId',width:'59px',static:'true',filter:'list'},
        {name:'操作时间',id:'doTime',width:'129px',date:'true',static:'true'},
        {name:'状态',id:'state',selectId:'0',width:"39px",static:'true'}
    ];
    $scope.bookHistoryFields=[
        {name:'预定单号',id:'bookSerial',width:'100px'},
        {name:'订单名称',id:'name',width:'100px'},
        {name:'抵达时间',id:'reachTime',width:'190px',date:'true'},
        {name:'离店时间',id:'leaveTime',width:'190px',date:'true'},
        {name:'单位名称',id:'company',width:'200px'},
        {name:'房价协议',id:'protocol',width:'110px'},
        {name:'订金',id:'subscription',width:'80px'},
        {name:'备注',id:'mark',width:'80px'},
        {name:'电话',id:'phone',width:'100px'},
        {name:'总房数',id:'totalRoom',width:'60px'},
        {name:'已开房数',id:'bookedRoom',width:'80px'},
        {name:'操作员',id:'userId',width:'80px'},
        {name:'操作时间',id:'doTime',width:'190px',date:'true'},
        {name:'状态',id:'state',width:"50px"},
        {name:'完成时间',id:'doneTime',width:"190px"}
    ];
    $scope.bookRoomFields = [
        {name: '房号', id: 'roomId',width:'200px'},
        {name: '房类', id: 'roomCategory',width:'200px'},
        {name: '房价', id: 'roomPrice',width:'200px'}
    ];
    $scope.bookRoomCategoryFields = [
        {name: '房类', id: 'roomCategory',width:'200px'},
        {name: '数量', id: 'num',width:'200px'}
    ];
    $scope.selectListBook=[];
    $scope.selectListBook[0]=dataService.getBookState;
    /*选择房间之后下方显示他的订单具体情况*/
    $scope.chooseBook=function (d) {
        if(d.bookRoomList.length>0){
            $scope.showRoom=true;
            $scope.showRoomCategory=false;
            $scope.bookRoomList=d.bookRoomList;
        }else {
            $scope.showRoomCategory=true;
            $scope.showRoom=false;
            $scope.bookRoomCategoryList=d.bookRoomCategoryList;
        }
    };
    /*未来房态图*/
    $scope.futureRoomState=function () {
        popUpService.pop('futureRoomState');
    };
    $scope.refresh=function () {
        window.location.reload();
    };
    $scope.bookInput=function () {
        popUpService.pop('bookInput');
    }
}]);