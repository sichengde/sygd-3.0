App.controller('saunaOutController', ['$scope','popUpService','util','webService', function ($scope,popUpService,util,webService) {
    var post=popUpService.getParam();
    $scope.totalConsume=0.0;
    if(post.groupSerial){//团队结算
        webService.post('saunaDetailGet',{condition:'sauna_group_serial='+util.wrapWithBrackets(post.groupSerial),orderByList:['ring']})
            .then(function (r) {
               $scope.saunaDetailList=r;
                for (var i = 0; i < r.length; i++) {
                    var obj = r[i];
                    $scope.totalConsume+=obj.num*obj.price;
                }
                $scope.currencyPayList[0].money = $scope.totalConsume;
            });
    }else {
        var p='';
        for (var i = 0; i < post.ringList.length; i++) {
            var ring = post.ringList[i];
            p+=' ring = '+util.wrapWithBrackets(ring)+' or '
        }
        p=p.substring(0,p.length-4);
        webService.post('saunaDetailGet',{condition:p})
            .then(function (r) {
                $scope.saunaDetailList=r;
                for (var i = 0; i < r.length; i++) {
                    var obj = r[i];
                    $scope.totalConsume+=obj.num*obj.price;
                }
                $scope.currencyPayList[0].money = $scope.totalConsume;//sz-pay
            });
    }
    $scope.saunaDetailFields=[
        {name:'手牌号',id:'ring',asc:'0'},
        {name:'项目',id:'saunaMenu'},
        {name:'单价',id:'price'},
        {name:'数量',id:'num'},
        {name:'小计',id:'total',exp:'price*num',sum:'true'}
    ];
    /*初始化分单数组，默认不分担，值为1，只有一个币种*/
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    /*确认提交结牌*/
    $scope.saunaOut=function () {
        post.currencyPayList=$scope.currencyPayList;
        webService.post('saunaOut',post)
            .then(function (r) {
                webService.openReport(r);
                popUpService.close('saunaOut');
            })
    }
}]);