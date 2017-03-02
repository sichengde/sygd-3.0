/**
 * Created by Administrator on 2016-09-13.
 */
App.controller('deskStateController', ['$scope', 'dataService', 'util', 'LoginService', 'popUpService','messageService', function ($scope, dataService, util, LoginService, popUpService,messageService) {
    $scope.menuFields = [
        {name: '菜名', id: 'name', notNull: 'true', width: '200px'},
        {name: '种类', id: 'category', selectId: '0', notNull: 'true', width: '200px', copy: 'true', filter: 'list'},
        {name: '单价', id: 'price', notNull: 'true', width: '200px'},
        {name: '单位', id: 'unit', notNull: 'true', width: '200px'},
        {name: '别名', id: 'alias', width: '200px'},
        {name: '打折', id: 'ifDiscount', selectId: '1', notNull: 'true', width: '200px', filter: 'list'}
    ];
    /*获取折扣列表*/
    dataService.refreshDeskDiscountList();
    /*获取预定信息*/
    dataService.refreshDeskBookList();
    $scope.menuCondition = 'point_of_sale=' + util.wrapWithBrackets(LoginService.getPointOfSale());
    /*弹出明细*/
    $scope.deskIn = function (desk) {
        $scope.$broadcast('desk', desk);
        popUpService.pop('deskDetail', null, null, desk);
    };
    /*点菜*/
    $scope.menuClick = function (food) {
        if(food.sellOut){
            messageService.setMessage({type:'error',content:'此菜品已估清'});
            popUpService.pop('message');
            return;
        }
        food.num = $scope.num;
        $scope.$broadcast('food', food);
    };
    /*侦听子控制器动作*/
    $scope.$on('deskRefresh', function (event, data) {
        $scope.refresh();
    });
    /*退菜*/
    $scope.$on('cancelMenu', function (event, data) {
        $scope.$broadcast('cancelMenu2', data);
    });
    /*赠菜*/
    $scope.$on('freeMenu',function (event, data) {
        $scope.$broadcast('freeMenu2', data);
    });
    $scope.refresh = function () {
        var pointOfSale=LoginService.getPointOfSale();
        dataService.initData(['refreshDeskList'],[{condition:'point_of_sale='+util.wrapWithBrackets(pointOfSale)}])
            .then(function () {
                $scope.deskList = dataService.getDeskList();
            });
    };
    /*预览未来预定*/
    $scope.previewBook=function () {
        $scope.featureDateOpened=true;
    };
    $scope.refresh();
}]);