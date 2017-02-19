package com.sygdsoft.service;

import com.sygdsoft.mapper.PointOfSaleMapper;
import com.sygdsoft.model.PointOfSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-19.
 * 营业部门 分为一级营业部门和二级营业部门，一级营业部门：接待，餐饮，桑拿，二级具体的房费，房吧（固定）餐厅名（自定义）
 */
@Service
@SzMapper(id = "pointOfSale")
public class PointOfSaleService extends BaseService<PointOfSale>{
    public final String JQ = "接待";
    public final String CK = "餐饮";
    public final String SN = "桑拿";

    public final String FF = "房费";
    public final String FB = "房吧";
    public final String DH = "电话";
    @Autowired
    PointOfSaleMapper pointOfSaleMapper;

    /**
     * 对象数组转字符串数组
     */
    public List<String> listToStringList(List<PointOfSale> pointOfSaleList){
        List<String> out=new ArrayList<>();
        for (PointOfSale pointOfSale : pointOfSaleList) {
            out.add(pointOfSale.getFirstPointOfSale());
        }
        return out;
    }

    /**
     * 根据一级营业部门获得一条记录
     */
    public PointOfSale getByFirst(String pointOfSale){
        PointOfSale pointOfSaleQuery=new PointOfSale();
        pointOfSaleQuery.setFirstPointOfSale(pointOfSale);
        return pointOfSaleMapper.selectOne(pointOfSaleQuery);
    }

    /**
     * 获得餐饮模块的所有营业部门
     */
    public List<PointOfSale> getByModule(String module){
        PointOfSale pointOfSaleQuery=new PointOfSale();
        pointOfSaleQuery.setModule(module);
        return pointOfSaleMapper.select(pointOfSaleQuery);
    }


}
