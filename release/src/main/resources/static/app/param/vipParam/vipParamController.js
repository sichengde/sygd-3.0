/**
 * Created by Administrator on 2016-07-21.
 */
App.controller('vipParamController',['$scope','dataService','util',function ($scope,dataService,util) {
    $scope.vipCategoryFields = [
        {name: '类别', id: 'category', notNull: 'true',width:'150px'},
        {name: '协议房价', id: 'protocol', notNull: 'true',selectId:'0',width:'350px'}
    ];
    $scope.selectListVipCategory=[];
    dataService.initData(['refreshProtocolList'])
        .then(function () {
            $scope.selectListVipCategory[0]=util.objectListToString(dataService.getProtocolList(),'protocol');
        })
}]);