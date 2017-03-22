package com.sygdsoft.service;

import com.sygdsoft.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 舒展 on 2016-05-19.
 * 分为最终版模块和体验版模块，根据注册码
 */
@Service
public class ModuleService {
    private Module[] moduleUltimate = new Module[]{
            new Module("接待", "reception", new String[]{
                    "开房", "结账", "团队结算", "联房", "杂单", "冲账", "房吧"
                    , "房态预览"
                    , "营销预定"
                    , "团队开房"
                    , "会员系统"
                    , "报表系统"
                    , "联房管理"
                    , "协议单位"
                    , "应收应付"
                    , "商品零售"
                    , "客房参数"
                    , "房态管理"
                    , "日志信息"
                    , "OTA订单"
            }),
            new Module("餐饮", "restaurant", new String[]{
                    "退菜","赠菜", "盘态图", "自助餐", "日志信息", "报表系统", "其他工具", "参数设置", "外卖","厨打划单"
            }, true),
            new Module("桑拿", "sauna", new String[]{
                    "消费录入",
            }, true),
            /*new Module("会员", "vip", new String[]{
            }),
            new Module("预定", "BOOK", new String[]{
            }),*/
            new Module("库存", "storage", new String[]{
                    "库存参数",
                    "库存管理",
                    "库存报表"
            }),
            new Module("经理查询", "manager", new String[]{
                    "实时状态","宾客信息","数据分析","报表预览"
            }),
            new Module("系统维护", "hotelParam", new String[]{
                     "酒店参数"
                    , "客房参数"
                    , "餐饮参数"
                    , "桑拿参数"
                    , "库存参数"
                    , "会员参数"
                    , "账务工具","手动夜审","客房叫回", "餐饮叫回", "权限管理"
            })
    };
    @Autowired
    RegisterService registerService;

    /**
     * 获取全部Module
     */
    public Module[] getAllModule() {
        return moduleUltimate;
/*        if (registerService.getPass() == 0) {//0是通过
            return moduleUltimate;
        }else {
            return null;
        }*/
    }
}
