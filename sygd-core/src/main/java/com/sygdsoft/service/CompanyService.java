package com.sygdsoft.service;

import com.sygdsoft.mapper.CompanyDebtMapper;
import com.sygdsoft.mapper.CompanyLordMapper;
import com.sygdsoft.mapper.CompanyMapper;
import com.sygdsoft.model.Company;
import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.model.CompanyLord;
import com.sygdsoft.model.Debt;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-11.
 */
@Service
@SzMapper(id = "company")
public class CompanyService extends BaseService<Company> {
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
    @Autowired
    SzMath szMath;

    /**
     * 通过单位名称获取单位对象
     */
    public Company getByName(String name) {
        Company companyQuery = new Company();
        companyQuery.setName(name);
        return companyMapper.selectOne(companyQuery);
    }

    /**
     * 更新单位挂账数值
     */
    public void addDebt(String company, Double debt) {
        companyMapper.addDebt(company, debt);
    }

    /**
     * 为该单位增加消费数据
     */
    public void addConsume(String company, Double consume) {
        companyMapper.addConsume(company, consume);
    }

    /**
     * 结算时币种为单位（离店结算，商品零售）
     */
    public String companyAddDebt(String company, String lord, Double money, String description, String pointOfSale, String secondPointOfSale, String serial) throws Exception {
        Company companyObj = getByName(company);
        if (companyObj.getDeposit() != null && (companyObj.getNotNullCompanyDebt() + money > companyObj.getNotNullDeposit())) {
            throw new Exception("已超出可挂账限额");
        }
        companyObj.setDebt(szMath.formatTwoDecimalReturnDouble(companyObj.getNotNullCompanyDebt() + money));
        update(companyObj);
        companyLordService.addDebt(lord, money);
        CompanyDebt companyDebt = new CompanyDebt();
        companyDebt.setCompany(company);
        companyDebt.setLord(lord);
        companyDebt.setPaySerial(serial);
        companyDebt.setDebt(money);
        companyDebt.setDoTime(timeService.getNow());
        companyDebt.setUserId(userService.getCurrentUser());
        companyDebt.setDescription(description);
        companyDebt.setPointOfSale(pointOfSale);
        companyDebt.setSecondPointOfSale(secondPointOfSale);
        companyDebt.setCurrentRemain(companyObj.getDebt());
        companyDebtService.add(companyDebt);
        return " 转单位至:" + company + " 签单人:" + lord;
    }

    /**
     * 获取该模块的单位挂账款
     * @param module
     * @return
     */
    public Double getModuleDebt(String module,String company) {
        return companyMapper.getModuleDebt(module,company);
    }
}
