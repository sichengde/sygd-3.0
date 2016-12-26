package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;
import com.sygdsoft.util.NullJudgement;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2016-05-09.
 */
public class Vip extends BaseEntity {
    private String vipNumber;//卡号
    private String cardId;//身份证
    private String category;//卡类别
    private String name;//姓名
    private String sex;//性别
    private String address;//地址
    private String race;//民族
    private Date birthdayTime;//生日
    private String phone;//电话
    private String state;//状态
    private Integer score;//积分
    private Double remain;//余额
    private Double deposit;//押金
    private Date doTime;//发卡时间
    private Date remainTime;//有效时间
    private String idNumber;//id卡号
    private String userId;//操作员号
    private String workCompany;//工作单位
    private String remark;//备注
    @Transient
    private String protocol;//关联的房价协议
    @Transient
    private CurrencyPost currencyPost;//会员发卡的时候充值的币种，为了记表vipDetail方便，vip中就不记了
    @Transient
    private Double deserve;//抵用金额，用于记录在vipDetail表中，vip表里只记录余额

    public Vip() {
    }


    /**
     * 获得非空的Double
     */
    public Double getNotNullVipRemain() {
        return NullJudgement.nullToZero(remain);
    }

    public Integer getNotNullScore() {
        return NullJudgement.nullToZero(score);
    }

    /**
     * 自动生成的getAndSet
     */
    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Date getBirthdayTime() {
        return birthdayTime;
    }

    public void setBirthdayTime(Date birthdayTime) {
        this.birthdayTime = birthdayTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public CurrencyPost getCurrencyPost() {
        return currencyPost;
    }

    public void setCurrencyPost(CurrencyPost currencyPost) {
        this.currencyPost = currencyPost;
    }

    public Double getDeserve() {
        return deserve;
    }

    public void setDeserve(Double deserve) {
        this.deserve = deserve;
    }

    public Date getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Date remainTime) {
        this.remainTime = remainTime;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
