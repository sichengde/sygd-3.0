/**
 * Created by Administrator on 2016-07-21.
 */
App.controller('vipCreateController', ['$scope', 'dataService', 'util', 'LoginService', 'popUpService', 'messageService', 'webService', function ($scope, dataService, util, LoginService, popUpService, messageService, webService) {
    $scope.currencyPayList = [];//sz-pay
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.vipPost = {};
    if(dataService.getOtherParamMapValue("无卡会员")=='y'){
        $scope.idRight=true;
    }
    var p1 = {condition: 'check_in=1'};
    dataService.initData(['refreshCurrencyList', 'refreshVipIdNumberList'], [p1])
        .then(function () {
            /*押金币种*/
            $scope.currencyList = util.objectListToString(dataService.getCurrencyList(), 'currency');
            util.deleteFromArray($scope.currencyList,null,'会员');
            $scope.currencyList.push('转单位');
            $scope.vipPost.currency = '人民币';
        });
    $scope.vipCategoryList = util.objectListToString(dataService.getVipCategoryList(), 'category')
    $scope.vipPost.category = $scope.vipCategoryList[0];
    $scope.readCard = function () {
        var cannotRefresh = true;
        /*判断是不是我们公司发的卡*/
        $scope.idRight = util.getValueByField(dataService.getVipIdNumberList(), 'idNumber', $scope.vipPost.idNumber);
        if (!$scope.idRight) {
            messageService.setMessage({type: 'error', content: '此卡无法识别'});
            popUpService.pop('message');
            cannotRefresh = false;
        }
        /*判断这个卡是否发行过*/
        var var1 = util.getValueByField(dataService.getVipList(), 'idNumber', $scope.vipPost.idNumber);
        if (var1) {
            messageService.setMessage({type: 'error', content: '此卡已经发过'});
            popUpService.pop('message');
            $scope.idRight = null;
            cannotRefresh = false;
        }
        return cannotRefresh;
    };
    $scope.addVip = function () {
        /*对提交的字段进行补充默认*/
        $scope.vipPost.state = '正常';
        $scope.vipPost.doTime = new Date();
        $scope.vipPost.userId = LoginService.getUser();
        $scope.vipPost.currencyPost=$scope.currencyPayList[0];
        webService.post('vipAdd', $scope.vipPost)
            .then(function (r) {
                webService.openReport(r);
                popUpService.close('vipCreate');
            })
    }
}]);