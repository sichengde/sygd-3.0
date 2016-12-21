App.controller('saunaInController',['$scope','dataService','util','webService','messageService','popUpService',function ($scope,dataService,util,webService,messageService,popUpService) {
    dataService.refreshSaleCountList({condition:'first_point_of_sale=\'桑拿\''})
        .then(function (r) {
            $scope.categoryList=['全部'].concat(util.objectListToString(r,'name'));
        });
    dataService.refreshSaunaMenuList()
        .then(function (r) {
            $scope.saunaMenuList=r;
        });
    dataService.refreshSaunaUserList();
    /*刷卡确定技师*/
    $scope.readCard=function () {
        var saunaUser=util.getValueByField(dataService.getSaunaUserList(),'idNumber',$scope.idNumber);  
        if(!saunaUser){
            messageService.setMessage({type:'error',content:'此卡无法识别'});
            popUpService.pop('message');
            return;
        }else {
            $scope.saunaUser=saunaUser.name;      
        }
    };
    /*选择手牌号*/
    $scope.chooseRingNumber=function () {
        $scope.chooseMode='ring';
        $scope.ifSwitchNumber=true;
    };
    /*选择数量*/
    $scope.chooseNum=function () {
        $scope.chooseMode='num';
        $scope.ifSwitchNumber=true;
    };
    /*清除选择*/
    $scope.clearInput=function () {
        if($scope.chooseMode=='ring'){
            $scope.ringNumber='';
        }  else {
            $scope.num=1;
        }
        $scope.ifSwitchNumber=false;
    };
    /*确认选择*/
    $scope.commitInput=function () {
        $scope.ifSwitchNumber=false;
        webService.post('checkSaunaRingIn',$scope.ringNumber)
            .then(function (r) {
                if(!r){
                    messageService.setMessage({type:'error',content:'手牌号{'+$scope.ringNumber+'}不存在或者没有开牌'});
                    popUpService.pop('message');
                    $scope.ringNumber='';
                }
            })
    };
    $scope.ringNumber='';
    $scope.num=1;
    /*选择数量/字母*/
    $scope.clickNum=function (num) {
        if($scope.chooseMode=='ring'){
            $scope.ringNumber+=num;
        }  else {
            $scope.num+=num;
        }
    };
    /*选择销售品类别*/
    $scope.chooseMenuCategory=function (category) {
        $scope.menuCategory=category;
        $scope.showMenuCategory=false;
    };
    /*选择销售品*/
    $scope.chooseMenu=function (menu) {
        if($scope.ringNumber==''||$scope.saunaUser==''||!$scope.saunaUser){
            messageService.setMessage({type:'error',content:'请先选择手牌和技师'});
            popUpService.pop('message');
            return;
        }
        if(menu.hover){
            util.deleteFromArray($scope.saunaDetailList,'menuSign',menu.name);
            menu.hover=null;
        }else {
            var saunaDetail={};
            saunaDetail.menuSign=menu.name;
            saunaDetail.ring=$scope.ringNumber;
            saunaDetail.saunaUser=$scope.saunaUser;
            saunaDetail.num=$scope.num;
            $scope.saunaDetailList.push(saunaDetail);
            menu.hover = 'hover';
        }
    };
    /*已选列表*/
    $scope.saunaDetailList=[];
    /*提交明细*/
    $scope.saunaDetailIn=function () {
        if($scope.saunaDetailList.length>0) {
            webService.post('saunaDetailIn', $scope.saunaDetailList)
                .then(function () {
                    window.location.reload();
                    messageService.actionSuccess();
                })
        }
    }
}]);