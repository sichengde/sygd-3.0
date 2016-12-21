/**
 * Created by Administrator on 2016-12-08.
 */
/**
 * Created by Administrator on 2016/7/31 0031.
 */
App.controller('restaurantController', ['$scope', 'dataService', 'webService', '$ionicPopup', 'util', '$ionicHistory','$state', function ($scope, dataService, webService, $ionicPopup, util, $ionicHistory,$state) {
    /*检查有几个营业部门，两个或两个以上需要先进行选择*/
    var pointOfSale = localStorage.getItem('pointOfSale');
    $scope.needChoosePointOfSale = false;
    dataService.refreshPointOfSaleList({condition: 'module=\'餐饮\''})
        .then(function (r) {
            $scope.pointOfSaleList = r;
            if (!pointOfSale) {//没有保存的营业部门才需要选择
                if ($scope.pointOfSaleList.length > 1) {//大于1个营业部门，需要进行选择
                    $scope.needChoosePointOfSale = true;
                } else if ($scope.pointOfSaleList == 1) {//如果只有一个则直接设定
                    pointOfSale = $scope.pointOfSaleList[0].firstPointOfSale;
                    localStorage.setItem('pointOfSale', pointOfSale);
                } else {//其他情况则报错，没有营业部门
                    $ionicPopup.alert({
                        title: '错误',
                        template: '没有定义营业部门'
                    });
                }
            }
        });
    dataService.refreshMenuRemarkList();
    /*准备切换营业部门*/
    $scope.togglePointOfSale = function () {
        $scope.needChoosePointOfSale = !$scope.needChoosePointOfSale;
    };
    /*选择营业部门*/
    $scope.choosePointOfSale = function (p) {
        pointOfSale = p;
        localStorage.setItem('pointOfSale', p);
        $scope.doRefresh();
        $scope.needChoosePointOfSale = false;
    };
    $scope.deskDetail = function (r) {
        webService.redirect('menu',r,true);
    };
    $scope.doRefresh = function () {
        dataService.initData(['refreshDeskList'], [{condition: 'point_of_sale=' + util.wrapWithBrackets(pointOfSale)}])
            .then(function () {
                $scope.deskList = dataService.getDeskList();
                var aaa = $scope.deskList;
                $scope.deskMessage = {};
                var openDesk = 0;
                $scope.deskMessage.totalDesk = $scope.deskList.length;
                var num = $scope.deskMessage.totalDesk;
                if(num <= 30){
                    $scope.dList = util.chunk(aaa, 5);
                }else{
                    $scope.dList = util.chunk(aaa, 6);
                }
                angular.forEach($scope.deskList, function (desk) {
                    if (desk.deskIn) {
                        openDesk++;
                    }
                });
                $scope.deskMessage.openDesk = openDesk;
            });
        $scope.$broadcast('scroll.refreshComplete');
    };
    $scope.doRefresh();


}]);
