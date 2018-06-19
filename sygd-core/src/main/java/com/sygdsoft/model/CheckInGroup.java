package com.sygdsoft.model;

import com.sygdsoft.util.NullJudgement;

import java.util.Date;

/**
 * Created by 舒展 on 2016-05-02.
 */
public class CheckInGroup extends BaseEntity {
    private String groupAccount;//共付账号
    private String name;//团名
    private String leader;//领队姓名
    private String leaderRoom;//领队房号
    private String phone;//领队电话
    private String currency;//押金币种
    private String vipNumber;//会员卡号
    private Date reachTime;//来店时间
    private Date leaveTime;//离店时间
    private Double deposit;//团队押金
    private Double consume;//团队消费
    private Double pay;//团队已经支付
    private String company;//隶属单位代码
    private String lord;//单位签单人代码
    private String userId;//操作员号
    private String guestSource;//客源
    private String roomPriceCategory;//房租方式
    private String protocol;//房价协议
    private Integer totalRoom;//房价协议
    private String remark;//备注

    public CheckInGroup() {
    }
    public CheckInGroup(CheckOutGroup checkOutGroup){
        this.groupAccount=checkOutGroup.getGroupAccount();
        this.name=checkOutGroup.getName();
        this.reachTime=checkOutGroup.getReachTime();
        this.leaveTime=checkOutGroup.getLeaveTime();
        this.leader=checkOutGroup.getLeader();
        this.leaderRoom=checkOutGroup.getLeaderRoom();
        this.phone=checkOutGroup.getPhone();
        this.vipNumber=checkOutGroup.getVipNumber();
        this.currency=checkOutGroup.getCurrency();
        this.deposit=checkOutGroup.getDeposit();
        this.consume=checkOutGroup.getConsume();
        this.pay=checkOutGroup.getPay();
        this.company=checkOutGroup.getCompany();
        this.lord=checkOutGroup.getLord();
        this.userId=checkOutGroup.getUserId();
        this.guestSource=checkOutGroup.getGuestSource();
        this.roomPriceCategory=checkOutGroup.getRoomPriceCategory();
        this.protocol=checkOutGroup.getProtocol();
        this.totalRoom=checkOutGroup.getTotalRoom();
        this.remark=checkOutGroup.getRemark();
    }
    /**
     * 获得非空的Double
     */
    public Double getNotNullGroupDeposit() {
        return NullJudgement.nullToZero(deposit);
    }

    public Double getNotNullGroupConsume() {
        return NullJudgement.nullToZero(consume);
    }

    public Double getNotNullGroupPay() {
        return NullJudgement.nullToZero(pay);
    }
    /**
     * 自动生成的getAndSet
     */
    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderRoom() {
        return leaderRoom;
    }

    public void setLeaderRoom(String leaderRoom) {
        this.leaderRoom = leaderRoom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLord() {
        return lord;
    }

    public void setLord(String lord) {
        this.lord = lord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Integer totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
