package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 * 单位帐卡
 */
public class Company extends BaseEntity{
    private String name;//单位名称
    private String alias;//单位别名，便于检索
    private String category;//协议类别
    private Double consume;//单位消费
    private Double deposit;//单位存款
    private Double debt;//单位欠款
    private String protocol;//适用于哪种房价协议
    private Date limitTime;//有效日期
    private String ifDebt;//允许挂账
    private String saleMan;//销售人员
    private String phone;//联系方式
    @Transient
    private List<CompanyDebtIntegration> companyDebtIntegrationList;
    @Transient
    private List<CompanyDebt> companyDebtList;

    public Company() {
    }
    public Double getNotNullCompanyConsume() {
        return NullJudgement.nullToZero(consume);
    }
    public Double getNotNullCompanyPay(){
        return NullJudgement.nullToZero(deposit);
    }
    public Double getNotNullCompanyDebt(){
        return NullJudgement.nullToZero(debt);
    }

    /**
     * 自动生成的GAS
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

    public String getIfDebt() {
        return ifDebt;
    }

    public void setIfDebt(String ifDebt) {
        this.ifDebt = ifDebt;
    }

    public String getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(String saleMan) {
        this.saleMan = saleMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<CompanyDebtIntegration> getCompanyDebtIntegrationList() {
        return companyDebtIntegrationList;
    }

    public void setCompanyDebtIntegrationList(List<CompanyDebtIntegration> companyDebtIntegrationList) {
        this.companyDebtIntegrationList = companyDebtIntegrationList;
    }

    public List<CompanyDebt> getCompanyDebtList() {
        return companyDebtList;
    }

    public void setCompanyDebtList(List<CompanyDebt> companyDebtList) {
        this.companyDebtList = companyDebtList;
    }
}
