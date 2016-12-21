// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in service.js
// 'starter.controllers' is found in controllers.js
var App = angular.module('appMobile', ['ionic','appIp'])
    .config(['$stateProvider','$urlRouterProvider','$ionicConfigProvider',function ($stateProvider, $urlRouterProvider, $ionicConfigProvider) {

        // Ionic uses AngularUI Router which uses the concept of states
        // Learn more here: https://github.com/angular-ui/ui-router
        // Set up the various states which the app can be in.
        // Each state's controller can be found in controllers.js
        $stateProvider
            .state('tabsController', {
                url: '/app',
                templateUrl: 'templates/tabsController.html',
                abstract: true
            })
            .state('login', {
                url: '/login',
                templateUrl: 'login/login.html'
            })
            .state('tabsController.roomState', {
                url: '/roomState',
                views: {
                    'tab1': {
                        templateUrl: 'roomState/roomState.html'
                    }
                }
            })
            .state('tabsController.roomDetail', {
                url: '/roomDetail',
                views: {
                    'tab1': {
                        templateUrl: 'roomDetail/roomDetail.html'
                    }
                }
            })
            .state('tabsController.checkOutDetailReport', {
                url: '/checkOutDetailReport',
                views: {
                    'tab2': {
                        templateUrl: 'checkOutDetailReport/checkOutDetailReport.html',
                        controller: 'checkOutDetailReportControllerM'
                    }
                }
            })
            .state('tabsController.exchangeUserReport', {
                url: '/exchangeUserReport',
                views: {
                    'tab3': {
                        templateUrl: 'exchangeUserReport/exchangeUserReport.html',
                        controller: 'exchangeUserReportControllerM'
                    }
                }
            });

        $urlRouterProvider.otherwise('/login')

        $ionicConfigProvider.tabs.position('bottom');
    }])
    .run(['$ionicPlatform',function ($ionicPlatform) {
        $ionicPlatform.ready(function () {
            // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
            // for form inputs)
            if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
                cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
                cordova.plugins.Keyboard.disableScroll(true);
            }
            if (window.StatusBar) {
                // org.apache.cordova.statusbar required
                StatusBar.styleDefault();
            }
        });
    }]);
