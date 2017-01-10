/**
 * Created by 舒展 on 2016-05-28.
 * 基本服务，包含程序基础模块的所有数据
 */
'use strict';
App.factory('dataService', ['webService', 'util', '$q', function (webService, util, $q) {
    /**
     * 常量
     */
    var sexList = ['男', '女'];//性别数组
    var cardTypeList = ['身份证', '军官证', '护照'];//证件种类
    var roomStateList = ["可用房", "团队房", "散客房", "走客房", "维修房"];//房间状态列表
    var getRoomStateInList = ['团队房', '散客房'];//在住的房间状态
    var roomPriceCategory = ["日租房", "小时房","会议房"];//房租方式数组
    var roomPointOfSale = ["房费", "房吧"];
    var booleanList = ["y", "n"];//是否常量
    var booleanListEn = [true, false];//是否常量
    var bookState = ["有效", "失效"];//预定订单状态
    var staticGuestInCurrency = ["会员"];//押金币种常量
    var storageCalculateStrategy = ["加权平均", "先进先出", "后进后出"];//出库成本计算方式
    var saunaRingState = ["未用", "占用", "维修"];//桑拿手牌状态
    /**
     * 获得可以开房的房态数组
     */
    function getRoomStateAvailableList() {
        if (getOtherParamMapValue('脏房可否开房') == 'y') {
            return ['可用房', '走客房'];
        } else {
            return ['可用房'];
        }
    }

    /**
     * 获得不能开房的房态数组
     */
    function getRoomStateUnavailableList() {
        if (getOtherParamMapValue('脏房可否开房') == 'y') {
            return ['团队房', '散客房', '维修房'];
        } else {
            return ['团队房', '散客房', '维修房', '走客房'];
        }
    }

    /**
     * 可用模块（后台程序直接定义）
     */
    var availableModuleList;

    function refreshAvailableModuleList(p) {
        p = p || {};
        return webService.post('moduleGet', p)
            .then(function (d) {
                availableModuleList = d;
                return d;
            })
    }

    function getAvailableModule() {
        return availableModuleList;
    }

    /**
     * 用户表user
     */
    var userList;

    function refreshUserList(p) {
        p = p || {};
        return webService.post('userGet', p)
            .then(function (d) {
                userList = d;
                return d;
            })
    }

    function getUserList() {
        return userList;
    }

    /**
     * 用户日志表user_log
     */
    var userLogList;

    function refreshUserLogList(p) {
        p = p || {};
        return webService.post('userLogGet', p)
            .then(function (d) {
                userLogList = d;
                return d;
            })
    }

    function getUserLogList() {
        return userLogList;
    }

    /**
     * 营业部门表point_of_sale
     */
    var pointOfSaleList;

    function refreshPointOfSaleList(p) {
        p = p || {};
        return webService.post('pointOfSaleGet', p)
            .then(function (d) {
                pointOfSaleList = d;
                return d;
            })
    }

    function getPointOfSaleList() {
        return pointOfSaleList;
    }

    /**
     * 房间类别表room_category
     */
    var roomCategoryList = [];

    function refreshRoomCategoryList(p) {
        p = p || {};
        return webService.post('roomCategoryGet', p)
            .then(function (d) {
                roomCategoryList = d;
                return d;
            })
    }

    function getRoomCategoryList() {
        return roomCategoryList;
    }

    /**
     * 获取时间（没有表，直接用sql获取）
     */
    var now;

    function refreshTimeNow() {
        return webService.get('time')
            .then(function (d) {
                now = d;
                return d;
            })
    }

    function getTimeNow() {
        return now;
    }

    /**
     * 房间表room
     */
    var roomList = [];

    function refreshRoomList(p) {
        p = p || {};
        return webService.post('roomGet', p)
            .then(function (d) {
                roomList = d;
                return d;
            })
    }

    function getRoomList() {
        return roomList;
    }

    /**
     * 在店户籍check_in表
     */
    var checkInList = [];

    function refreshCheckInList(p) {
        p = p || {};
        return webService.post('checkInGet', p)
            .then(function (d) {
                checkInList = d;
                return d;
            })
    }

    function getCheckInList() {
        return checkInList;
    }

    /**
     * 在店宾客表check_in_guest
     */
    var checkInGuestList = [];

    function refreshCheckInGuestList(p) {
        p = p || {};
        return webService.post('checkInGuestGet', p)
            .then(function (d) {
                checkInGuestList = d;
                return d;
            })
    }

    function getCheckInGuestList() {
        return checkInGuestList;
    }

    /**
     * 离店信息check_out表
     */
    var checkOutList = [];

    function refreshCheckOutList(p) {
        p = p || {};
        return webService.post('checkOutGet', p)
            .then(function (d) {
                checkOutList = d;
                return d;
            })
    }

    function getCheckOutList() {
        return checkOutList;
    }

    /**
     * 离店信息房间check_out_room表
     */
    var checkOutRoomList = [];

    function refreshCheckOutRoomList(p) {
        p = p || {};
        return webService.post('checkOutRoomGet', p)
            .then(function (d) {
                checkOutRoomList = d;
                return d;
            })
    }

    function getCheckOutRoomList() {
        return checkOutRoomList;
    }

    /**
     * 历史宾客表check_in_history
     */
    var checkInHistoryList = [];

    function refreshCheckInHistoryList(p) {
        p = p || {};
        return webService.post('checkInHistoryGet', p)
            .then(function (d) {
                checkInHistoryList = d;
                return d;
            })
    }

    function getCheckInHistoryList() {
        return checkInHistoryList;
    }

    /**
     * 历史宾客明细表check_in_history
     */
    var checkInHistoryLogList = [];

    function refreshCheckInHistoryLogList(p) {
        p = p || {};
        return webService.post('checkInHistoryLogGet', p)
            .then(function (d) {
                checkInHistoryLogList = d;
                return d;
            })
    }

    function getCheckInHistoryLogList() {
        return checkInHistoryLogList;
    }

    /**
     * 在店团队信息表
     */
    var checkInGroupList;

    function refreshCheckInGroupList(p) {
        p = p || {};
        return webService.post('checkInGroupGet', p)
            .then(function (d) {
                checkInGroupList = d;
                return d;
            })
    }

    function getCheckInGroupList() {
        return checkInGroupList;
    }

    /**
     * 客源guest_source
     */
    var guestSourceList = [];

    function refreshGuestSourceList(p) {
        p = p || {};
        return webService.post('guestSourceGet', p)
            .then(function (d) {
                guestSourceList = d;
                return d;
            })
    }

    function getGuestSourceList() {
        return guestSourceList;
    }

    /**
     * 协议房价protocol
     */
    var protocolList = [];

    function refreshProtocolList(p) {
        p = p || {};
        return webService.post('protocolGet', p)
            .then(function (d) {
                protocolList = d;
                return d;
            })
    }

    function getProtocolList() {
        return protocolList;
    }

    /**
     * 币种表currency
     */
    var currencyList;

    function refreshCurrencyList(p) {
        p = p || {};
        return webService.post('currencyGet', p)
            .then(function (d) {
                currencyList = d;
                return d;
            })
    }

    function getCurrencyList() {
        return currencyList;
    }

    /**
     * 账务表debt
     */
    var debtList;

    function refreshDebtList(p) {
        p = p || {};
        return webService.post('debtGet', p)
            .then(function (d) {
                debtList = d;
                return d;
            })
    }

    function getDebtList() {
        return debtList;
    }

    /**
     * 账务历史表debt_history
     */
    var debtHistoryList;

    function refreshDebtHistoryList(p) {
        p = p || {};
        return webService.post('debtHistoryGet', p)
            .then(function (d) {
                debtHistoryList = d;
                return d;
            })
    }

    function getDebtHistoryList() {
        return debtHistoryList;
    }

    /**
     * 账务整合表debt和debt_history(后台将两表合二为一)
     */
    var debtIntegrationList;

    function refreshDebtIntegrationList(p) {
        p = p || {};
        return webService.post('debtIntegrationGet', p)
            .then(function (d) {
                debtIntegrationList = d;
                return d;
            })
    }

    function getDebtIntegrationList() {
        return debtIntegrationList;
    }

    /**
     * 加收房租表room_price_add
     */
    var roomPriceAddList;//加收房租定义数组

    /*获取加收房租数组*/
    function refreshRoomPriceAddList(p) {
        p = p || {};
        return webService.post('roomPriceAddGet', p)
            .then(function (d) {
                roomPriceAddList = d;
                return d;
            })
    }

    function getRoomPriceAddList() {
        return roomPriceAddList;
    }

    /**
     * 其他参数表other_param
     */
    var otherParamMap = {};
    var otherParamList;

    function refreshOtherParamList(p) {
        p = p || {};
        return webService.post('otherParamGet', p)
            .then(function (d) {
                otherParamList = d;
                var l = d.length;
                otherParamMap = {};
                for (var i = 0; i < l; i++) {
                    otherParamMap[d[i].otherParam] = d[i].value;
                }
                return d;
            })
    }

    function getOtherParamList() {
        return otherParamList;
    }

    function getOtherParamMapValue(field) {
        return otherParamMap[field];
    }

    /**
     * 销售的品种类别统计表sale_count
     */

    var saleCountList;

    function refreshSaleCountList(p) {
        p = p || {};
        return webService.post('saleCountGet', p)
            .then(function (d) {
                saleCountList = d;
                return d;
            })
    }

    function getSaleCountList() {
        return saleCountList;
    }

    /**
     * 房吧品种表room_shop
     */

    var roomShopList;

    function refreshRoomShopList(p) {
        p = p || {};
        return webService.post('roomShopGet', p)
            .then(function (d) {
                roomShopList = d;
                return d;
            })
    }

    function getRoomShopList() {
        return roomShopList;
    }

    /**
     * 房吧账务表room_shop_detail
     */

    var roomShopDetailList;

    function refreshRoomShopDetailList(p) {
        p = p || {};
        return webService.post('roomShopDetailGet', p)
            .then(function (d) {
                roomShopDetailList = d;
                return d;
            })
    }

    function getRoomShopDetailList() {
        return roomShopDetailList;
    }

    /**
     * 会员信息表
     */
    var vipList;

    function refreshVipList(p) {
        p = p || {};
        return webService.post('vipGet', p)
            .then(function (d) {
                vipList = d;
                return d;
            })
    }

    function getVipList() {
        return vipList;
    }

    /**
     * 会员信息历史表（被注销的）
     */
    var vipHistoryList;

    function refreshVipHistoryList(p) {
        p = p || {};
        return webService.post('vipHistoryGet', p)
            .then(function (d) {
                vipHistoryList = d;
                return d;
            })
    }

    function getVipHistoryList() {
        return vipHistoryList;
    }

    /**
     * 预定信息表book
     */
    var bookList;

    function refreshBookList(p) {
        p = p || {};
        return webService.post('bookGet', p)
            .then(function (d) {
                bookList = d;
                return bookList;
            })
    }

    function getBookList() {
        return bookList;
    }

    /**
     * 预定历史表bookHistory
     */
    var bookHistoryList;

    function refreshBookHistoryList(p) {
        p = p || {};
        return webService.post('bookHistoryGet', p)
            .then(function (d) {
                bookHistoryList = d;
                return bookHistoryList;
            })
    }

    function getBookHistoryList() {
        return bookHistoryList;
    }

    /**
     * 预定房号表book_room
     */
    var bookRoomList;

    function refreshBookRoomList(p) {
        p = p || {};
        return webService.post('bookRoomGet', p)
            .then(function (d) {
                bookRoomList = d;
                return d;
            })
    }

    function getBookRoomList() {
        return bookRoomList;
    }

    /**
     * 单位帐卡表company
     */
    var companyList;

    function refreshCompanyList(p) {
        p = p || {};
        return webService.post('companyGet', p)
            .then(function (d) {
                companyList = d;
                return d;
            })
    }

    function getCompanyList() {
        return companyList;
    }

    /**
     * 单位签单人表company_lord
     */
    var companyLordList;

    function refreshCompanyLordList(p) {
        p = p || {};
        return webService.post('companyLordGet', p)
            .then(function (d) {
                companyLordList = d;
                return d;
            })
    }

    function getCompanyLordList() {
        return companyLordList;
    }

    /**
     * 交班设定
     */
    var exchangeUserList;

    function refreshExchangeUserList(p) {
        p = p || {};
        return webService.post('exchangeUserGet', p)
            .then(function (d) {
                exchangeUserList = d;
                return d;
            })
    }

    function getExchangeUserList() {
        return exchangeUserList;
    }

    /**
     * 单位类别设定
     */
    var companyCategoryList;

    function refreshCompanyCategoryList(p) {
        p = p || {};
        return webService.post('companyCategoryGet', p)
            .then(function (d) {
                companyCategoryList = d;
                return d;
            })
    }

    function getCompanyCategoryList() {
        return companyCategoryList;
    }

    /**
     * 餐饮桌台设定
     */
    var deskList;

    function refreshDeskList(p) {
        p = p || {};
        return webService.post('deskGet', p)
            .then(function (d) {
                deskList = d;
                return d;
            })
    }

    function getDeskList() {
        return deskList;
    }

    /**
     * 餐饮桌台设定
     */
    var deskInList;

    function refreshDeskInList(p) {
        p = p || {};
        return webService.post('deskInGet', p)
            .then(function (d) {
                deskInList = d;
                return d;
            })
    }

    function getDeskInList() {
        return deskInList;
    }

    /**
     * 餐饮折扣率
     */
    var deskDiscountList;

    function refreshDeskDiscountList(p) {
        p = p || {};
        return webService.post('deskDiscountGet', p)
            .then(function (d) {
                deskDiscountList = d;
                return d;
            })
    }

    function getDeskDiscountList() {
        return deskDiscountList;
    }

    /**
     * 餐饮预定
     */
    var deskBookList;

    function refreshDeskBookList(p) {
        p = p || {};
        return webService.post('deskBookGet', p)
            .then(function (d) {
                deskBookList = d;
                return d;
            })
    }

    function getDeskBookList() {
        return deskBookList;
    }

    /**
     * 餐饮桌台结账分担记录
     */
    var deskPayList;

    function refreshDeskPayList(p) {
        p = p || {};
        return webService.post('deskPayGet', p)
            .then(function (d) {
                deskPayList = d;
                return d;
            })
    }

    function getDeskPayList() {
        return deskPayList;
    }

    /**
     * 餐饮已点菜品
     */
    var deskDetailList;

    function refreshDeskDetailList(p) {
        p = p || {};
        return webService.post('deskDetailGet', p)
            .then(function (d) {
                deskDetailList = d;
                return d;
            })
    }

    function getDeskDetailList() {
        return deskDetailList;
    }

    /**
     * 餐饮已点菜品历史
     */
    var deskDetailHistoryList;

    function refreshDeskDetailHistoryList(p) {
        p = p || {};
        return webService.post('deskDetailHistoryGet', p)
            .then(function (d) {
                deskDetailHistoryList = d;
                return d;
            })
    }

    function getDeskDetailHistoryList() {
        return deskDetailHistoryList;
    }

    /**
     * 餐饮桌台历史
     */
    var deskInHistoryList;

    function refreshDeskInHistoryList(p) {
        p = p || {};
        return webService.post('deskInHistoryGet', p)
            .then(function (d) {
                deskInHistoryList = d;
                return d;
            })
    }

    function getDeskInHistoryList() {
        return deskInHistoryList;
    }

    /**
     * 结账记录表
     */
    var debtPayList;

    function refreshDebtPayList(p) {
        p = p || {};
        return webService.post('debtPayGet', p)
            .then(function (d) {
                debtPayList = d;
                return d;
            })
    }

    function getDebtPayList() {
        return debtPayList;
    }

    /**
     * 单位挂账明细
     */
    var companyDebtList;

    function refreshCompanyDebtList(p) {
        p = p || {};
        return webService.post('companyDebtGet', p)
            .then(function (d) {
                companyDebtList = d;
                return d;
            })
    }

    function getCompanyDebtList() {
        return companyDebtList;
    }

    /**
     * 餐饮菜谱定义
     */
    var menuList;

    function refreshMenuList(p) {
        p = p || {};
        return webService.post('menuGet', p)
            .then(function (r) {
                menuList = r;
                return r;
            })
    }

    function getMenuList() {
        return menuList
    }

    /**
     * 餐饮厨房打印定义
     */
    var cookRoomList;

    function refreshCookRoomList(p) {
        p = p || {};
        return webService.post('cookRoomGet', p)
            .then(function (r) {
                cookRoomList = r;
                return r;
            })
    }

    function getCookRoomList() {
        return cookRoomList
    }

    /**
     * 餐饮套餐定义
     */
    var foodSetList;

    function refreshFoodSetList(p) {
        p = p || {};
        return webService.post('foodSetGet', p)
            .then(function (r) {
                foodSetList = r;
                return r;
            })
    }

    function getFoodSetList() {
        return foodSetList
    }


    /**
     * 餐饮菜谱备注定义
     */
    var menuRemarkList;

    function refreshMenuRemarkList(p) {
        p = p || {};
        return webService.post('menuRemarkGet', p)
            .then(function (r) {
                menuRemarkList = r;
                return r;
            })
    }

    function getMenuRemarkList() {
        return menuRemarkList
    }

    /**
     * 会员账务明细
     */
    var vipDetailList;

    function refreshVipDetailList(p) {
        p = p || {};
        return webService.post('vipDetailGet', p)
            .then(function (r) {
                vipDetailList = r;
                return r;
            })
    }

    function getVipDetailList() {
        return vipDetailList;
    }

    /**
     * 会员账务历史明细（被注销的）
     */
    var vipDetailHistoryList;

    function refreshVipDetailHistoryList(p) {
        p = p || {};
        return webService.post('vipDetailHistoryGet', p)
            .then(function (r) {
                vipDetailHistoryList = r;
                return r;
            })
    }

    function getVipDetailHistoryList() {
        return vipDetailHistoryList;
    }

    /**
     * 会员类别
     */
    var vipCategoryList;

    function refreshVipCategoryList(p) {
        p = p || {};
        return webService.post('vipCategoryGet', p)
            .then(function (r) {
                vipCategoryList = r;
                return r;
            })
    }

    function getVipCategoryList() {
        return vipCategoryList;
    }

    /**
     * 会员可用的id卡号
     */
    var vipIdNumberList;

    function refreshVipIdNumberList(p) {
        p = p || {};
        return webService.post('vipIdNumberGet', p)
            .then(function (r) {
                vipIdNumberList = r;
                return r;
            })
    }

    function getVipIdNumberList() {
        return vipIdNumberList;
    }

    /**
     * 宴请签单人
     */
    var freemanList;

    function refreshFreemanList(p) {
        p = p || {};
        return webService.post('freemanGet', p)
            .then(function (r) {
                freemanList = r;
                return r;
            })
    }

    function getFreemanList() {
        return freemanList;
    }

    /**
     * 宴请人消费明细
     */
    var freeDetailList;

    function refreshFreeDetailList(p) {
        p = p || {};
        return webService.post('freeDetailGet', p)
            .then(function (r) {
                freeDetailList = r;
                return r;
            })
    }

    function getFreeDetailList() {
        return freeDetailList;
    }

    /**
     * 宴请人消费明细
     */
    var cleanRoomManList;

    function refreshCleanRoomManList(p) {
        p = p || {};
        return webService.post('cleanRoomManGet', p)
            .then(function (r) {
                cleanRoomManList = r;
                return r;
            })
    }

    function getCleanRoomManList() {
        return cleanRoomManList;
    }

    /**
     * 客房清扫消费明细
     */
    var cleanRoomList;

    function refreshCleanRoomList(p) {
        p = p || {};
        return webService.post('cleanRoomGet', p)
            .then(function (r) {
                cleanRoomList = r;
                return r;
            })
    }

    function getCleanRoomList() {
        return cleanRoomList;
    }

    /**
     * 仓库定义
     */
    var houseList;

    function refreshHouseList(p) {
        p = p || {};
        return webService.post('houseGet', p)
            .then(function (d) {
                houseList = d;
                return d;
            })
    }

    function getHouseList() {
        return houseList;
    }

    /**
     * 货品类别定义
     */
    var storageCategoryList;

    function refreshStorageCategoryList(p) {
        p = p || {};
        return webService.post('storageCategoryGet', p)
            .then(function (d) {
                storageCategoryList = d;
                return d;
            })
    }

    function getStorageCategoryList() {
        return storageCategoryList;
    }

    /**
     * 供应商定义
     */
    var supplierList;

    function refreshSupplierList(p) {
        p = p || {};
        return webService.post('supplierGet', p)
            .then(function (d) {
                supplierList = d;
                return d;
            })
    }

    function getSupplierList() {
        return supplierList;
    }

    /**
     * 货品定义
     */
    var cargoList;

    function refreshCargoList(p) {
        p = p || {};
        return webService.post('cargoGet', p)
            .then(function (d) {
                cargoList = d;
                return d;
            })
    }

    function getCargoList() {
        return cargoList;
    }

    /**
     * 领用部门定义
     */
    var storageOutDeptList;

    function refreshStorageOutDeptList(p) {
        p = p || {};
        return webService.post('storageOutDeptGet', p)
            .then(function (d) {
                storageOutDeptList = d;
                return d;
            })
    }

    function getStorageOutDeptList() {
        return storageOutDeptList;
    }

    /**
     * 采购部门定义
     */
    var storageInDeptList;

    function refreshStorageInDeptList(p) {
        p = p || {};
        return webService.post('storageInDeptGet', p)
            .then(function (d) {
                storageInDeptList = d;
                return d;
            })
    }

    function getStorageInDeptList() {
        return storageInDeptList;
    }

    /**
     * 批准人定义
     */
    var approverList;

    function refreshApproverList(p) {
        p = p || {};
        return webService.post('approverGet', p)
            .then(function (d) {
                approverList = d;
                return d;
            })
    }

    function getApproverList() {
        return approverList;
    }

    /**
     * 入库类型
     */
    var storageInCategoryList;

    function refreshStorageInCategoryList(p) {
        p = p || {};
        return webService.post('storageInCategoryGet', p)
            .then(function (d) {
                storageInCategoryList = d;
                return d;
            })
    }

    function getStorageInCategoryList() {
        return storageInCategoryList;
    }

    /**
     * 出库类型
     */
    var storageOutCategoryList;

    function refreshStorageOutCategoryList(p) {
        p = p || {};
        return webService.post('storageOutCategoryGet', p)
            .then(function (d) {
                storageOutCategoryList = d;
                return d;
            })
    }

    function getStorageOutCategoryList() {
        return storageOutCategoryList;
    }

    /**
     * 入库记录
     */
    var storageInList;

    function refreshStorageInList(p) {
        p = p || {};
        return webService.post('storageInGet', p)
            .then(function (d) {
                storageInList = d;
                return d;
            })
    }

    function getStorageInList() {
        return storageInList;
    }

    /**
     * 入库明细
     */
    var storageInDetailList;

    function refreshStorageInDetailList(p) {
        p = p || {};
        return webService.post('storageInDetailGet', p)
            .then(function (d) {
                storageInDetailList = d;
                return d;
            })
    }

    function getStorageInDetailList() {
        return storageInDetailList;
    }

    /**
     * 库存余量（视图）
     */
    var storageRemainList;

    function refreshStorageRemainList(p) {
        p = p || {};
        return webService.post('storageRemainGet', p)
            .then(function (d) {
                storageRemainList = d;
                return d;
            })
    }

    function getStorageRemainList() {
        return storageRemainList;
    }

    /**
     * 出库记录
     */
    var storageOutList;

    function refreshStorageOutList(p) {
        p = p || {};
        return webService.post('storageOutGet', p)
            .then(function (d) {
                storageOutList = d;
                return d;
            })
    }

    function getStorageOutList() {
        return storageOutList;
    }

    /**
     * 出库明细
     */
    var storageOutDetailList;

    function refreshStorageOutDetailList(p) {
        p = p || {};
        return webService.post('storageOutDetailGet', p)
            .then(function (d) {
                storageOutDetailList = d;
                return d;
            })
    }

    function getStorageOutDetailList() {
        return storageOutDetailList;
    }

    /**
     * 手牌定义
     */
    var saunaRingList;

    function refreshSaunaRingList(p) {
        p = p || {};
        return webService.post('saunaRingGet', p)
            .then(function (d) {
                saunaRingList = d;
                return d;
            })
    }

    function getSaunaRingList() {
        return saunaRingList;
    }

    /**
     * 桑拿消费品定义
     */
    var saunaMenuList;

    function refreshSaunaMenuList(p) {
        p = p || {};
        return webService.post('saunaMenuGet', p)
            .then(function (d) {
                saunaMenuList = d;
                return d;
            })
    }

    function getSaunaMenuList() {
        return saunaMenuList;
    }

    /**
     * 桑拿折扣定义
     */
    var saunaDiscountList;

    function refreshSaunaDiscountList(p) {
        p = p || {};
        return webService.post('saunaDiscountGet', p)
            .then(function (d) {
                saunaDiscountList = d;
                return d;
            })
    }

    function getSaunaDiscountList() {
        return saunaDiscountList;
    }

    /**
     * 桑拿开牌账单
     */
    var saunaInList;

    function refreshSaunaInList(p) {
        p = p || {};
        return webService.post('saunaInGet', p)
            .then(function (d) {
                saunaInList = d;
                return d;
            })
    }

    function getSaunaInList() {
        return saunaInList;
    }

    /**
     * 桑拿开牌账单历史
     */
    var saunaInHistoryList;

    function refreshSaunaInHistoryList(p) {
        p = p || {};
        return webService.post('saunaInHistoryGet', p)
            .then(function (d) {
                saunaInHistoryList = d;
                return d;
            })
    }

    function getSaunaInHistoryList() {
        return saunaInHistoryList;
    }

    /**
     * 桑拿消费明细
     */
    var saunaDetailList;

    function refreshSaunaDetailList(p) {
        p = p || {};
        return webService.post('saunaDetailGet', p)
            .then(function (d) {
                saunaDetailList = d;
                return d;
            })
    }

    function getSaunaDetailList() {
        return saunaDetailList;
    }

    /**
     * 桑拿消费明细历史
     */
    var saunaDetailHistoryList;

    function refreshSaunaDetailHistoryList(p) {
        p = p || {};
        return webService.post('saunaDetailHistoryGet', p)
            .then(function (d) {
                saunaDetailHistoryList = d;
                return d;
            })
    }

    function getSaunaDetailHistoryList() {
        return saunaDetailHistoryList;
    }

    /**
     * 桑拿账单明细
     */
    var saunaMenuDetailList;

    function refreshSaunaMenuDetailList(p) {
        p = p || {};
        return webService.post('saunaMenuDetailGet', p)
            .then(function (d) {
                saunaMenuDetailList = d;
                return d;
            })
    }

    function getSaunaMenuDetailList() {
        return saunaMenuDetailList;
    }

    /**
     * 桑拿技师
     */
    var saunaUserList;

    function refreshSaunaUserList(p) {
        p = p || {};
        return webService.post('saunaUserGet', p)
            .then(function (d) {
                saunaUserList = d;
                return d;
            })
    }

    function getSaunaUserList() {
        return saunaUserList;
    }

    /**
     * 账单类型（桑拿餐饮通用）
     */
    var inCategoryList;
    function refreshInCategoryList(p) {
        p = p || {};
        return webService.post('inCategoryGet', p)
            .then(function (d) {
                inCategoryList = d;
                return d;
            })
    }

    function getInCategoryList() {
        return inCategoryList;
    }

    /**
     * 来店信息整合
     */
    var checkInIntegrationList;
    function refreshCheckInIntegrationList(p) {
        p = p || {};
        return webService.post('checkInIntegrationGet', p)
            .then(function (d) {
                checkInIntegrationList = d;
                return d;
            })
    }

    function getCheckInIntegrationList() {
        return checkInIntegrationList;
    }

    /**
     * 公共方法，获取之类的
     */
    var refreshed;
    /*批量执行promise函数*/
    function initData(fun, param) {
        var funList = [];
        for (var i = 0; i < fun.length; i++) {
            var f = this[fun[i]];
            funList.push(f);
        }
        return $q(function (resolve, reject) {
            /*统一返回*/
            var total = 0;
            var l = fun.length;
            for (var i = 0; i < l; i++) {
                var p;
                if (param) {
                    p = param[i];
                }
                funList[i](p).then(function () {
                    total = total + 1;
                    if (total == l) {
                        refreshed = true;
                        return resolve(refreshed);
                    }
                })
            }
        });
    }

    return {
        getSexList: sexList,
        getCardTypeList: cardTypeList,
        getRoomPriceCategory: roomPriceCategory,
        getRoomStateList: roomStateList,
        getRoomStateInList: getRoomStateInList,
        getRoomStateUnavailableList: getRoomStateUnavailableList,
        getRoomStateAvailableList: getRoomStateAvailableList,
        getRoomPointOfSale: roomPointOfSale,
        getStaticGuestInCurrency: staticGuestInCurrency,
        getBooleanList: booleanList,
        getBooleanListEn: booleanListEn,
        getBookState: bookState,
        storageCalculateStrategy: storageCalculateStrategy,
        saunaRingState: saunaRingState,
        getAvailableModule: getAvailableModule,
        refreshAvailableModuleList: refreshAvailableModuleList,
        getUserList: getUserList,
        refreshUserList: refreshUserList,
        getUserLogList: getUserLogList,
        refreshUserLogList: refreshUserLogList,
        getPointOfSale: getPointOfSaleList,
        refreshPointOfSaleList: refreshPointOfSaleList,
        getRoomList: getRoomList,
        refreshRoomList: refreshRoomList,
        getCheckInList: getCheckInList,
        refreshCheckInList: refreshCheckInList,
        getCheckInGuestList: getCheckInGuestList,
        refreshCheckOutList: refreshCheckOutList,
        getCheckOutList: getCheckOutList,
        refreshCheckOutRoomList: refreshCheckOutRoomList,
        getCheckOutRoomList: getCheckOutRoomList,
        refreshCheckInHistoryList: refreshCheckInHistoryList,
        getCheckInHistoryList: getCheckInHistoryList,
        refreshCheckInHistoryLogList: refreshCheckInHistoryLogList,
        getCheckInHistoryLogList: getCheckInHistoryLogList,
        refreshCheckInGuestList: refreshCheckInGuestList,
        getCheckInGroupList: getCheckInGroupList,
        refreshCheckInGroupList: refreshCheckInGroupList,
        getRoomCategoryList: getRoomCategoryList,
        refreshRoomCategoryList: refreshRoomCategoryList,
        getTimeNow: getTimeNow,
        refreshTimeNow: refreshTimeNow,
        getGuestSourceList: getGuestSourceList,
        refreshGuestSourceList: refreshGuestSourceList,
        getProtocolList: getProtocolList,
        refreshProtocolList: refreshProtocolList,
        getCurrencyList: getCurrencyList,
        refreshCurrencyList: refreshCurrencyList,
        getDebtList: getDebtList,
        refreshDebtHistoryList: refreshDebtHistoryList,
        getDebtHistoryList: getDebtHistoryList,
        refreshDebtList: refreshDebtList,
        getRoomPriceAddList: getRoomPriceAddList,
        refreshDebtIntegrationList: refreshDebtIntegrationList,
        getDebtIntegrationList: getDebtIntegrationList,
        refreshRoomPriceAddList: refreshRoomPriceAddList,
        getOtherParamList: getOtherParamList,
        getOtherParamMapValue: getOtherParamMapValue,
        refreshOtherParamList: refreshOtherParamList,
        getSaleCountList: getSaleCountList,
        refreshSaleCountList: refreshSaleCountList,
        getRoomShopList: getRoomShopList,
        refreshRoomShopList: refreshRoomShopList,
        getVipList: getVipList,
        refreshVipList: refreshVipList,
        getVipHistoryList: getVipHistoryList,
        refreshVipHistoryList: refreshVipHistoryList,
        getRoomShopDetailList: getRoomShopDetailList,
        refreshRoomShopDetailList: refreshRoomShopDetailList,
        getBookList: getBookList,
        refreshBookList: refreshBookList,
        getBookHistoryList: getBookHistoryList,
        refreshBookHistoryList: refreshBookHistoryList,
        getBookRoomList: getBookRoomList,
        refreshBookRoomList: refreshBookRoomList,
        getCompanyList: getCompanyList,
        refreshCompanyList: refreshCompanyList,
        getCompanyLordList: getCompanyLordList,
        refreshCompanyLordList: refreshCompanyLordList,
        getExchangeUserList: getExchangeUserList,
        refreshExchangeUserList: refreshExchangeUserList,
        getDeskList: getDeskList,
        refreshDeskList: refreshDeskList,
        getDeskInList: getDeskInList,
        refreshDeskInList: refreshDeskInList,
        refreshDeskDiscountList: refreshDeskDiscountList,
        getDeskDiscountList: getDeskDiscountList,
        refreshCompanyCategoryList: refreshCompanyCategoryList,
        getCompanyCategoryList: getCompanyCategoryList,
        refreshDeskBookList: refreshDeskBookList,
        getDeskBookList: getDeskBookList,
        refreshDeskPayList: refreshDeskPayList,
        getDeskPayList: getDeskPayList,
        refreshDeskDetailList: refreshDeskDetailList,
        getDeskDetailList: getDeskDetailList,
        refreshDeskDetailHistoryList: refreshDeskDetailHistoryList,
        getDeskDetailHistoryList: getDeskDetailHistoryList,
        refreshDeskInHistoryList: refreshDeskInHistoryList,
        getDeskInHistoryList: getDeskInHistoryList,
        refreshDebtPayList: refreshDebtPayList,
        getDebtPayList: getDebtPayList,
        refreshCompanyDebtList: refreshCompanyDebtList,
        getCompanyDebtList: getCompanyDebtList,
        refreshMenuList: refreshMenuList,
        getMenuList: getMenuList,
        refreshCookRoomList: refreshCookRoomList,
        getCookRoomList: getCookRoomList,
        refreshFoodSetList: refreshFoodSetList,
        getFoodSetList: getFoodSetList,
        refreshMenuRemarkList: refreshMenuRemarkList,
        getMenuRemarkList: getMenuRemarkList,
        refreshVipDetailList: refreshVipDetailList,
        getVipDetailList: getVipDetailList,
        refreshVipDetailHistoryList: refreshVipDetailHistoryList,
        getVipDetailHistoryList: getVipDetailHistoryList,
        refreshVipCategoryList: refreshVipCategoryList,
        getVipCategoryList: getVipCategoryList,
        refreshVipIdNumberList: refreshVipIdNumberList,
        getVipIdNumberList: getVipIdNumberList,
        refreshFreemanList: refreshFreemanList,
        getFreemanList: getFreemanList,
        refreshFreeDetailList: refreshFreeDetailList,
        getFreeDetailList: getFreeDetailList,
        refreshCleanRoomManList: refreshCleanRoomManList,
        getCleanRoomManList: getCleanRoomManList,
        refreshCleanRoomList: refreshCleanRoomList,
        getCleanRoomList: getCleanRoomList,
        refreshHouseList: refreshHouseList,
        getHouseList: getHouseList,
        refreshStorageCategoryList: refreshStorageCategoryList,
        getStorageCategoryList: getStorageCategoryList,
        refreshSupplierList:refreshSupplierList,
        getSupplierList:getSupplierList,
        refreshCargoList: refreshCargoList,
        getCargoList: getCargoList,
        refreshStorageOutDeptList: refreshStorageOutDeptList,
        getStorageOutDeptList: getStorageOutDeptList,
        refreshStorageInDeptList: refreshStorageInDeptList,
        getStorageInDeptList: getStorageInDeptList,
        refreshApproverList: refreshApproverList,
        getApproverList: getApproverList,
        refreshStorageInCategoryList: refreshStorageInCategoryList,
        getStorageInCategoryList: getStorageInCategoryList,
        refreshStorageOutCategoryList: refreshStorageOutCategoryList,
        getStorageOutCategoryList: getStorageOutCategoryList,
        refreshStorageInList: refreshStorageInList,
        getStorageInList: getStorageInList,
        refreshStorageInDetailList: refreshStorageInDetailList,
        getStorageInDetailList: getStorageInDetailList,
        refreshStorageRemainList: refreshStorageRemainList,
        getStorageRemainList: getStorageRemainList,
        refreshStorageOutList: refreshStorageOutList,
        getStorageOutList: getStorageOutList,
        refreshStorageOutDetailList: refreshStorageOutDetailList,
        getStorageOutDetailList: getStorageOutDetailList,
        refreshSaunaRingList:refreshSaunaRingList,
        getSaunaRingList:getSaunaRingList,
        refreshSaunaMenuList:refreshSaunaMenuList,
        getSaunaMenuList:getSaunaMenuList,
        refreshSaunaDiscountList:refreshSaunaDiscountList,
        getSaunaDiscountList:getSaunaDiscountList,
        refreshSaunaInList:refreshSaunaInList,
        getSaunaInList:getSaunaInList,
        refreshSaunaInHistoryList:refreshSaunaInHistoryList,
        getSaunaInHistoryList:getSaunaInHistoryList,
        refreshSaunaDetailList:refreshSaunaDetailList,
        getSaunaDetailList:getSaunaDetailList,
        refreshSaunaDetailHistoryList:refreshSaunaDetailHistoryList,
        getSaunaDetailHistoryList:getSaunaDetailHistoryList,
        refreshSaunaMenuDetailList:refreshSaunaMenuDetailList,
        getSaunaMenuDetailList:getSaunaMenuDetailList,
        refreshSaunaUserList:refreshSaunaUserList,
        getSaunaUserList:getSaunaUserList,
        refreshInCategoryList:refreshInCategoryList,
        getInCategoryList:getInCategoryList,
        refreshCheckInIntegrationList:refreshCheckInIntegrationList,
        getCheckInIntegrationList:getCheckInIntegrationList,
        initData: initData
    }
}]);