package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;
import org.bouncycastle.jce.provider.JCEECDHKeyAgreement;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 * 账务明细
 */
public class Debt extends BaseEntity {
    private Date doTime;//日期
    private String pointOfSale;//销售点
    private Double consume;//消费
    private Double deposit;//押金
    private String currency;//币种代码，只针对于押金
    private String description;//描述
    private String selfAccount;//自付帐号
    private String groupAccount;//公付帐号
    private String roomId;//房号
    private String paySerial;//结账序列号
    private String protocol;//协议代码
    private String userId;//操作员号
    private String remark;//备注
    private String bed;//床位号，针对查询客人消费
    private String vipNumber;//会员编号，针对于用会员余额作为押金的顾客
    private String category;//账务类别，便于在账务明细中筛选
    private String fromRoom;//从哪转入的，针对于转房客，主要用于如果是餐饮的话就是餐饮的结账序列号
    private String guestSource;//客源
    private String company;//单位
    private Double totalConsume;//录完该笔消费后消费合计
    private String guestName;//客人姓名
    private String sourceRoom;//原房号
    private Boolean back;//已退款标志(针对押金，以前是在remark中用字符串"已退"标记)
    private Boolean notPartIn;//不参与营业收入发生额等的统计

    @Transient
    private String area;

    public Debt() {
    }

    public Debt(Debt debt) {
        this.doTime = debt.getDoTime();
        this.pointOfSale = debt.getPointOfSale();
        this.consume = debt.getConsume();
        this.deposit = debt.getDeposit();
        this.currency = debt.getCurrency();
        this.description = debt.getDescription();
        this.selfAccount = debt.getSelfAccount();
        this.groupAccount = debt.getGroupAccount();
        this.roomId = debt.getRoomId();
        this.paySerial = debt.getPaySerial();
        this.protocol = debt.getProtocol();
        this.userId = debt.getUserId();
        this.remark = debt.getRemark();
        this.bed=debt.getBed();
        this.vipNumber=debt.getVipNumber();
        this.category=debt.getCategory();
        this.fromRoom=debt.getFromRoom();
        this.guestSource=debt.getGuestSource();
        this.company=debt.getCompany();
        this.guestName=debt.getGuestName();
        this.sourceRoom=debt.getSourceRoom();
        this.back=debt.getBack();
        this.notPartIn=debt.getBack();
    }

    public Debt(DebtHistory debtHistory){
        this.doTime = debtHistory.getDoTime();
        this.pointOfSale = debtHistory.getPointOfSale();
        this.consume = debtHistory.getConsume();
        this.deposit = debtHistory.getDeposit();
        this.currency = debtHistory.getCurrency();
        this.description = debtHistory.getDescription();
        this.selfAccount = debtHistory.getSelfAccount();
        this.groupAccount = debtHistory.getGroupAccount();
        this.roomId = debtHistory.getRoomId();
        this.paySerial = debtHistory.getPaySerial();
        this.protocol = debtHistory.getProtocol();
        this.userId = debtHistory.getUserId();
        this.remark = debtHistory.getRemark();
        this.bed=debtHistory.getBed();
        this.vipNumber=debtHistory.getVipNumber();
        this.category=debtHistory.getCategory();
        this.fromRoom=debtHistory.getFromRoom();
        this.guestSource=debtHistory.getGuestSource();
        this.company=debtHistory.getCompany();
        this.guestName=debtHistory.getGuestName();
        this.sourceRoom=debtHistory.getSourceRoom();
        this.back=debtHistory.getBack();
        this.notPartIn=debtHistory.getNotPartIn();
    }

    public Double getNotNullDeposit(){
        return NullJudgement.nullToZero(deposit);
    }

    public Double getNotNullConsume(){
        return NullJudgement.nullToZero(consume);
    }

    /**
     * getAndSet方法
     */
    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFromRoom() {
        return fromRoom;
    }

    public void setFromRoom(String fromRoom) {
        this.fromRoom = fromRoom;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(Double totalConsume) {
        this.totalConsume = totalConsume;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getSourceRoom() {
        return sourceRoom;
    }

    public void setSourceRoom(String sourceRoom) {
        this.sourceRoom = sourceRoom;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getBack() {
        return back;
    }

    public void setBack(Boolean back) {
        this.back = back;
    }

    public Boolean getNotPartIn() {
        return notPartIn;
    }

    public void setNotPartIn(Boolean notPartIn) {
        this.notPartIn = notPartIn;
    }
}
