package com.sygdsoft.service;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;
import com.sygdsoft.mapper.CompanyDebtMapper;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.model.CompanyDebtIntegration;
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
    @Autowired
    CompanyDebtMapper companyDebtMapper;
    /**
     * 获得某个时间段内某单位挂账明细
     */
    public List<CompanyDebt> getByCompanyDate(String company, Date beginTime, Date endTime){
        return companyDebtMapper.getByCompanyDate(company,beginTime,endTime);
    }

    /**
     * 根据结账序列号和单位名称获得账务列表
     */
    public List<CompanyDebt> getByNameSerial(String company,String paySerial){
        return companyDebtMapper.getByNameSerial(company,paySerial);
    }

    /**
     * 获得某个单位某段时间内的挂账款
     */
    /*正负都算*/
    public Double getDebtByCompanyDate(String company,Date beginTime,Date endTime){
        return companyDebtMapper.getDebtByCompanyDate(company,beginTime,endTime);
    }
    /**
     * 根据结账序列号删除
     */
    public void deleteBySerial(String paySerial) throws Exception {
        CompanyDebt companyDebt = new CompanyDebt();
        companyDebt.setPaySerial(paySerial);
        companyDebtMapper.delete(companyDebt);
    }

    /**
     * 根据销售员和时间获得各个单位的总消费额
     */
    public List<Company> getTotalDebtBySaleManDate(String saleMan,Date beginTime,Date endTime ){
        return companyDebtMapper.getTotalDebtBySaleManDate(saleMan,beginTime,endTime );
    }

    /**
     * 获得某个单位某段时间内的房费和其他
     */
    public CompanyDebtReportRow getRoomConsumeByCompanyDate(String company, Date beginTime,  Date endTime){
        return companyDebtMapper.getRoomConsumeByCompanyDate(company,beginTime,endTime);
    }

    /**
     * 获得某个模块的总金额
     */
    public Double getSumDebtMoney(Date beginTime,Date endTime ,String pointOfSale){
        return companyDebtMapper.getSumDebtMoney(beginTime,endTime,pointOfSale);
    }

    /**
     * 获得单位杂单的总金额
     */
    public Double getSumOtherConsume(){
        return companyDebtMapper.getSumOtherConsume();
    }
}
