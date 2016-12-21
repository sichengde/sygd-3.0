package com.sygdsoft.service;

import com.sygdsoft.mapper.FreemanMapper;
import com.sygdsoft.model.FreeDetail;
import com.sygdsoft.model.Freeman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-07-26.
 */
@Service
@SzMapper(id = "freeman")
public class FreemanService extends BaseService<Freeman>{
    @Autowired
    FreemanMapper freemanMapper;
    @Autowired
    UserService userService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    FreeDetailService freeDetailService;
    /**
     * 更新该宴请签单人消费总和
     */
    public void addConsume(String freeman,Double consume){
        freemanMapper.addConsume(freeman, consume);
    }

    /**
     * 结算时币种为宴请（离店结算，商品零售）
     */
    public String freePay(String freeman,String reason,Double money) throws Exception {
        this.addConsume(freeman, money);
        FreeDetail freeDetail = new FreeDetail();
        freeDetail.setUserId(userService.getCurrentUser());
        freeDetail.setConsume(money);
        freeDetail.setDoTime(timeService.getNow());
        freeDetail.setFreeman(freeman);
        freeDetail.setPaySerial(serialService.getPaySerial());
        freeDetail.setPointOfSale(pointOfSaleService.JQ);
        freeDetail.setReason(reason);
        freeDetailService.add(freeDetail);
        return "转为宴请,签单人:" + freeman;
    }

}
