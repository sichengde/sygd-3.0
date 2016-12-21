package com.sygdsoft.service;

import com.sygdsoft.model.Debt;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.model.RoomShop;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-06-17.
 */
@Service
@SzMapper(id = "roomShop")
public class RoomShopService extends BaseService<RoomShop>{
    /**
     * 通过房吧分解字符串获得品种
     */
    public String getShopItem(String msg) {
        return msg.substring(0, msg.indexOf(":"));
    }

    /**
     * 通过房吧分解获得数量
     */
    public Integer getShopNum(String msg) {
        return Integer.valueOf(msg.substring(msg.indexOf(":") + 1, msg.indexOf("*")));
    }

    /**
     * 通过品种数量单价合成房吧信息
     *
     * @param src 成熟的房吧字符串
     * @param des 目标商品
     * @param num 增加的数量
     */
    public String setShopNum(String src, String des, Integer num) {
        Integer var1 = src.indexOf(":", src.indexOf(des));//数量前边冒号出现的位置
        Integer var2 = src.indexOf("*", src.indexOf(des));//数量后边乘号出现的位置
        Integer newNum = Integer.valueOf(src.substring(var1 + 1, var2)) + num;
        return src.substring(0, var1 + 1) + newNum + src.substring(var2, src.length());
    }

}
