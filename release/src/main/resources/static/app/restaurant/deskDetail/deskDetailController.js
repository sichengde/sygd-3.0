/**
 * Created by Administrator on 2016-09-13.
 * * 2016-10-09更新：新点的菜品可以修改数量，之前的不可以修改，（因为厨打只判断新增的，修改之前菜品数量厨打程序无法出单子）
 */
App.controller('deskDetailController', ['$scope', 'popUpService', 'dataService', 'util', 'webService', 'LoginService', 'messageService', 'host', '$q', function ($scope, popUpService, dataService, util, webService, LoginService, messageService, host, $q) {
    $scope.desk = popUpService.getParam();//第一次点击时页面刚加载因此无法接收广播事件
    /*初始化分单数组，默认不分担，值为1，只有一个币种*/
    $scope.currencyPayList = [];
    $scope.currencyPayList[0] = {};//使用sz-pay必须在父控制器声明这两个变量
    $scope.deskDetailFields = [
        {name: '菜品', id: 'foodName', static: '!tempFood', width: '54px'},
        {name: '数量', id: 'num', static: '!needInsert', width: '54px'},
        {name: '单价', id: 'price', static: '!tempFood', width: '54px'},
        {name: '单位', id: 'unit', static: '!tempFood', width: '54px'},
        {name: '小计', id: 'num*price', exp: 'num*price', static: 'true', width: '54px'},
        {name: '折扣', id: 'ifDiscount', boolean: true, static: '!tempFood', width: '54px'},
        {name: '等叫', id: 'waitCall', boolean: true, width: '54px'},
        {name: '叫起', id: 'callUp', boolean: true, width: '54px'},
        {name: '备注', id: 'remark', width: '54px'}
    ];
    $scope.deskDiscountList = dataService.getDeskDiscountList();
    $scope.deskDiscount = $scope.deskDiscountList[0];
    var deskDetailListBackUp;//备份之前点的菜，看看有没有修改数量
    var pointOfSale = ' and point_of_sale = ' + util.wrapWithBrackets(LoginService.getPointOfSale());
    /*初始化已点菜品*/
    function initDeskDetail() {
        var p = {condition: 'desk=' + util.wrapWithBrackets($scope.desk.name) + pointOfSale};
        dataService.initData(['refreshDeskDetailList'], [p])
            .then(function () {
                $scope.deskDetailAddList = dataService.getDeskDetailList();
                deskDetailListBackUp = angular.copy($scope.deskDetailAddList);
                calculateTotalMoney();
            });
    }

    initDeskDetail();
    /*监听主界面点选其他桌台*/
    $scope.$on('desk', function (event, data) {
        $scope.desk = data;
        initDeskDetail();
    });
    /*侦听主界面点菜*/
    $scope.$on('food', function (event, data) {
        var deskDetail = {};
        if (data.name == '临时菜') {//临时菜增加一个属性，使得可以编辑菜名数量
            deskDetail.foodName = '';
            deskDetail.tempFood = true;
        } else {
            deskDetail.foodName = data.name;
        }
        deskDetail.num = data.num;
        deskDetail.desk = $scope.desk.name;
        deskDetail.price = data.price;
        deskDetail.category = data.category;
        deskDetail.pointOfSale = LoginService.getPointOfSale();
        deskDetail.needInsert = true;
        deskDetail.foodSign = deskDetail.foodName;
        deskDetail.foodSet = data.foodSet;
        deskDetail.ifDiscount = data.ifDiscount;
        deskDetail.cookRoom = data.cookRoom;
        deskDetail.cargo = data.cargo;
        $scope.deskDetailAddList.push(deskDetail);
    });
    /*监听主界面退菜*/
    $scope.$on('cancelMenu2', function (event, data) {
        var deskDetail = {};
        deskDetail.foodName = data.foodName + '/退菜';
        deskDetail.num = -data.num;
        deskDetail.desk = $scope.desk.name;
        deskDetail.price = data.price;
        deskDetail.category = data.category;
        deskDetail.pointOfSale = LoginService.getPointOfSale();
        deskDetail.needInsert = true;
        deskDetail.foodSign = data.foodName;
        deskDetail.foodSet = data.foodSet;
        deskDetail.ifDiscount = data.ifDiscount;
        deskDetail.cookRoom = data.cookRoom;
        deskDetail.cargo = data.cargo;
        $scope.deskDetailAddList.push(deskDetail);
    });
    /*选择折扣*/
    $scope.deskDiscountChange = function () {
        calculateTotalMoney();
    };
    /*读取预定信息*/
    $scope.readDeskBook = function () {
        popUpService.pop('readDeskBook');
    };
    /*子页面选择预订信息*/
    $scope.$on('chooseDeskBook', function (event, data) {
        $scope.deskBook = data;
        $scope.payRemark = '订金：';
        for (var i = 0; i < $scope.deskBook.bookMoneyList.length; i++) {
            var bookMoney = $scope.deskBook.bookMoneyList[i];
            $scope.payRemark += bookMoney.currency + '/' + bookMoney.subscription;//币种/金额
            for (var j = 0; j < $scope.currencyPayList.length; j++) {
                var currencyPay = $scope.currencyPayList[j];
                if (currencyPay.currency == bookMoney.currency) {//币种相同，开始分析
                    var diff = currencyPay.money - bookMoney.subscription;
                    if (diff > 0) {
                        $scope.payRemark += '，补交金额/' + diff;
                    } else {
                        $scope.payRemark += '，退回押金/' + Math.abs(diff);
                    }
                    break;
                }
            }
            if (j == $scope.currencyPayList.length) {//没有相同的，那么显示找回
                $scope.payRemark += ',退回押金/' + bookMoney.subscription;
            }
        }
    });
    /*改变实收款*/
    $scope.changeTotalConsume = function () {
        if ($scope.currencyPayList.length = 1) {
            $scope.currencyPayList[0].money = $scope.totalConsume;
        }
    };
    /*计算当前总金额，通过菜品和折扣*/
    function calculateTotalMoney() {
        $scope.totalConsume = 0;
        for (var i = 0; i < $scope.deskDetailAddList.length; i++) {
            //noinspection JSDuplicatedDeclaration
            var deskDetail = $scope.deskDetailAddList[i];
            if (deskDetail.ifDiscount) {//判断该菜品是否参与折扣
                $scope.totalConsume += deskDetail.num * deskDetail.price * $scope.deskDiscount.discountValue;
            } else {
                $scope.totalConsume += deskDetail.num * deskDetail.price;
            }
        }
        if (dataService.getOtherParamMapValue('结算精度') == '去零') {
            $scope.totalConsume = parseInt($scope.totalConsume);
        } else if (dataService.getOtherParamMapValue('结算精度') == '四舍五入') {
            $scope.totalConsume = Math.round($scope.totalConsume);
        }
        $scope.currencyPayList[0].money = $scope.totalConsume;
        $scope.beforePrice = $scope.totalConsume;
    }

    /*暂记*/
    $scope.deskDetailAction = function () {
        if (!$scope.desk.deskIn) {
            $scope.deskDetailAddList[0].people = 1;
        } else {
            $scope.deskDetailAddList[0].people = $scope.desk.deskIn.num;
        }
        /*提交检查，退菜数量不能大于现有菜品之和*/
        for (var i = 0; i < $scope.deskDetailAddList.length; i++) {
            var deskDetail = $scope.deskDetailAddList[i];
            if (deskDetail.needInsert) {//只校验新增的即可
                if (deskDetail.num < 0) {//小于0说明是退菜
                    var totalNum = 0;
                    angular.forEach($scope.deskDetailAddList, function (item) {
                        if (item.foodSign == deskDetail.foodSign && item != deskDetail) {
                            totalNum += item.num * 1;
                        }
                    });
                    if (-deskDetail.num > totalNum) {//退菜数量比现有的还大
                        messageService.setMessage({type: 'error', content: '退菜数量不能大于现有菜品之和'});
                        popUpService.pop('message');
                        return $q(function (resolve) {
                            return resolve();
                        });
                    }
                }
            }
            /*判断是不是临时菜，是的话菜名前边加上临时菜三个字*/
            if (deskDetail.tempFood) {
                deskDetail.foodSign = '临时-' + deskDetail.foodName;
                deskDetail.foodName = '临时-' + deskDetail.foodName;
            }
        }
        return webService.post('deskDetailAction', $scope.deskDetailAddList)
            .then(function () {
                initDeskDetail();
                /*重新获得当前窗口的desk对象*/
                var p2 = {condition: 'name=' + util.wrapWithBrackets($scope.desk.name) + pointOfSale};
                dataService.refreshDeskList(p2)
                    .then(function () {
                        $scope.desk = dataService.getDeskList()[0];
                    });
                $scope.$emit('deskRefresh');
                messageService.actionSuccess();
                /*switch (r) {
                 case 0:
                 messageService.actionSuccess();
                 break;
                 case 1:
                 messageService.setMessage({type: 'error', content: '厨房打印机不通，菜已点，但后厨没有打印'});
                 popUpService.pop('message');
                 break;
                 case 2:
                 messageService.setMessage({type: 'error', content: '传菜打印机不通，菜已点，但后厨没有打印'});
                 popUpService.pop('message');
                 break;
                 case 3:
                 messageService.setMessage({type: 'error', content: '厨房打印机与传菜打印机都不通，菜已点，但后厨没有打印'});
                 popUpService.pop('message');
                 break;
                 }*/
            });
    };
    /*结账*/
    $scope.deskOut = function () {
        /*判断输入的金额对不对*/
        var totalPay = 0;
        for (var i = 0; i < $scope.currencyPayList.length; i++) {
            var obj = $scope.currencyPayList[i];
            if (obj.money < 0) {
                messageService.setMessage({type: 'error', content: '结账金额不可以为负数'});
                popUpService.pop('message');
                return $q(function (resolve) {
                    return resolve();
                });
            }
            totalPay += obj.money * 1;
        }
        if (totalPay != $scope.totalConsume) {
            messageService.setMessage({type: 'error', content: '结账金额总和不等于实收款'});
            popUpService.pop('message');
            return $q(function (resolve) {
                return resolve();
            });
        }
        /*判断抹零金额是否超过服务员权限*/
        var user = LoginService.getUserObj();
        if ($scope.beforePrice - $scope.totalConsume > user.maxDiscount) {
            messageService.setMessage({type: 'error', content: '该操作员最大抹零金额为' + user.maxDiscount});
            popUpService.pop('message');
            return $q(function (resolve) {
                return resolve();
            });
        }
        var deskOut = {};
        deskOut.desk = $scope.desk.name;
        deskOut.pointOfSale = LoginService.getPointOfSale();
        deskOut.currencyPostList = $scope.currencyPayList;
        if ($scope.deskDiscount.discountValue == 1 && $scope.totalConsume != $scope.beforePrice) {//手动抹零
            deskOut.discount = ($scope.totalConsume / $scope.beforePrice).toFixed(2);
        } else {
            deskOut.discount = $scope.deskDiscount.discountValue;
        }
        deskOut.finalPrice = $scope.totalConsume;
        deskOut.deskBook = $scope.deskBook;
        return webService.post('deskOut', deskOut)
            .then(function (r) {
                initDeskDetail();
                /*重新获得当前窗口的desk对象*/
                var p2 = {condition: 'name=' + util.wrapWithBrackets($scope.desk.name) + pointOfSale};
                dataService.refreshDeskList(p2)
                    .then(function () {
                        $scope.desk = dataService.getDeskList()[0];
                    });
                $scope.$emit('deskRefresh');
                window.open(host + "/receipt/" + r);
            })
    };
    /*打印预结单*/
    $scope.deskPrintBefore = function () {
        var deskOut = {};
        deskOut.desk = $scope.desk.name;
        deskOut.pointOfSale = LoginService.getPointOfSale();
        deskOut.discount = $scope.deskDiscount.discountValue;
        deskOut.finalPrice = $scope.totalConsume;
        webService.post('deskPrintBefore', deskOut)
            .then(function (r) {
                window.open(host + "/receipt/" + r);
            })
    }
}]);