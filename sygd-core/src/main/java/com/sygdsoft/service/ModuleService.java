package com.sygdsoft.service;

import com.sygdsoft.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 舒展 on 2016-05-19.
 * 桑拿和餐饮分别生成序列号
 * 桑拿对12做异或
 * 餐饮对19做异或
 */
@Service
public class ModuleService {
    //新版权限
    public Module[] moduleUltimateNew = new Module[]{
            new Module("接待", "reception", new String[]{
                    "开房", "结账",  "联房", "杂单", "冲账", "房吧","提前制卡","修改户籍","钱箱提款","修改房价","手录身份","手动积分"
                    , "前台接待"
                    , "房务中心"
                    , "团队管理"
                    , "营销预定"
                    , "会员管理"
                    , "应收应付"
                    , "报表系统"
                    , "日志信息"
                    , "其他工具"
            }),
            new Module("餐饮", "restaurant", new String[]{
                    "退菜","赠菜", "主界面", "宴会预定","日志信息", "报表系统", "其他工具", "参数设置", "外卖","厨打划单"
            }, true),
            new Module("桑拿", "sauna", new String[]{
                    "消费录入","主界面",
            }, true),
            new Module("库存", "storage", new String[]{
                    "库存参数",
                    "库存管理",
                    "库存报表"
            }),
            new Module("经理查询", "manager", new String[]{
                    "实时状态","宾客信息","会员分析","数据分析","库存记录","每日数据","报表导航"
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
     * 获取新版module
     */
    public Module[] moduleNewGet(){
        if(!registerService.getPassCK()){
            moduleUltimateNew[1]=new Module("无餐饮权限","",new String[]{});
        }
        if(!registerService.getPassSN()){
            moduleUltimateNew[2]=new Module("无桑拿权限","",new String[]{});
        }
        return moduleUltimateNew;
    }

    /**
     * 接待：0
     * 餐饮：1
     * 桑拿：2
     */
    public List<String> moduleCheck(){
        List<String> moduleList=new ArrayList<>();
        if(registerService.getPass()){
            moduleList.add("room");
        }
        if(registerService.getPassCK()){
            moduleList.add("eat");
        }
        if(registerService.getPassSN()){
            moduleList.add("sauna");
        }
        return moduleList;
    }
}
