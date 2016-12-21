(function (angular) {
    'use strict';
    angular.module('docsTabsExample', [])
        .directive('myTabs', function () {
            return {
                restrict: 'E',
                transclude: true,
                scope: {},
                controller: ['$scope', function ($scope) {
                    $scope.panes = [];
                    $scope.select = function (pane) {
                        angular.forEach($scope.panes, function (pane) {
                            pane.aaa = false;
                        });
                        pane.aaa = true;
                    };

                    this.addMyPane = function (pane) {
                        if ($scope.panes.length === 1) {
                            $scope.select(pane);
                        }
                        $scope.panes.push(pane);
                    };
                }],
                templateUrl: 'index2.html'
            };
        })
        .directive('myPane', function () {
            return {
                require: '^^myTabs',
                restrict: 'E',
                transclude: true,
                scope: {
                    title: '@'
                },
                link: function (scope, element, attrs, tabsCtrl) {
                    var a=attrs.aa;
                    tabsCtrl.addMyPane(scope);
                },
                templateUrl: 'index3.html'
            };
        });
})(window.angular);

/*
 Copyright 2016 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license
 */