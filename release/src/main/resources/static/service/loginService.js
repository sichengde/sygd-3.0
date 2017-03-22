/**
 * Created by 舒展 on 2016-05-28.
 * 登录服务，主要管理登录用到的用户，权限，模块，营业部门
 * sessionStorage主要用于调试期的刷新操作，正式使用后可以去掉
 */
App.factory('LoginService', [function () {
    var reception = [
        {
            name: '房态预览',
            href: 'reception',
            class: 'icon-home'
        },
        {
            name: '营销预定',
            href: 'book/edit',
            class: 'icon-lock'
        },
        {
            name: '团队开房',
            href: 'guestInGroup',
            class: 'icon-group'
        },
        {
            name: '会员系统',
            href: 'vip',
            class: 'icon-vip'
        },
        {
            name: '报表系统',
            href: 'report',
            class: 'icon-file-alt'
        },
        {
            name: '联房管理',
            href: 'joinRoom',
            class: 'icon-sitemap'
        },
        {
            name: '协议单位',
            href: 'company',
            class: 'icon-bookmark-empty'
        },
        {
            name: '应收应付',
            href: 'companyDebt/edit',
            class: 'icon-edit'
        },
        {
            name: '商品零售',
            href: 'retail',
            class: 'icon-bell'
        },
        {
            name: '客房参数',
            href: 'roomParam',
            class: 'icon-cog'
        },
        {
            name: '房态管理',
            href: 'roomStateManager',
            class: 'icon-reorder'
        },
        {
            name: '日志信息',
            href: 'userLogInfo/reception',
            class: 'icon-book'
        },
        {
            name: 'OTA订单',
            href: '',
            class: 'icon-ota'
        }
    ];
    var hotelParam = [
        {
            name: '酒店参数',
            href: 'hotelParam',//系统维护默认界面
            class: 'icon-cogs'
        },
        {
            name: '客房参数',
            href: 'roomParam',
            class: 'icon-home'
        },
        {
            name: '餐饮参数',
            href: 'restaurantParam',
            class: 'icon-food'
        },
        {
            name: '桑拿参数',
            href: 'saunaParam',
            class: 'icon-tint'
        },
        {
            name: '库存参数',
            href: 'storageParam',
            class: 'icon-inbox'
        },
        {
            name: '会员参数',
            href: 'vipParam',
            class: 'icon-vip'
        },
        {
            name: '账务工具',
            href: 'debtTool',
            class: 'icon-key'
        }
    ];
    var restaurant = [
        {
            name: '盘态图',
            href: 'restaurant',
            class: 'icon-food'
        },
        {
            name: '自助餐',
            href: 'buffet',
            class: 'icon-coffee'
        },
        {
            name: '外卖',
            href: 'outSale',
            class: 'icon-volume-down'
        },
        {
            name: '日志信息',
            href: 'userLogInfo/restaurant',
            class: 'icon-book'
        },
        {
            name: '会员系统',
            href: 'vip',
            class: 'icon-vip'
        },
        {
            name: '报表系统',
            href: 'deskReport',
            class: 'icon-file-alt'
        },
        {
            name: '应收应付',
            href: 'companyDebt/edit',
            class: 'icon-edit'
        },
        {
            name: '其他工具',
            href: 'otherTools',
            class: 'icon-wrench'
        },
        {
            name: '参数设置',
            href: 'restaurantParam',
            class: 'icon-cog'
        }];
    var manager = [
        {
            name: '实时状态',
            href: 'realState',
            class: 'icon-bar-chart'
        },
        {
            name: '宾客信息',
            href: 'guestInfo',
            class: 'icon-file-alt'
        },
        {
            name: '营销预定',
            href: 'book/read',
            class: 'icon-lock'
        },
        {
            name: '应收应付',
            href: 'companyDebt/read',
            class: 'icon-edit'
        },
        {
            name: '数据分析',
            href: 'dataParse',
            class: 'icon-signal'
        },
        {
            name: '报表预览',
            href: 'report',
            class: 'icon-file-alt'
        }
    ];
    var sauna = [
        {
            name: '盘态图',
            href: 'sauna',
            class: 'icon-tags'
        },
        {
            name: '消费录入',
            href: 'saunaIn',
            class: 'icon-edit'
        },
        {
            name: '日志信息',
            href: 'userLogInfo/sauna',
            class: 'icon-book'
        },
        {
            name: '会员系统',
            href: 'vip',
            class: 'icon-vip'
        },
        {
            name: '报表系统',
            href: 'saunaReport',
            class: 'icon-file-alt'
        },
        {
            name: '参数设置',
            href: 'saunaParam',
            class: 'icon-cog'
        }];
    var storage = [
        {
            name: '库存管理',
            href: 'storageManager',
            class: 'icon-inbox'
        },
        {
            name: '库存参数',
            href: '/storageParam/',
            class: 'icon-cog'
        },
        {
            name: '库存报表',
            href: 'storageReport',
            class: 'icon-file-alt'
        }
    ];

    var userId;//当前操作员号（注意不是对象，用于操作记录）
    var userObj;//当前操作员对象
    var ownModuleList = [];//当前操作员拥有的全部模块（用于登陆后模块间切换）
    var ownPermissionList = [];//当前操作员拥有的全部权限
    var ownPointOfSaleList = [];//当前操作员拥有的全部营业部门;
    var module;//模块中文名称，用于界面用户选择
    var moduleLink;//模块链接,用于链接导航
    var pointOfSale;//当前登录的营业部门
    function getUser() {
        return userId;
    }

    function setUser(u) {
        userId = u;
    }

    function getUserObj() {
        return userObj
    }

    function setUserObj(obj) {
        userObj = obj;
    }

    function getOwnModuleList() {
        return ownModuleList;
    }

    function setOwnModuleList(p) {
        ownModuleList = p;
    }

    function getOwnPermissionList() {
        return ownPermissionList;
    }

    function setOwnPermissionList(p) {
        ownPermissionList = p;
    }

    function getOwnPointOfSaleList() {
        return ownPointOfSaleList;
    }

    function setOwnPointOfSaleList(p) {
        ownPointOfSaleList = p;
    }

    function getModuleLink() {
        return moduleLink;
    }

    function setModuleLink(p) {
        moduleLink = p;
    }

    function getModule() {
        return module;
    }

    function setModule(moduleName) {
        module = moduleName;
    }

    function setPointOfSale(pos) {
        pointOfSale = pos;
    }

    function getPointOfSale() {
        return pointOfSale;
    }

    function getTitle() {
        if (!module) {
            return '';
        }
        if (pointOfSale) {
            return '-' + module + '-' + pointOfSale;
        } else {
            return '-' + module;
        }
    }

    function clearLogin() {
        userId = null;
        ownModuleList = [];
        ownPermissionList = [];
        ownPointOfSaleList = [];
        module = null;
        moduleLink = null;
        pointOfSale = null;
    }

    return {
        reception: reception,//获取接待的模块按钮
        hotelParam: hotelParam,
        restaurant: restaurant,
        manager: manager,
        sauna: sauna,
        storage: storage,
        getUser: getUser,
        setUser: setUser,
        getUserObj: getUserObj,
        setUserObj: setUserObj,
        getOwnModuleList: getOwnModuleList,
        setOwnModuleList: setOwnModuleList,
        getOwnPermissionList: getOwnPermissionList,
        setOwnPermissionList: setOwnPermissionList,
        getOwnPointOfSaleList: getOwnPointOfSaleList,
        setOwnPointOfSaleList: setOwnPointOfSaleList,
        getModuleLink: getModuleLink,
        setModuleLink: setModuleLink,
        getModule: getModule,
        setModule: setModule,
        getPointOfSale: getPointOfSale,
        setPointOfSale: setPointOfSale,
        getTitle: getTitle,
        clearLogin: clearLogin
    }
}]);