package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2015-12-10.
 * 在店户籍表
 */
public class CheckIn extends BaseEntity{
    private String roomId;//房号
    private String roomCategory;//房类
    private String selfAccount;//自付帐号
    private String groupAccount;//公付帐号
    private Date reachTime;//来店时间
    private Date leaveTime;//预计离店时间
    private String guestSource;//客源代码
    private String important;//特殊要求
    private Boolean vip;//贵宾标志
    private String breakfast;//早餐类型
    private String remark;//备注
    private Double finalRoomPrice;//开房房价
    private String protocol;//协议价格编码
    private String company;//协议价格编码
    private String vipNumber;//会员编号
    private Double deposit;//押金
    private Double consume;//消费
    private Double pay;//已经支付的金额（用于中间结算）
    private String roomPriceCategory;//房租方式 hour---小时房，day---日租房
    private String userId;//操作员号
    @Transient
    private String currency;//押金币种，其实没啥用，只是在开房时用到，因为后期补交押金可能会导致跟之前的币种不一样的情况发生

    public CheckIn() {
    }

    public CheckIn(CheckInHistoryLog checkInHistoryLog){
        this.roomId=checkInHistoryLog.getRoomId();
        this.roomCategory=checkInHistoryLog.getRoomCategory();
        this.selfAccount=checkInHistoryLog.getSelfAccount();
        this.groupAccount=checkInHistoryLog.getGroupAccount();
        this.reachTime=checkInHistoryLog.getReachTime();
        this.leaveTime=checkInHistoryLog.getLeaveTime();
        this.guestSource=checkInHistoryLog.getGuestSource();
        this.important=checkInHistoryLog.getImportant();
        this.vip=checkInHistoryLog.getVip();
        this.breakfast=checkInHistoryLog.getBreakfast();
        this.remark=checkInHistoryLog.getRemark();
        this.finalRoomPrice=checkInHistoryLog.getFinalRoomPrice();
        this.protocol=checkInHistoryLog.getProtocol();
        this.company=checkInHistoryLog.getCompany();
        this.vipNumber=checkInHistoryLog.getVipNumber();
        this.deposit=checkInHistoryLog.getDeposit();
        this.consume=checkInHistoryLog.getConsume();
        this.pay=checkInHistoryLog.getPay();
        this.roomPriceCategory=checkInHistoryLog.getRoomPriceCategory();
        this.userId=checkInHistoryLog.getUserId();
    }

    /**
     * 获得非空的Double
     */
    public Double getNotNullDeposit() {
        return NullJudgement.nullToZero(deposit);
    }

    public Double getNotNullConsume() {
        return NullJudgement.nullToZero(consume);
    }

    public Double getNotNullPay() {
        return NullJudgement.nullToZero(pay);
    }

    public Double getNotNullFinalRoomPrice() {
        return NullJudgement.nullToZero(finalRoomPrice);
    }

    /**
     * 自动生成的getAndSet在下边
     */
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public String getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(String selfAccount) {
        this.selfAccount = selfAccount;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getFinalRoomPrice() {
        return finalRoomPrice;
    }

    public void setFinalRoomPrice(Double finalRoomPrice) {
        this.finalRoomPrice = finalRoomPrice;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
