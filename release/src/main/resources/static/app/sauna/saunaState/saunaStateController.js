App.controller('saunaStateController', ['$scope', 'dataService', 'webService', 'messageService', 'popUpService', 'util', function ($scope, dataService, webService, messageService, popUpService, util) {
    $scope.ringIn = 0;
    $scope.todayTotal = 0;
    $scope.refresh = function () {
        dataService.refreshSaunaRingList({orderByList: ['sex', 'number']})
            .then(function (r) {
                $scope.saunaRingList = r;
                for (var i = 0; i < $scope.saunaRingList.length; i++) {
                    var saunaRing = $scope.saunaRingList[i];
                    if (saunaRing.saunaIn) {
                        $scope.ringIn++;
                    }
                }
            });
        webService.get('saunaTodayTotal')
            .then(function (r) {
                $scope.todayTotal = r;
            });
        $scope.ringNumber = '';
    };
    $scope.refresh();
    $scope.ringSexList = ['全部'].concat(dataService.getSexList);
    $scope.ringStateList = ['全部'].concat(dataService.saunaRingState);
    $scope.ringState = $scope.ringSex = '全部';
    /*开牌*/
    $scope.saunaIn = function () {
        var post = parseRingIn();
        if (!post) {
            return
        }
        webService.post('saunaIn', post)
            .then(function () {
                $scope.refresh();
                messageService.actionSuccess();
            })
    };
    /*折扣开牌*/
    $scope.saunaInSale = function () {
        var post = parseRingIn();
        if (!post) {
            return
        }
        popUpService.pop('saunaInSale', null, $scope.refresh, post);
    };
    /*检查开牌手牌*/
    function parseRingIn() {
        var ringNumberList = $scope.ringNumber.split(',');
        var post = {};
        post.saunaInRowList = [];
        /*检查手牌号是否存在，正常的话生成浴资键值对*/
        for (var i = 0; i < ringNumberList.length; i++) {
            var ringNumber = ringNumberList[i];
            var saunaRing = checkRingExist(ringNumber);
            if (!saunaRing) {
                return false;
            }
            if (saunaRing.state == '占用' || saunaRing.state == '维修') {
                messageService.setMessage({type: 'error', content: '手牌号{' + ringNumber + '}处于占用或是维修'});
                popUpService.pop('message');
                return false;
            }
            var menu = '男浴资';
            if (saunaRing.sex == '女') {
                menu = '女浴资';
            }
            post.saunaInRowList.push({ringNumber: ringNumber, menu: menu});
        }
        return post;
    }

    /*点击样式，统一团队*/
    $scope.saunaRing1Click = function (saunaRing) {
        if (saunaRing.saunaIn) {
            angular.forEach($scope.saunaRingList, function (item) {
                item.hover = null;
                if (saunaRing.saunaIn.saunaGroupSerial) {
                    if (item.saunaIn) {
                        if (item.saunaIn.saunaGroupSerial == saunaRing.saunaIn.saunaGroupSerial) {
                            item.hover = 'hover';
                        }
                    }
                }
            });
            saunaRing.hover = 'hover';
        }
        else {
            angular.forEach($scope.saunaRingList, function (item) {
                item.hover = null;
            })
        }
    };
    /*手动结算*/
    $scope.saunaOut = function () {
        var ringNumberList = $scope.ringNumber.split(',');
        var post = {};
        post.ringList = [];
        if (ringNumberList.length > 1) {//多个手牌结算
            messageService.setMessage({type: 'alert', content: '您选择了手动连牌，系统不会检测你开牌时的连牌情况'});
            popUpService.pop('message');
            for (var i = 0; i < ringNumberList.length; i++) {
                var ringNumber = ringNumberList[i];
                var saunaRing = checkRingExist(ringNumber);
                if (!saunaRing) {
                    return false;
                }
                if (saunaRing.state != '占用') {
                    messageService.setMessage({type: 'error', content: '手牌号{' + ringNumber + '}不处于开牌状态'});
                    popUpService.pop('message');
                    return false;
                }
                post.ringList.push(ringNumber);
                popUpService.pop('saunaOut', null, $scope.refresh, post);
            }
        } else {
            /*检查手牌号是否存在，正常的话生成浴资键值对*/
            var ringNumber = $scope.ringNumber;
            var saunaRing = checkRingExist(ringNumber);
            if (!saunaRing) {
                return false;
            }
            if (saunaRing.state != '占用') {
                messageService.setMessage({type: 'error', content: '手牌号{' + ringNumber + '}不处于开牌状态'});
                popUpService.pop('message');
                return false;
            }
            post.ringList.push(ringNumber);
            if (saunaRing.saunaIn.saunaGroupSerial) {
                messageService.setMessage({content: '是否结算同组手牌？'});
                messageService.actionChoose()
                    .then(function () {
                        post.groupSerial = saunaRing.saunaIn.saunaGroupSerial;
                        post.ringList = [];
                        for (var i = 0; i < $scope.saunaRingList.length; i++) {
                            var obj = $scope.saunaRingList[i];
                            if (obj.saunaIn) {
                                if (obj.saunaIn.saunaGroupSerial == saunaRing.saunaIn.saunaGroupSerial) {
                                    post.ringList.push(obj.number);
                                }
                            }
                        }
                        popUpService.pop('saunaOut', null, $scope.refresh, post);
                    }, function () {
                        popUpService.pop('saunaOut', null, $scope.refresh, post);
                    })
            } else {
                popUpService.pop('saunaOut', null, $scope.refresh, post);
            }
        }
    };
    function checkRingExist(ringNumber) {
        if (ringNumber == '') {
            messageService.setMessage({type: 'error', content: '请不要以逗号结尾或出现连续两个逗号'});
            popUpService.pop('message');
            return false;
        }
        var saunaRing = util.getValueByField($scope.saunaRingList, 'number', ringNumber);
        if (!saunaRing) {
            messageService.setMessage({type: 'error', content: '手牌号{' + ringNumber + '}不存在'});
            popUpService.pop('message');
            return false;
        }
        return saunaRing;
    }

    /*双击把手牌号选上去*/
    $scope.pushIn = function (number) {
        if ($scope.ringNumber == '') {
            $scope.ringNumber = number;
        } else {
            $scope.ringNumber += ',' + number;
        }
    }
}]);