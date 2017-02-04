/**
 * Created by Administrator on 2016-06-15.
 */
App.controller('userLogInfoController', ['$scope', 'dataService', 'dateFilter', 'util', '$route', function ($scope, dataService, dateFilter, util, $route) {
    var module;
    if ($route.current.params.param == 'reception') {
        module='接待';
        $scope.category = 'reception'
    }else if($route.current.params.param == 'restaurant'){
        module='餐饮';
        $scope.category = 'restaurant';
    }else if($route.current.params.param == 'sauna'){
        module='桑拿';
        $scope.category = 'sauna';
    }
    $scope.userLogFields = [
        {name: '姓名', id: 'userId',  filter: 'list',width:'100px'},
        {name: '操作内容', id: 'action',width:'100px'},
        {name: '模块', id: 'module',  filter: 'list',filterInit:module,width:'100px'},
        {name: '操作类别', id: 'category',  filter: 'list', filterFather: 'module',width:'150px'},
        {name: '操作时间', id: 'doTime',  filter: 'date', desc: '0',width:'150px'}
    ];
    $scope.checkInFields = [
        {name: '房号', id: 'roomId', width: '80px'},
        {name: '房型', id: 'roomCategory', filter: 'list', width: '100px'},
        {name: '结算价格', id: 'finalRoomPrice', width: '100px'},
        {name: '当前消费', id: 'consume', width: '100px'},
        {name: '当前预付', id: 'deposit', width: '100px'},
        {name: '房价协议', id: 'protocol', filter: 'list', width: '150px'},
        {name: '房租方式', id: 'roomPriceCategory', filter: 'list', width: '120px'},
        {name: '早餐人数', id: 'breakfast', width: '80px'},
        {name: '来店时间', id: 'reachTime', filter: 'date', width: '220px'},
        {name: '预离时间', id: 'leaveTime', filter: 'date', width: '220px'},
        {name: '客源', id: 'guestSource', filter: 'list', width: '100px'},
        {name: '特殊要求', id: 'important', width: '100px'},
        {name: '贵宾', id: 'vip', width: '80px'},
        {name: '协议公司', id: 'company', filter: 'list', width: '150px'},
        {name: '团队名称', id: 'groupName', filter: 'list', width: '150px'},
        {name: '开房人员', id: 'userId', width: '100px', filter: 'list', width: '100px'}
    ];
    $scope.checkInIntegrationFields=[
        {name:'房号',id:'roomId'},
        {name:'房型',id:'roomCategory'},
        {name:'房价',id:'finalRoomPrice'},
        {name:'姓名',id:'name'},
        {name:'国籍',id:'country'},
        {name:'单位',id:'company'},
        {name:'来期',id:'reachTime',filterInit:'today',filter: 'date'},
        {name:'离期',id:'leaveTime',filter: 'date'}
    ];
    $scope.initRemark = function (data) {
        var totalForeigner=0;
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            if(item.country&&item.country!='中国'){
                totalForeigner++;
            }
        }
        $scope.checkInIntegrationRemark= '外宾人数:'+totalForeigner;
    };
    $scope.checkOutFields = [
        {name: '主账号', exp: 'groupAccount?groupAccount:selfAccount'},
        {name: '房间号', id: 'roomId'},
        {name: '来店时间', id: 'reachTime', filter: 'date'},
        {name: '离店时间', id: 'checkOutTime', desc: '0', filter: 'date',filterInit:'today'},
        {name: '消费', id: 'consume'},
        {name: '操作员', id: 'userId', filter: 'list'},
        {name: '备注', id: 'remark'}
    ];
    $scope.checkOutRoomFields = [
        {name: '房间号', id: 'roomId'},
        {name: '来店时间', id: 'reachTime'},
        {name: '离店时间', id: 'leaveTime'},
        {name: '姓名', id: 'name'},
        {name: '单位', id: 'company'},
        {name: '客源', id: 'source'},
        {name: '房租方式', id: 'roomPriceCategory'},
        {name: '房价协议', id: 'protocol'}
    ];
    $scope.debtIntegrationFields = [
        {name: '房号', id: 'roomId', width: '70px', filter: 'list'},
        {name: '金额', exp: 'consume*1+deposit*1', width: '70px'},
        {name: '币种', id: 'currency', width: '70px', filter: 'list'},
        {name: '入账时间', id: 'doTime', width: '180px', filter: 'date', desc: '0'},
        {name: '结算时间', id: 'doneTime', width: '180px', filter: 'date'},
        {name: '类型', id: 'category', width: '180px', filter: 'list'},
        {name: '操作员', id: 'userId', width: '180px', filter: 'list'},
        {name: '备注', id: 'remark', width: '200px'}
    ];
    $scope.debtPayFields = [
        {name: '结账时间', id: 'doneTime', date: 'true', width: '120px', filter: 'date', desc: '0'},
        {name: '房号', id: 'roomId',  width: '120px'},
        {name: '结账金额', id: 'debtMoney', width: '100px' ,sum:'true'},
        {name: '币种', id: 'currency', width: '100px', filter: 'list'},
        {name: '单位名称', id: 'company', width: '230px', filter: 'list'},
        {name: '主账号', exp: 'groupAccount?groupAccount:selfAccount', width: '150px'},
        {name: '结账流水号', id: 'paySerial', width: '150px'},
        {name: '离店流水号', id: 'checkOutSerial', width: '150px'},
        {name: '结账类型', id: 'debtCategory', width: '150px', filter: 'list'},
        {name: '操作员', id: 'userId', width: '100px'}
    ];
    $scope.roomShopDetailFields=[
        {name:'商品',id:'item'},
        {name:'类别',id:'category',filter:'list'},
        {name:'单价',id:'price'},
        {name:'数量',id:'num',sum:'true'},
        {name:'合计',id:'totalMoney',sum:'true'},
        {name:'房间',id:'room'},
        {name:'操作员',id:'userId',filter:'list'},
        {name:'操作时间',id:'doTime',filter:'date',desc:'0'}
    ];
    $scope.checkInGuestCondition = 'id=-1';
    $scope.checkOutRoomCondition = 'id=-1';
    $scope.chooseCheckIn = function (d) {
        $scope.checkInGuestCondition = 'room_id=' + util.wrapWithBrackets(d.roomId);
    };
    $scope.chooseCheckOut = function (d) {
        $scope.checkOutRoomCondition = 'check_out_serial=' + util.wrapWithBrackets(d.checkOutSerial);
    };
}]);