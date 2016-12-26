package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyDebtMapper;
import com.sygdsoft.mapper.CompanyLordMapper;
import com.sygdsoft.mapper.CompanyMapper;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.model.CompanyLord;
import com.sygdsoft.model.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-11.
 */
@Service
@SzMapper(id = "company")
public class CompanyService extends BaseService<Company>{
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    CompanyLordService companyLordService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    CompanyDebtService companyDebtService;

    /**
     * 更新单位挂账数值
     */
    public void addDebt(String company,Double debt){
        companyMapper.addDebt(company,debt );
    }

    /**
     * 充值
     */
    public void addDeposit(String companyName, Double money)  {
        companyMapper.addDeposit(companyName,money);
    }
    /**
     * 结算
     */
    public void pay(String company,Double total,Double deposit){
        companyMapper.pay(company, total, deposit);
    }
    /**
     * 查询充值总额
     */
    public Double getTotalDeposit(Date beginTime,Date endTime){
        return companyMapper.getTotalDeposit(beginTime, endTime);
    }

    /**
     * 为该单位增加消费数据
     */
    public void addConsume(String company,Double consume){
        companyMapper.addConsume(company,consume);
    }

    /**
     * 结算时币种为单位（离店结算，商品零售）
     */
    public String companyPay(String company, String lord, Double money,String description) throws Exception {
        this.addDebt(company, money);
        companyLordService.addDebt(lord, money);
        CompanyDebt companyDebt = new CompanyDebt();
        companyDebt.setCompany(company);
        companyDebt.setLord(lord);
        companyDebt.setPaySerial(serialService.getPaySerial());
        companyDebt.setDebt(money);
        companyDebt.setDoTime(timeService.getNow());
        companyDebt.setUserId(userService.getCurrentUser());
        companyDebt.setCategory("单位挂账");
        companyDebt.setDescription(description);
        companyDebtService.add(companyDebt);
        return " 转单位至:" + company + " 签单人:" + lord;
    }
}
