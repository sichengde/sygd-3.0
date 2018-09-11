package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.DeskDetailMapper;
import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.model.DeskIn;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
@Service
@SzMapper(id = "deskDetail")
public class DeskDetailService extends BaseService<DeskDetail> {
    @Autowired
    DeskDetailMapper deskDetailMapper;
    @Autowired
    Util util;
    @Autowired
    OtherParamService otherParamService;

    /**
     * 获得聚合后的
     */
    public List<DeskDetail> getGroupByQuery(Query query) throws Exception {
        String[] orderBy = query.getOrderByList();
        String[] finalString;
        String nameOrSign = "foodSign";
        if ("y".equals(otherParamService.getValueByName("结账包含退菜"))) {
            nameOrSign = "foodName";
        }
        if (orderBy == null) {
            finalString = new String[]{nameOrSign};
        } else {
            finalString = new String[orderBy.length + 1];
            System.arraycopy(orderBy, 0, finalString, 0, orderBy.length);
            finalString[finalString.length - 1] = nameOrSign;
        }
        query.setOrderByList(finalString);
            /*菜品聚合的话还要加一个条件，套餐的菜要放在最后边，通过price is null判断*/
        List<DeskDetail> deskDetailList = get(query);//不带套餐的菜品
        List<DeskDetail> out = new ArrayList<>();
        String lastName = null;
        DeskDetail lastDeskDetail = null;
        for (DeskDetail deskDetail : deskDetailList) {
            String sign = deskDetail.getFoodSign();
            if ("y".equals(otherParamService.getValueByName("结账包含退菜"))) {
                sign = deskDetail.getFoodName();
            }
            if (sign.equals(lastName) && deskDetail.getNum() != null && lastDeskDetail.getNum() != null && deskDetail.getNotNullPrice().equals(lastDeskDetail.getNotNullPrice())) {//重名，并且两者数量都不是null（说明不是套餐里的菜），并且单价也不能一样，就聚合
                lastDeskDetail.setNum(lastDeskDetail.getNum() + deskDetail.getNum());
            } else {
                out.add(deskDetail);
                lastName = sign;
                lastDeskDetail = deskDetail;
            }
        }
        return out;
    }

    /**
     * 通过桌号和销售点获得该桌账务列表，不进行聚合，主要用于数据计表
     */
    /*考虑营业部门*/
    public List<DeskDetail> getListByDesk(String deskName, String pointOfSale, String orderByList, Boolean foodSet) throws Exception {
        return deskDetailMapper.getList(deskName, pointOfSale, orderByList, foodSet);
    }

    /*根据厨房排序，用于电子划菜*/
    public List<DeskDetail> getListByDeskCookRoom(String deskName) throws Exception {
        Query query = new Query("desk=" + util.wrapWithBrackets(deskName));
        query.setOrderByList(new String[]{"cookRoom"});
        return get(query);
    }

    /**
     * 通过桌号和销售点获得该桌账务列表，根据菜品名排序，进行聚合，主要用于账单显示
     */
    public List<DeskDetail> getListByDeskGroup(String deskName, String pointOfSale) throws Exception {
        Query query = new Query("desk=" + util.wrapWithBrackets(deskName) + " and point_of_sale=" + util.wrapWithBrackets(pointOfSale));
        query.setOrderByList(new String[]{"category"});
        return getGroupByQuery(query);
    }

    /**
     * 通过桌号获得当前桌参与点菜的操作员
     */
    public List<String> getDistinctUserId(String deskName, String pointOfSale) {
        return deskDetailMapper.getDistinctUserId(deskName, pointOfSale);
    }
}
