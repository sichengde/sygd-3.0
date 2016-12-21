/**
 * Created by Administrator on 2016-06-15.
 */
App.controller('overViewController',['$scope','webService','util','dateFilter',function ($scope,webService,util,dateFilter) {
    var config;
    var canvas;
    $scope.query=function () {
        var p=[$scope.beginTime,$scope.endTime];
        webService.post('consumePerDay',p)
            .then(function (d) {
                config.data.datasets[0].data=util.objectListToStringDuplicate(d,'consume');
                config.data.labels=util.objectListToStringDuplicate(d,'doTime',dateFilter,'MM-dd');
                canvas.update();
            })
    };
    $scope.show=function () {
        $scope.init();
    };
    $scope.init=function () {

        config = {
            type: 'line',
            data: {
                labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
                datasets: [
                    {
                        label: "客房当日发生额",
                        fill: true,
                        lineTension: 0.1,
                        backgroundColor: "rgba(75,192,192,0.4)",
                        borderColor: "rgba(75,192,192,1)",
                        borderCapStyle: 'butt',
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: 'miter',
                        pointBorderColor: "rgba(255,255,251,1)",
                        pointBackgroundColor: "rgba(255,0,0,1)",
                        pointBorderWidth: 1,
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: "rgba(75,192,192,1)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: [65, 59, 80, 81, 56, 55, 40]
                    }
                ]
            },
            options: {
                responsive: true,
                hover: {
                    mode: 'dataset'
                },
                legend: {
                    display: false
                },
                scales: {
                    xAxes: [{
                        display: true,
                        gridLines: {
                            display:false
                        },
                        scaleLabel: {
                            show: true,
                            labelString: 'Month'
                        },
                        ticks:{
                            userCallback: function(dataLabel, index) {
                                return index % 1 === 0 ? dataLabel : '';
                            }
                        }
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            show: true,
                            labelString: 'Value'
                        },
                        gridLines: {
                            display:false
                        },
                        ticks: {
                            suggestedMin: 0
                        }
                    }]
                }
            }
        };

        var ctx = document.getElementById("canvas").getContext("2d");
        canvas=new Chart(ctx, config);
    };
    $scope.$on('$viewContentLoaded', function() {
        $scope.init();
    });
}]);