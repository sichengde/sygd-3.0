'use strict';
/* angularjs Scroll Glue
 * version 2.1.0
 * https://github.com/Luegg/angularjs-scroll-glue
 * An AngularJs directive that automatically scrolls to the bottom of an element on changes in it's scope.
 */

// Allow module to be loaded via require when using common js. e.g. npm
if(typeof module === "object" && module.exports){
    module.exports = 'luegg.directives';
}
(function(angular, undefined){
    function createActivationState($parse, attr, scope){
        function unboundState(initValue){
            var activated = initValue;
            return {
                getValue: function(){
                    return activated;
                },
                setValue: function(value){
                    activated = value;
                }
            };
        }

        function oneWayBindingState(getter, scope){
            return {
                getValue: function(){
                    return getter(scope);
                },
                setValue: function(){}
            };
        }

        function twoWayBindingState(getter, setter, scope){
            return {
                getValue: function(){
                    return getter(scope);
                },
                setValue: function(value){
                    if(value !== getter(scope)){
                        scope.$apply(function(){
                            setter(scope, value);
                        });
                    }
                }
            };
        }

        if(attr !== ""){
            var getter = $parse(attr);
            if(getter.assign !== undefined){
                return twoWayBindingState(getter, getter.assign, scope);
            } else {
                return oneWayBindingState(getter, scope);
            }
        } else {
            return unboundState(true);
        }
    }

    function createDirective(module, attrName, direction){
        module.directive(attrName, ['$parse', '$window', '$timeout', function($parse, $window, $timeout){
            return {
                priority: 1,
                restrict: 'A',
                link: function(scope, $el, attrs){
                    var el = $el[0],
                        activationState = createActivationState($parse, attrs[attrName], scope);

                    function scrollIfGlued() {
                        if(activationState.getValue() && !direction.isAttached(el)){
                            direction.scroll(el);
                        }
                    }

                    function onScroll() {
                        activationState.setValue(direction.isAttached(el));
                    }

                    scope.$watch(scrollIfGlued);

                    $timeout(scrollIfGlued, 0, false);

                    $window.addEventListener('resize', scrollIfGlued, false);

                    $el.on('scroll', onScroll);


                    // Remove listeners on directive destroy
                    $el.on('$destroy', function() {
                        $el.unbind('scroll', onScroll);
                    });

                    scope.$on('$destroy', function() {
                        $window.removeEventListener('resize', scrollIfGlued, false);
                    });
                }
            };
        }]);
    }

    var bottom = {
        isAttached: function(el){
            // + 1 catches off by one errors in chrome
            return el.scrollTop + el.clientHeight + 1 >= el.scrollHeight;
        },
        scroll: function(el){
            el.scrollTop = el.scrollHeight;
        }
    };

    var top = {
        isAttached: function(el){
            return el.scrollTop <= 1;
        },
        scroll: function(el){
            el.scrollTop = 0;
        }
    };

    var right = {
        isAttached: function(el){
            return el.scrollLeft + el.clientWidth + 1 >= el.scrollWidth;
        },
        scroll: function(el){
            el.scrollLeft = el.scrollWidth;
        }
    };

    var left = {
        isAttached: function(el){
            return el.scrollLeft <= 1;
        },
        scroll: function(el){
            el.scrollLeft = 0;
        }
    };

    var module = angular.module('luegg.directives', []);

    createDirective(module, 'scrollGlue', bottom);
    createDirective(module, 'scrollGlueTop', top);
    createDirective(module, 'scrollGlueBottom', bottom);
    createDirective(module, 'scrollGlueLeft', left);
    createDirective(module, 'scrollGlueRight', right);
}(angular));
//agGrid.LicenseManager.setLicenseKey("ag-Grid_EvaluationLicense_100Devs27_January_2018__MTUxNzAxMTIwMDAwMA==8c9dff17620c1f17c7c34cb69061acb6");

// get ag-Grid to create an Angular module and register the ag-Grid directive
//agGrid.initialiseAgGridWithAngular1(angular);

// create your module with ag-Grid as a dependency
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
        }).when('/breakfast', {//早餐刷卡
            templateUrl: 'reception/breakfast/breakfast.html',
            controller: 'breakfastController'
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