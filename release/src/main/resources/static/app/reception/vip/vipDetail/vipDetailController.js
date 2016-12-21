App.controller('vipDetailController',['$scope','popUpService',function ($scope,popUpService) {
    $scope.vipDetailList=popUpService.getParam();
    $scope.vipDetailFields=[
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
        {name: '结账序列号', id: 'paySerial'},
        {name: '操作员号', id: 'userId'},
        {name: '已注销', id: 'fatherId',exp:'fatherId?\'是\':\'否\''}
    ]
}]);