package com.sygdsoft.service;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;
import com.sygdsoft.mapper.CompanyDebtMapper;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
@Service
@SzMapper(id = "companyDebt")
public class CompanyDebtService extends BaseService<CompanyDebt> {
    public String deposit = "单位充值";
    public String pay = "单位结算";
    public String debt = "单位挂账";
    @Autowired
    CompanyDebtMapper companyDebtMapper;

    /**
     * 获得单位充值总额
     */
    public Double getTotalCompanyDeposit(String userId, String currency, Date beginTime, Date endTime) {
        if (userId == null) {
            return companyDebtMapper.getDepositBy(currency, beginTime, endTime);
        } else {
            return companyDebtMapper.getDepositByUser(userId, currency, beginTime, endTime);
        }
    }

    /**
     * 获得某个单位某段时间内的挂账款
     */
    public Double getDebtByCompanyDate(String company,Date beginTime,Date endTime){
        return companyDebtMapper.getDebtByCompanyDate(company,beginTime,endTime);
    }

    /**
     * 根据结账序列号删除
     */
    public void deleteBySerial(String paySerial) throws Exception {
        CompanyDebt companyDebt = new CompanyDebt();
        companyDebt.setPaySerial(paySerial);
        this.delete(companyDebt);
    }

    /**
     * 根据销售员和时间获得各个单位的总消费额
     */
    public List<Company> getTotalDebtBySaleManDate(String saleMan,Date beginTime,Date endTime ){
        return companyDebtMapper.getTotalDebtBySaleManDate(saleMan,beginTime,endTime );
    }

    /**
     * 根据时间获得各个单位的总挂账款
     */
    public List<CompanyDebtReportRow> getTotalDebtByDate(Date beginTime,Date endTime ){
        return companyDebtMapper.getTotalDebtByDate(beginTime,endTime);
    }
}
