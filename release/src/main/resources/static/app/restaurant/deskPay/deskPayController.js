/**
 * Created by Administrator on 2016-09-20.
 * 用处：
 * 餐饮交班审核子报表
 * 营业日报表子报表
 */
App.controller('deskPayController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.deskPayList=popUpService.getParam();
    $scope.deskPayFields=[
        {name:'币种',id:'currency',width:'69px'},
        {name:'额外币种信息',id:'currencyAdd',width:'89px'},
        {name:'金额',id:'payMoney',sum:'true',width:'69px'},
        {name:'时间',id:'doneTime',width:'129px'},
        {name:'结算序列号',id:'ckSerial',width:'159px'},
        {name:'操作员',id:'userId',width:'69px'},
        {name:'营业部门',id:'pointOfSale',width:'109px'}
    ];
}]);