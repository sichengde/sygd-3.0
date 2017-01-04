'use strict';
var App = angular.module("appMain", ['ngRoute', 'ui.bootstrap','angular-drag','luegg.directives','appIp']);
App.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/login', {//-------------------------------------登录模块-------------------------------------登录
            templateUrl: 'common/login/login.html',
            controller: 'LoginController'
        }).when('/reception', {//-------------------------------------客房模块-------------------------------------房态图
            templateUrl: 'reception/reception.html',
            controller: 'ReceptionController'
        }).when('/book/:mode', {//预定管理
            templateUrl: 'reception/book/book.html',
            controller: 'BookController'
        }).when('/guestInGroup', {//团队开房
            templateUrl: 'reception/guestInGroup/guestInGroup.html',
            controller: 'GuestInGroupController'
        }).when('/vip', {//会员管理
            templateUrl: 'reception/vip/vip.html',
            controller: 'vipController'
        }).when('/report', {//报表系统
            templateUrl: 'reception/report/report.html',
            controller: 'reportController'
        }).when('/joinRoom', {//联房管理
            templateUrl: 'reception/joinRoom/joinRoom.html',
            controller: 'joinRoomController'
        }).when('/company', {//协议单位
            templateUrl: 'reception/company/company.html',
            controller: 'companyController'
        }).when('/companyDebt/:mode', {//应收应付
            templateUrl: 'reception/companyDebt/companyDebt.html',
            controller: 'companyDebtController'
        }).when('/retail', {//商品零售
            templateUrl: 'reception/retail/retail.html',
            controller: 'retailController'//******************系统维护-客房参数******************
        }).when('/roomStateManager', {//房态管理
            templateUrl: 'reception/roomStateManager/roomStateManager.html'
        }).when('/hotelParam', {//-------------------------------------系统维护-------------------------------------酒店参数
            templateUrl:'param/hotelParam/hotelParam.html',
            controller:'UserController'
        }).when('/roomParam', {//客房参数，因为包含其他参数module的初始化，所以用了动态参数
            templateUrl: 'param/roomParam/roomParam.html',
            controller: 'paramList1Controller'
        }).when('/restaurantParam', {//餐饮参数
            templateUrl: 'param/restaurantParam/restaurantParam.html',
            controller: 'restaurantParamController'
        }).when('/saunaParam', {//桑拿参数
            templateUrl: 'param/saunaParam/saunaParam.html',
            controller: 'saunaParamController'
        }).when('/storageParam', {//库存参数
            templateUrl: 'param/storageParam/storageParam.html',
            controller: 'storageParamController'
        }).when('/vipParam', {//会员参数
            templateUrl: 'param/vipParam/vipParam.html',
            controller: 'vipParamController'
        }).when('/debtTool', {//账务工具
            templateUrl: 'param/debtTool/debtTool.html',
            controller: 'debtToolController'
        }).when('/realState', {//-------------------------------------经理查询-------------------------------------实时状态
            templateUrl: 'manager/realState/realState.html',
            controller: 'realStateController'
        }).when('/guestInfo', {//宾客信息
            templateUrl: 'manager/guestInfo/guestInfo.html',
            controller: 'guestInfoController'
        }).when('/dataParse', {//数据分析
            templateUrl: 'manager/dataParse/dataParse.html',
            controller: 'dataParseController'
        }).when('/userLogInfo/:param', {//操作员日志
            templateUrl: 'manager/userLogInfo/userLogInfo.html',
            controller: 'userLogInfoController'
        }).when('/restaurant', {//-------------------------------------餐饮模块-------------------------------------盘态图
            templateUrl: 'restaurant/deskState/deskState.html',
            controller: 'deskStateController'
        }).when('/buffet', {//餐饮自助餐模块
            templateUrl: 'restaurant/buffet/buffet.html',
            controller:'buffetController'
        }).when('/outSale', {//餐饮外卖模块
            templateUrl: 'restaurant/outSale/outSale.html',
            controller:'outSaleController'
        }).when('/deskReport', {//餐饮报表系统
            templateUrl: 'restaurant/deskReport/deskReport.html',
            controller:'deskReportController'
        }).when('/otherTools', {//餐饮其他工具
            templateUrl: 'restaurant/deskOtherTools/deskOtherTools.html',
            controller:'deskOtherToolsController'
        }).when('/sauna',{//-------------------------------------桑拿模块-------------------------------------盘态图
            templateUrl:'sauna/saunaState/saunaState.html',
            controller:'saunaStateController'
        }).when('/saunaIn', {
            templateUrl: 'sauna/saunaIn/saunaIn.html',
            controller:'saunaInController'
        }).when('/saunaReport', {
            templateUrl: 'sauna/saunaReport/saunaReport.html',
            controller:'saunaReportController'
        }).when('/storageManager', {//-------------------------------------库存模块-------------------------------------库存管理
            templateUrl: 'storage/storageManager/storageManager.html',
            controller:'storageManagerController'
        }).when('/storageReport', {//-------------------------------------库存模块-------------------------------------报表查询
            templateUrl: 'storage/storageReport/storageReport.html',
            controller:'storageReportController'
        });
    }]);