App.factory('echartService', ['util', function (util) {
    /**
     * 历史同期对比
     * @param rowList 第一个数组
     * @param historyList 历史数组
     * @param xAxis 比较的x轴域名
     * @param content 比较的内容
     */
    function generateChartCompare(rowList, historyList, xAxis, content) {
        var xAxisData = [];
        var average = [];
        var averageHistory = [];
        for (var i = 0; i < rowList.length; i++) {
            var row = rowList[i];
            xAxisData.push(row[xAxis]);
            average.push(row[content]);
            /*查询该房类的历史同期*/
            var rowHistory = util.getValueByField(historyList, xAxis, row[xAxis]);
            if (rowHistory) {
                averageHistory.push(rowHistory[content]);
                rowHistory.same = true;//说明这个类已经有了，如果没有的话则要再添加(当前时间段该房类没卖，但历史同期卖过)
            } else {
                averageHistory.push(0);
            }
        }
        for (var i = 0; i < historyList.length; i++) {
            var rowHistory = historyList[i];
            if (!rowHistory.same) {
                xAxisData.push(rowHistory.category);
                average.push(0);
                averageHistory.push(rowHistory[content]);
            }
        }
        var option = {
            title: {
                text: '历史同期对比'
            },
            tooltip: {},
            toolbox: {
                feature: {
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data: ['总计房费', '历史同期']
            },
            xAxis: {
                data: xAxisData
            },
            yAxis: {},
            series: [{
                name: '总计房费',
                type: 'bar',
                data: average
            }, {
                name: '历史同期',
                type: 'bar',
                data: averageHistory
            }]
        };
        var szChart = echarts.init(document.getElementById('szChart'));//图表
        szChart.setOption(option);
    }

    /**
     * 一个数组多字段对比
     * @param title 标题
     * @param legend
     * @param xAxisData
     * @param series
     */
    function generateChartStd(title, legend, xAxisData, series) {
        var option = {
            title: {
                text: title
            },
            tooltip: {},
            toolbox: {
                feature: {
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data: legend
            },
            xAxis: {
                data: xAxisData
            },
            yAxis: {},
            series: series
        };
        var szChart = echarts.init(document.getElementById('szChart'));//图表
        szChart.setOption(option);
    }

    /**
     * 生成时间轴对比
     */
    function generateChartTimeLine(title,list1,list2,show) {
        var date;
        var data1=[];
        for (var i = 0; i < list1.length; i++) {
            var d1 = list1[i];
            date=new Date(d1.date);
            data1.push({value:[
                [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('/'),
                d1.money
            ]})
        }
        var data2=[];
        for (var i = 0; i < list2.length; i++) {
            var d2 = list2[i];
            date=new Date(d2.date);
            data2.push({value:[
                [date.getFullYear()+1, date.getMonth() + 1, date.getDate()].join('/'),
                d2.money
            ]})
        }
        var option = {
            title: {
                text: title
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    animation: false
                }
            },
            toolbox: {
                feature: {
                    saveAsImage: {show: true}
                }
            },
            legend: {
                data:['当日累计','历史同期']
            },
            xAxis: {
                type: 'time',
                splitLine: {
                    show: false
                }
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%'],
                splitLine: {
                    show: false
                }
            },
            series: [{
                name: '当日累计',
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: data1
            },{
                name: '历史同期',
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: data2
            }]
        };
        var szChart = echarts.init(document.getElementById('szChart'));//图表
        szChart.setOption(option);
        if(show) {
            var ctBox = angular.element(".chartBox");
            ctBox.toggleClass("chartBoxTop");
        }
    }

    return {
        generateChartCompare: generateChartCompare,
        generateChartTimeLine: generateChartTimeLine,
        generateChartStd: generateChartStd
    }
}]);