package com.sygdsoft.service;

import com.sygdsoft.mapper.SerialMapper;
import com.sygdsoft.model.Serial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-04-19.
 * 获取各种流水号
 */
@Service
public class SerialService {
    static public String ZHENG_CHANG_CO="常规流水";
    static public String FA_PIAO_CO="发票流水";
    @Autowired
    SerialMapper serialMapper;
    @Autowired
    TimeService timeService;
    private Serial serial;
    private String paySerial;//结账序列号
    private String bookSerial;//预订单号
    private String selfAccount;//接待自付账号
    private String groupAccount;//接待公付账号
    private String checkOutSerial;//离店结算序列号
    private String checkOutSerialFp;//离店结算序列号发票
    private String ckSerial;//餐饮结算序列号
    private String deskBookSerial;//餐饮预定序列号
    private String storageOutSerial;//库存出库序列号
    private String storageInSerial;//库存入库序列号
    private String saunaGroupSerial;//桑拿结算序列号
    private String saunaOutSerial;//桑拿团队手牌序列号
    private String companyPaySerial;//单位结算序列号

    public SerialService() {
    }

    public String getPaySerial() {
        return paySerial;
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public String getSelfAccount() {
        return selfAccount;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }
    public String getCheckOutSerialFp() {
        return checkOutSerialFp;
    }

    public String getCkSerial() {
        return ckSerial;
    }

    public String getDeskBookSerial() {
        return deskBookSerial;
    }

    public String getStorageOutSerial() {
        return storageOutSerial;
    }

    public String getStorageInSerial() {
        return storageInSerial;
    }

    public String getSaunaGroupSerial() {
        return saunaGroupSerial;
    }

    public String getSaunaOutSerial() {
        return saunaOutSerial;
    }

    public String getCompanyPaySerial() {
        return companyPaySerial;
    }

    public String setPaySerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.paySerial = "p" + timeService.getSerialShort() + serial.getPaySerial();
        serial.setPaySerial(String.format("%03d", Integer.valueOf(serial.getPaySerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String setBookSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.bookSerial = "b" + timeService.getSerialShort() + serial.getBookSerial();
        serial.setBookSerial(String.format("%03d", Integer.valueOf(serial.getBookSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return bookSerial;
    }

    public String setSelfAccount() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.selfAccount = "s" + timeService.getSerialShort() + serial.getSelfAccount();
        serial.setSelfAccount(String.format("%03d", Integer.valueOf(serial.getSelfAccount()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return selfAccount;
    }

    public String setGroupAccount() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.groupAccount = "g" + timeService.getSerialShort() + serial.getGroupAccount();
        serial.setGroupAccount(String.format("%03d", Integer.valueOf(serial.getGroupAccount()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        timeService.setNow();
        this.groupAccount = groupAccount;
    }

    public String setCheckOutSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.checkOutSerial = "c" + timeService.getSerialShort() + serial.getCheckOutSerial();
        serial.setCheckOutSerial(String.format("%03d", Integer.valueOf(serial.getCheckOutSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return checkOutSerial;
    }
    public String setCheckOutSerialFp() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.checkOutSerialFp = "c" + timeService.getSerialShort() + serial.getCheckOutSerialFp();
        serial.setCheckOutSerialFp(String.format("%04d", Integer.valueOf(serial.getCheckOutSerialFp()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return checkOutSerialFp;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }
    public void setCheckOutSerialFp(String checkOutSerialFp) {
        this.checkOutSerialFp = checkOutSerialFp;
    }

    public void setDeskBookSerial(String deskBookSerial) {
        this.deskBookSerial = deskBookSerial;
    }

    public String setDeskBookSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.deskBookSerial = "db" + timeService.getSerialShort() + serial.getDeskBookSerial();
        serial.setDeskBookSerial(String.format("%03d", Integer.valueOf(serial.getDeskBookSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return deskBookSerial;
    }

    public void setCkSerial(String ckSerial) {
        this.ckSerial = ckSerial;
    }

    public String setCkSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.ckSerial = "ck" + timeService.getSerialShort() + serial.getCkSerial();
        serial.setCkSerial(String.format("%03d", Integer.valueOf(serial.getCkSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return ckSerial;
    }

    public String setStorageOutSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.storageOutSerial = "sto" + timeService.getSerialShort() + serial.getStorageOutSerial();
        serial.setStorageOutSerial(String.format("%03d", Integer.valueOf(serial.getStorageOutSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return storageOutSerial;
    }

    public void setStorageOutSerial(String storageOutSerial) {
        this.storageOutSerial = storageOutSerial;
    }

    public String setStorageInSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.storageInSerial = "sti" + timeService.getSerialShort() + serial.getStorageInSerial();
        serial.setStorageInSerial(String.format("%03d", Integer.valueOf(serial.getStorageInSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return storageInSerial;
    }

    public void setStorageInSerial(String storageInSerial) {
        this.storageInSerial = storageInSerial;
    }

    public String setSaunaOutSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.saunaOutSerial = "sno" + timeService.getSerialShort() + serial.getSaunaOutSerial();
        serial.setSaunaOutSerial(String.format("%03d", Integer.valueOf(serial.getSaunaOutSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return saunaOutSerial;
    }

    public String setCompanyPaySerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.companyPaySerial = "cp" + timeService.getSerialShort() + serial.getCompanyPaySerial();
        serial.setCompanyPaySerial(String.format("%03d", Integer.valueOf(serial.getCompanyPaySerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return companyPaySerial;
    }

    public void setSaunaOutSerial(String saunaOutSerial) {
        this.saunaOutSerial = saunaOutSerial;
    }

    public void setCompanyPaySerial(String companyPaySerial) {
        this.companyPaySerial = companyPaySerial;
    }

    public String setSaunaGroupSerial() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        this.saunaGroupSerial = "sti" + timeService.getSerialShort() + serial.getSaunaGroupSerial();
        serial.setSaunaGroupSerial(String.format("%03d", Integer.valueOf(serial.getSaunaGroupSerial()) + 1));
        serialMapper.updateByPrimaryKey(serial);
        return saunaGroupSerial;
    }

    public void setSaunaGroupSerial(String saunaGroupSerial) {
        this.saunaGroupSerial = saunaGroupSerial;
    }

    /**
     * 不能直接设置为001，要判断当日最大序列号
     * 否则如果，由于夜审做晚了，手动夜审之后流水号会冲突
     */
    void setAllToOne() {
        timeService.setNow();
        serial = serialMapper.selectAll().get(0);
        serial.setBookSerial("001");
        serial.setCheckOutSerial("001");
        serial.setCheckOutSerialFp("0001");
        serial.setGroupAccount("001");
        serial.setSelfAccount("001");
        serial.setPaySerial("001");
        serial.setCkSerial("001");
        serial.setDeskBookSerial("001");
        serial.setStorageInSerial("001");
        serial.setStorageOutSerial("001");
        serial.setSaunaOutSerial("001");
        serial.setCompanyPaySerial("001");
        serial.setSaunaGroupSerial("001");
        serialMapper.updateByPrimaryKey(serial);
    }
}
