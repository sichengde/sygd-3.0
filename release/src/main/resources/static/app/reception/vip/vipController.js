/**
 * Created by Administrator on 2016-04-28.
 */
App.controller('vipController', ['$scope', 'popUpService', 'util', 'dataService', function ($scope, popUpService, util, dataService) {
    $scope.vipFields = [{name: '卡类别', id: 'category', width: '8%', selectId: '0',filter:'list'},
        {name: '卡号', id: 'vipNumber', width: '100px', static: 'true'},
        {name: '姓名', id: 'name', width: '100px'},
        {name: '生日', id: 'birthdayTime', width: '150px', date: 'short'},
        {name: '身份证', id: 'cardId', width: '10%'},
        {name: '电话', id: 'phone', width: '7%'},
        {name: '状态', id: 'state', width: '5%', static: 'true',filter:'list',filterInit:'正常'},
        {name: '积分', id: 'score', width: '5%'},
        {name: '余额', id: 'remain', width: '7%', static: 'true'},
        {name: '押金', id: 'deposit', width: '7%', static: 'true'},
        {name: '发卡时间', id: 'doTime', width: '7%', static: 'true',date:'short'},
        {name: '有效时间', id: 'remainTime', width: '7%',date:'short'},
        {name: '工作单位', id: 'workCompany', width: '7%'},
        {name: '备注', id: 'remark', width: '7%'},
        {name: '操作员号', id: 'userId',filter:'list'}
    ];
    $scope.vipHistoryFields=angular.copy($scope.vipFields);
    $scope.vipHistoryFields.push({name:'注销时间',id:'doneTime',desc:'0'});
    $scope.selectListVip = [];
    $scope.vipDetailFields = [
        {name: '卡号', id: 'vipNumber'},
        {name: '操作时间', id: 'doTime'},
        {name: '销售点', id: 'pointOfSale'},
        {name: '消费', id: 'consume',sum:'true'},
        {name: '充值', id: 'pay',exp:'pay>0?pay:null',sum:'true'},
        {name: '退款', id: 'back',exp:'pay<0?pay:null',sum:'true'},
        {name: '抵用', id: 'deserve',sum:'true'},
        {name: '币种', id: 'currency'},
        {name: '描述', id: 'description'},
        {name: '类型', id: 'category'},
        {name: '当前余额', id: 'remain'},
        {name: '结账序列号', id: 'paySerial'},
        {name: '操作员号', id: 'userId'}
    ];
    $scope.vipDetailHistoryFields=$scope.vipDetailFields;
    $scope.debtPayFields = [
        {name: '单位名称', id: 'company'},
        {name: '结账时间', id: 'doneTime', date: 'true'},
        {name: '结账金额', id: 'debtMoney'},
        {name: '币种', id: 'currency'},
        {name: '结账流水号', id: 'paySerial'},
        {name: '离店流水号', id: 'checkOutSerial'},
        {name: '结账种类', id: 'debtCategory'},
        {name: '操作员', id: 'userId'}
    ];
    $scope.checkInHistoryFields=[
        {name:'身份证号',id:'cardId'},
        {name:'证件种类',id:'cardType'},
        {name:'姓名',id:'name'},
        {name:'生日',id:'birthdayTime', date: 'short'},
        {name:'性别',id:'sex'},
        {name:'民族',id:'race'},
        {name:'地址',id:'address'},
        {name:'联系电话',id:'phone'},
        {name:'最近消费',id:'lastTime',filter:'date',desc:'0'}
    ];
    $scope.checkInHistoryLogFields=[
        {name:'来店时间',id:'reachTime'},
        {name:'离店时间',id:'leaveTime'},
        {name:'房号',id:'roomId'},
        {name:'消费',id:'consume',sum:'true'},
        {name: '主账号', exp: 'groupAccount?groupAccount:selfAccount'}
    ];
    dataService.initData(['refreshVipCategoryList'])
        .then(function () {
            $scope.selectListVip[0] = util.objectListToString(dataService.getVipCategoryList(), 'category');
        });
    $scope.selectList = [];
    var refreshVip;
    $scope.refreshVip=function (f) {
        refreshVip=f;
    };
    $scope.refresh = function () {
        refreshVip();
    };
    $scope.clickPop = function (name) {
        popUpService.pop(name);
    };
    /*下边的明细先都不显示*/
    $scope.initConditionVipDetail = 'id=-1';
    $scope.initConditionVipHistoryDetail='id=-1';
    $scope.initConditionDebtPay = 'id=-1';
    $scope.initConditionCheckInHistoryLog='id=-1';
    /*选中某条会员*/
    $scope.chooseVip = function (vip) {
        $scope.initConditionVipDetail = 'vip_number=' + util.wrapWithBrackets(vip.vipNumber);
        $scope.initConditionDebtPay = $scope.initConditionVipDetail + ' and currency != \'会员\'';
    };
    /*选中某条会员历史*/
    $scope.chooseVipHistory = function (vip) {
        $scope.initConditionVipHistoryDetail = 'father_id=' + util.wrapWithBrackets(vip.id);
    };
    /*选中某条客史，下边显示客史明细*/
    $scope.chooseCheckInHistory=function (guest) {
        $scope.initConditionCheckInHistoryLog='card_id = '+util.wrapWithBrackets(guest.cardId);
    }
}]);