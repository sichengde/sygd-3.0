/**
 * Created by Administrator on 2016-04-25.
 */
App.directive('szTab', [function () {
    return {
        restrict: 'E',
        transclude: true,
        scope:{
            chooseItem:'&'
        },
        controller: ['$scope', function ($scope) {
            $scope.panes = [];
            $scope.select = function (pane) {
                angular.forEach($scope.panes, function (value) {
                    value.selected = false;
                });
                pane.selected = true;
                $scope.chooseItem({d:pane.title});
                pane.init();
                angular.element("#iframeReport").css("display","none");
            };

            this.addPane = function (pane) {
                if ($scope.panes.length === 0) {
                    $scope.select(pane);
                }
                $scope.panes.push(pane);
            };
        }],
        templateUrl: '../directive/szTab.html'
    };

}])
    .directive('szPane', [function () {
        return {
            require: '^^szTab',
            restrict: 'E',
            transclude: true,
            scope: {
                title: '@',
                init:'&'
            },
            link: function (scope, element, attr, tabsCtrl) {
                tabsCtrl.addPane(scope);
            },
            templateUrl: '../directive/szPane.html'
        };
    }]);
