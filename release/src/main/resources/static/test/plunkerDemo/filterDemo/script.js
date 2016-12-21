(function(angular) {
    'use strict';
    angular.module('myReverseFilterApp', [])
        .filter('reverse', function() {
            return function(input, uppercase) {
                input = input || '';
                var out = "";
                for (var i = 0; i < input.length; i++) {
                    out = input.charAt(i) + out;
                }
                // conditional based on optional argument
                if (uppercase) {
                    out = out.toUpperCase();
                }
                return out;
            };
        })
        .controller('MyController', ['$scope', 'reverseFilter', function($scope, reverseFilter) {
            $scope.greeting = 'hello';
            $scope.filteredGreeting = reverseFilter($scope.greeting);
        }]);
})(window.angular);

/*
 Copyright 2016 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license
 */