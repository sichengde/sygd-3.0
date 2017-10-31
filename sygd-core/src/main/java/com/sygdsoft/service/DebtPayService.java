package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.DebtPayMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "debtPay")
public class DebtPayService extends BaseService<DebtPay> {
    public String ldjs = "离店结算";
    public String yfjs = "哑房结算";
    public String zjjszdzw = "中间结算指定账务";
    public String zjjsbzdzw = "中间结算不指定账务";
    @Autowired
    DebtPayMapper debtPayMapper;
    @Autowired
    SerialService serialService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    RoomService roomService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    UserService userService;
    @Autowired
    Util util;
    @Autowired
    DebtService debtService;
    @Autowired
    VipDetailService vipDetailService;
    @Autowired
    VipService vipService;
    @Autowired
    CompanyDebtService companyDebtService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CompanyLordService companyLordService;
    @Autowired
    FreemanService freemanService;
    @Autowired
    FreeDetailService freeDetailService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    SzMath szMath;
    @Autowired
    TimeService timeService;

    /**
     * 查找结账记录
     */
    /*自付账号*/
    public List<DebtPay> getListBySelfAccount(String selfAccount) {
        DebtPay debtPayQuery = new DebtPay();
        debtPayQuery.setSelfAccount(selfAccount);
        return debtPayMapper.select(debtPayQuery);
    }

    /*结账序列号*/
    public List<DebtPay> getListByPaySerial(String paySerial) {
        DebtPay debtPayQuery = new DebtPay();
        debtPayQuery.setPaySerial(paySerial);
        return debtPayMapper.select(debtPayQuery);
    }

    /*时间和币种*/
    public List<DebtPay> getList(String userId, String currency, Date beginTime, Date endTime, String orderByList) {
        return debtPayMapper.getList(userId, currency, beginTime, endTime, orderByList);
    }

    /**
     * 获得该日期该币种的消费额
     */
    public Double getDebtMoney(String userId, String currency, Boolean payTotal, Date beginTime, Date endTime) {
        return debtPayMapper.getDebtMoney(userId, currency, payTotal, beginTime, endTime);
    }

    /**
     * 分析处理结账（包括分单币种）
     *
     * @param currency
     * @param currencyAdd
     * @param money
     * @param roomList     离店结算时的房号
     * @param groupAccount 离店结算时的公付账号
     */
    public String parseCurrency(String currency, String currencyAdd, Double money, List<String> roomList, String groupAccount, String description, String paySerial, String pointOfSale, String secondPointOfSale) throws Exception {
        String changeDebt = "";
        switch (currency) {
            case "转房客"://转房客，新建一条账务
                /*看看该客是不是在店*/
                if (checkInService.getByRoomId(currencyAdd) == null || currencyAdd == null) {
                    throw new Exception("房间号不存在或者没有开房");
                }
                Debt debt = new Debt();
                debt.setPointOfSale("挂账");
                debt.setConsume(money);
                debt.setCurrency("挂账");
                debt.setRoomId(currencyAdd);
                debt.setUserId(userService.getCurrentUser());
                debt.setDescription(roomService.roomListToString(roomList) + "挂账");
                debt.setFromRoom(paySerial);
                if (paySerial.contains("ck")) {
                    debt.setCategory("餐饮挂账");
                } else if (paySerial.contains("p")) {
                    debt.setCategory("客房挂账");
                }
                debtService.addDebt(debt);
                changeDebt += " 转房客至:" + currencyAdd;
                break;//不转移账务明细
            case "转哑房"://转哑房
                Debt debt2 = new Debt();
                debt2.setPointOfSale("挂账");
                debt2.setConsume(money);
                debt2.setCurrency("挂账");
                debt2.setRoomId("哑房");
                debt2.setUserId(userService.getCurrentUser());
                debt2.setDescription(roomService.roomListToString(roomList) + "哑房挂账");
                debt2.setFromRoom(paySerial);
                debt2.setCategory("哑房挂账");
                debt2.setDoTime(new Date());
                debtService.add(debt2);
                changeDebt += " 转为哑房";
                break;
            case "会员"://会员
                String vipNumber;
                String payCategory;
                try {
                    vipNumber = currencyAdd.split(" ")[0];
                    payCategory = currencyAdd.split(" ")[1];
                } catch (Exception e) {
                    throw new Exception("请输入会员卡号和余额/积分结算");
                }
                if (groupAccount == null) {
                    if (roomList == null) {//不是客房结账
                        changeDebt += vipService.vipPay(vipNumber, payCategory, money, description, null, null, paySerial, pointOfSale);
                    } else {
                        CheckIn checkIn = checkInService.getByRoomId(roomList.get(0));//在店户籍
                        changeDebt += vipService.vipPay(vipNumber, payCategory, money, description, checkIn.getSelfAccount(), checkIn.getGroupAccount(), paySerial, pointOfSale);
                    }
                } else {
                    CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(groupAccount);
                    changeDebt += vipService.vipPay(vipNumber, payCategory, money, description, null, checkInGroup.getGroupAccount(), paySerial, pointOfSale);
                }
                break;
            case "转单位"://转单位
                String company;
                String lord;
                try {
                    company = currencyAdd.split(" ")[0];
                    lord = currencyAdd.split(" ")[1];
                } catch (Exception e) {
                    throw new Exception("请输入签单单位和签单人");
                }
                changeDebt += companyService.companyAddDebt(company, lord, money, description, pointOfSale, secondPointOfSale, paySerial);
                break;
            case "宴请"://转宴请
                String name;
                String reason;
                try {
                    name = currencyAdd.split(" ")[0];
                    reason = currencyAdd.split(" ")[1];
                } catch (Exception e) {
                    throw new Exception("请输入宴请人和原因");
                }
                changeDebt += freemanService.freePay(name, reason, money);
                break;
        }
        return changeDebt;
    }

    /**
     * 账单取消后退回币种
     */
    public void cancelPay(String currency, String currencyAdd, Double money, String serial, String pointOfSale) throws Exception {
        switch (currency) {
            case "转房客"://把转的金额取消
                debtService.deleteByCheckOutSerial(serial);//删除房账
                break;//不转移账务明细
            case "转哑房"://转哑房
                break;
            case "会员"://会员
                String vipNumber = currencyAdd.split(" ")[0];
                String payCategory = currencyAdd.split(" ")[1];
                vipService.vipPay(vipNumber, payCategory, -money, "叫回", null, null, serial, pointOfSale);
                break;
            case "转单位"://转单位
                String company = currencyAdd.split(" ")[0];
                String lord = currencyAdd.split(" ")[1];
                companyDebtService.deleteBySerial(serial);
                companyService.addDebt(company, -money);
                companyLordService.addDebt(lord, -money);
                break;
            case "宴请"://转宴请
                freemanService.addConsume(currencyAdd, -money);
                freeDetailService.deleteByPaySerial(serial);
                break;
        }
    }
}
