(function(angular) {
    'use strict';
    angular.module('docsTransclusionDirective', [])
        .controller('Controller', ['$scope', function($scope) {
            $scope.name = 'Tobias';
            $scope.aaa="aaa";
            $scope.array=[1,2,3];
        }])
        .service('Service',function () {
            var scope;
            return {
                init:function (s) {
                    scope=s;
                }
            }
        })
        .directive('myDialog', function() {
            return {
                restrict: 'A',
                scope:{},
                controller:['$scope', function ($scope) {
                    $scope.zxc='zxc';
                }],
                template: '<div class="alert">aaa{{zxc}}</div>',
            };
        });
})(window.angular);

/*
 Copyright 2016 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license
 */