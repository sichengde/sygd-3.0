package com.sygdsoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sygdsoft.jsonModel.CurrencyPost;
import com.sygdsoft.jsonModel.PrintMessage;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

/**
 * Created by 舒展 on 2016-07-14.
 */
@RestController
public class DeskController {
    @Autowired
    DeskService deskService;
    @Autowired
    ReportService reportService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    SerialService serialService;
    @Autowired
    DeskInService deskInService;
    @Autowired
    DeskInHistoryService deskInHistoryService;
    @Autowired
    DeskDetailService deskDetailService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    UserService userService;
    @Autowired
    DeskPayService deskPayService;
    @Autowired
    DebtPayService debtPayService;
    @Autowired
    SaleCountService saleCountService;
    @Autowired
    HotelService hotelService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    DeskBookService deskBookService;
    @Autowired
    DeskBookHistoryService deskBookHistoryService;
    @Autowired
    BookMoneyService bookMoneyService;
    @Autowired
    CookRoomService cookRoomService;
    @Autowired
    DebtService debtService;
    @Autowired
    DeskControllerService deskControllerService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "deskAdd")
    public void deskAdd(@RequestBody Desk desk) throws Exception {
        deskService.add(desk);
    }

    @RequestMapping(value = "deskDelete")
    @Transactional(rollbackFor = Exception.class)
    public void deskDelete(@RequestBody List<Desk> deskList) throws Exception {
        deskService.delete(deskList);
    }

    @RequestMapping(value = "deskUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void deskUpdate(@RequestBody List<Desk> deskList) throws Exception {
        if (deskList.size() > 1) {
            if (deskList.get(0).getId().equals(deskList.get(deskList.size() / 2).getId())) {
                deskService.update(deskList.subList(0, deskList.size() / 2));
                return;
            }
        }
        deskService.update(deskList);
    }

    @RequestMapping(value = "deskGet")
    public List<Desk> deskGet(@RequestBody Query query) throws Exception {
        List<Desk> deskList = deskService.get(query);
        deskService.setDeskDetail(deskList, new Date());
        return deskList;
    }

    @RequestMapping(value = "deskGetByDate")
    public List<Desk> deskGetByDate(@RequestBody POSAndDate posAndDate) {
        String pointOfSale = posAndDate.getPointOfSale();
        Date date = posAndDate.getDate();
        List<Desk> deskList = deskService.getByPointOfSale(pointOfSale);
        deskService.setDeskDetail(deskList, date);
        return deskList;
    }

    /**
     * 餐饮结账
     *
     * @return 结账单报表
     */
    @RequestMapping(value = "deskOut")
    @Transactional(rollbackFor = Exception.class)
    public Integer deskOut(@RequestBody DeskOut deskOut) throws Exception {
        timeService.setNow();
        serialService.setCkSerial();
        String desk = deskOut.getDesk();
        String pointOfSale = deskOut.getPointOfSale();
        Double discount = deskOut.getDiscount();
        if(discount==null){
            discount=1.0;
        }
        Double finalPrice = deskOut.getFinalPrice();
        List<CurrencyPost> currencyPostList = deskOut.getCurrencyPostList();
        DeskBook deskBook = deskOut.getDeskBook();
        List<DeskPay> deskPayList = new ArrayList<>();
        /*生成结账信息*/
        String changeDebt = "";//转账信息
        for (CurrencyPost currencyPost : currencyPostList) {
            this.generateDeskPay(pointOfSale, currencyPost, deskPayList);
            String currency = currencyPost.getCurrency();
            String currencyAdd = currencyPost.getCurrencyAdd();
            Double money = currencyPost.getMoney();
            changeDebt += " 币种:" + currency + "/" + money;
            changeDebt += debtPayService.parseCurrency(currency, currencyAdd, money, null, null, "餐饮结账", serialService.getCkSerial(), "餐饮", pointOfSale);
        }
        deskPayService.add(deskPayList);
        /*餐桌信息转移到历史*/
        DeskIn deskIn = deskService.getByDesk(desk, pointOfSale);
        DeskInHistory deskInHistory = new DeskInHistory(deskIn);
        deskInHistory.setDiscount(discount);
        deskInHistory.setFinalPrice(finalPrice);
        deskInHistory.setDoneTime(timeService.getNow());
        deskInHistory.setCkSerial(serialService.getCkSerial());
        deskInService.delete(deskIn);
        deskInHistoryService.add(deskInHistory);
        /*处理报表*/
        List<FieldTemplate> templateList = new ArrayList<>();
        if ("y".equals(otherParamService.getValueByName("菜品聚合"))) {
            deskControllerService.generateDetail(deskDetailService.getListByDeskGroup(desk, pointOfSale), templateList);
        } else {
            deskControllerService.generateDetail(deskDetailService.getListByDesk(desk, pointOfSale, null), templateList);
        }
        /*菜品明细转移到历史*/
        List<DeskDetail> deskDetailList = deskDetailService.getListByDesk(desk, pointOfSale, "category,do_time");
        List<DeskDetailHistory> deskDetailHistoryList = new ArrayList<>();
        for (DeskDetail deskDetail : deskDetailList) {
            DeskDetailHistory deskDetailHistory = new DeskDetailHistory(deskDetail);
            deskDetailHistory.setCkSerial(serialService.getCkSerial());
            deskDetailHistory.setAfterDiscount(deskDetailHistory.getTotal() * discount);
            deskDetailHistory.setDoneTime(timeService.getNow());
            deskDetailHistoryList.add(deskDetailHistory);
        }
        deskDetailHistoryService.add(deskDetailHistoryList);
        deskDetailService.delete(deskDetailList);
        /*处理预定*/
        if (deskBook != null) {
            DeskBookHistory deskBookHistory = new DeskBookHistory(deskBook);
            deskBookService.delete(deskBook);
            deskBookHistoryService.add(deskBookHistory);
            if (deskBook.getBookMoneyList() != null) {//退掉订金
                for (BookMoney bookMoney : deskBook.getBookMoneyList()) {
                    bookMoneyService.addSubscription(bookMoney.getBookSerial(), -bookMoney.getSubscription(), bookMoney.getCurrency());
                }
            }
        }

        /*生成操作员日志*/
        userLogService.addUserLog(desk + "台结算", userLogService.desk, userLogService.deskOut, desk);
        /*处理报表
        * param
        * 1.酒店名称
        * 2.结账序列号
        * 3.结算币种信息(转账信息)
        * 4.合计
        * 5.折扣
        * 6.折后
        * 7.桌台号
        * field
        * 1.菜品
        * 2.单价
        * 3.数量
        * 4.小计
        * */
        String[] parameters = new String[]{otherParamService.getValueByName("酒店名称"), serialService.getCkSerial(), changeDebt, ifNotNullGetString(deskIn.getConsume()), ifNotNullGetString(discount), ifNotNullGetString(finalPrice), desk};
        return reportService.generateReport(templateList, parameters, "deskOut", "pdf");
    }

    /**
     * 餐饮预结单
     */
    @RequestMapping(value = "deskPrintBefore")
    @Transactional(rollbackFor = Exception.class)
    public Integer deskPrintBefore(@RequestBody DeskOut deskOut) throws Exception {
        String desk = deskOut.getDesk();
        String pointOfSale = deskOut.getPointOfSale();
        DeskIn deskIn = deskService.getByDesk(desk, pointOfSale);
        Double discount = deskOut.getDiscount();
        Double finalPrice = deskOut.getFinalPrice();
        List<DeskDetail> deskDetailList;
        if (deskOut.getDeskDetailList() != null) {
            deskDetailList = deskOut.getDeskDetailList();
        } else {
            if ("y".equals(otherParamService.getValueByName("菜品聚合"))) {
                deskDetailList = deskDetailService.getListByDeskGroup(desk, pointOfSale);
            } else {
                deskDetailList = deskDetailService.getListByDesk(desk, pointOfSale, null);
            }
        }
        List<FieldTemplate> templateList = new ArrayList<>();
        deskControllerService.generateDetail(deskDetailList, templateList);
        /*处理报表
        * param
        * 1.酒店名称
        * 2.结账序列号
        * 3.结算币种信息(转账信息)
        * 4.合计
        * 5.折扣
        * 6.折后
        * 7.桌台号
        * field
        * 1.菜品
        * 2.单价
        * 3.数量
        * 4.小计
        * */
        String[] parameters = new String[]{otherParamService.getValueByName("酒店名称"), null, null, ifNotNullGetString(deskIn.getConsume()), ifNotNullGetString(discount), ifNotNullGetString(finalPrice), desk};
        return reportService.generateReport(templateList, parameters, "deskOut", "pdf");
    }


    /**
     * 餐饮补打结账单
     */
    @RequestMapping(value = "deskOutPrintAgain")
    @Transactional(rollbackFor = Exception.class)
    public Integer deskOutPrintAgain(@RequestBody String ckSerial) throws Exception {
        DeskInHistory deskInHistory = deskInHistoryService.getByCkSerial(ckSerial);
        /*消费明细*/
        List<FieldTemplate> templateList = new ArrayList<>();
        List<DeskDetailHistory> deskDetailHistoryList = deskDetailHistoryService.getList(ckSerial, "category,do_time");
        deskControllerService.generateDetailHistory(deskDetailHistoryList, templateList);
        /*生成结账信息*/
        String changeDebt = "";//转账信息
        List<DeskPay> deskPayList = deskPayService.getByCkSerial(ckSerial);
        for (DeskPay deskPay : deskPayList) {
            changeDebt += " 币种:" + deskPay.getCurrency() + "/" + deskPay.getPayMoney() + '/';
            String currencyAdd = deskPay.getCurrencyAdd();
            if (currencyAdd != null) {
                changeDebt += currencyAdd;
            }
        }
        String[] parameters = new String[]{otherParamService.getValueByName("酒店名称"), deskInHistory.getCkSerial(), changeDebt, ifNotNullGetString(deskInHistory.getTotalPrice()), ifNotNullGetString(deskInHistory.getDiscount()), ifNotNullGetString(deskInHistory.getFinalPrice()), deskInHistory.getDesk()};
        return reportService.generateReport(templateList, parameters, "deskOut", "pdf");
    }

    /**
     * 叫回餐饮结账单
     * 返回值1.有客人
     */
    @RequestMapping(value = "deskOutReverse")
    @Transactional(rollbackFor = Exception.class)
    public Integer deskOutReverse(@RequestBody DeskInHistory deskInHistory) throws Exception {
        String desk = deskInHistory.getDesk();
        String pointOfSale = deskInHistory.getPointOfSale();
        String ckSerial = deskInHistory.getCkSerial();
        /*判断该桌有没有客人，没有客人才可以叫回*/
        if (deskInService.getByDesk(desk, pointOfSale) != null) {
            return 1;
        }
        /*增加餐桌信息*/
        deskInService.add(new DeskIn(deskInHistory));
        deskInHistoryService.delete(deskInHistory);
        /*增加点菜明细*/
        List<DeskDetail> deskDetailList = new ArrayList<>();
        List<DeskDetailHistory> deskDetailHistoryList = deskDetailHistoryService.getList(ckSerial, null);
        for (DeskDetailHistory deskDetailHistory : deskDetailHistoryList) {
            deskDetailList.add(new DeskDetail(deskDetailHistory));
        }
        deskDetailService.add(deskDetailList);
        deskDetailHistoryService.delete(deskDetailHistoryList);
        List<DeskPay> deskPayList = deskPayService.getByCkSerial(deskInHistory.getCkSerial());
        for (DeskPay deskPay : deskPayList) {
            debtPayService.cancelPay(deskPay.getCurrency(), deskPay.getCurrencyAdd(), deskPay.getPayMoney(), deskPay.getCkSerial(), "餐饮", pointOfSale);
        }
        /*删除结账信息*/
        deskPayService.deleteByCkSerial(ckSerial);
        deskPayService.setDisabledBySerial(deskInHistory.getCkSerial());//明细数据设置为不可用
        /*操作员日志记录*/
        userLogService.addUserLog("叫回餐饮账单:" + ckSerial, userLogService.desk, userLogService.deskOutReverse, ckSerial);
        return 0;
    }

    /**
     * 自助餐直接入账
     */
    @RequestMapping(value = "buffetPay")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Integer buffetPay(@RequestBody BuffetPost buffetPost) throws Exception {
        timeService.setNow();
        serialService.setCkSerial();
        String pointOfSale = buffetPost.getPointOfSale();
        Menu menu = buffetPost.getMenu();
        List<CurrencyPost> currencyPostList = buffetPost.getCurrencyPostList();
        Integer num = buffetPost.getNum();
        List<DeskPay> deskPayList = new ArrayList<>();
        /*生成结账信息，自助餐不允许分单*/
        CurrencyPost currencyPost = currencyPostList.get(0);
        String currency = currencyPost.getCurrency();
        String currencyAdd = currencyPost.getCurrencyAdd();
        Double money = menu.getPrice() * num;
        currencyPost.setMoney(money);
        String changeDebt = debtPayService.parseCurrency(currency, currencyAdd, money, null, null, "自助餐", serialService.getCkSerial(), "餐饮", "自助餐");
        this.generateDeskPay(pointOfSale, currencyPost, deskPayList);
        deskPayService.add(deskPayList);
        /*餐桌信息转移到历史*/
        DeskInHistory deskInHistory = new DeskInHistory();
        deskInHistory.setDesk("自助餐");
        deskInHistory.setDoTime(timeService.getNow());
        deskInHistory.setTotalPrice(money);
        deskInHistory.setFinalPrice(money);
        deskInHistory.setDoneTime(timeService.getNow());
        deskInHistory.setCkSerial(serialService.getCkSerial());
        deskInHistory.setUserId(userService.getCurrentUser());
        deskInHistory.setPointOfSale(pointOfSale);
        deskInHistory.setNum(num);
        deskInHistoryService.add(deskInHistory);
        /*菜品明细转移到历史*/
        DeskDetailHistory deskDetailHistory = new DeskDetailHistory();
        deskDetailHistory.setCkSerial(serialService.getCkSerial());
        deskDetailHistory.setFoodName(menu.getName());
        deskDetailHistory.setPrice(menu.getPrice());
        deskDetailHistory.setNum(Double.valueOf(num));
        deskDetailHistory.setDesk("自助餐");
        deskDetailHistory.setUserId(userService.getCurrentUser());
        deskDetailHistory.setPointOfSale(pointOfSale);
        deskDetailHistory.setDoTime(timeService.getNow());
        deskDetailHistory.setDoneTime(timeService.getNow());
        deskDetailHistory.setFoodSign(menu.getName());
        deskDetailHistory.setTotal(money);
        deskDetailHistory.setAfterDiscount(menu.getPrice());
        deskDetailHistory.setIfDiscount(false);
        deskDetailHistory.setCategory(menu.getCategory());
        deskDetailHistoryService.add(deskDetailHistory);
        /*获取打印机*/
        if (menu.getCookRoom() != null) {//有厨房定义的话就进行厨房打印
            List<CookRoom> cookRoomList = cookRoomService.getByCookName(menu.getCookRoom().split(" "));
            for (CookRoom cookRoom : cookRoomList) {
            /*打印参数
            * 1.酒店名称
            * 2.流水号
            * 3.单价
            * 4.数量
            * 5.结转信息changeDebt
            * 6.菜名
            * 7.币种
            * 8.结转信息
            * */
                List<String> paramList = new ArrayList<>();
                paramList.add(otherParamService.getValueByName("酒店名称"));
                paramList.add(serialService.getCkSerial());
                paramList.add(String.valueOf(menu.getPrice()));
                paramList.add(String.valueOf(num));
                paramList.add(String.valueOf(money));
                paramList.add(menu.getName());
                paramList.add(currency);
                paramList.add(changeDebt);
                for (int i = 0; i < cookRoom.getNum(); i++) {
                    if (cookRoom.getNotNullUPort()) {//远程u口打印
                        PrintMessage printMessage = new PrintMessage(null, cookRoom.getPrinter(), "buffet", paramList);
                        ObjectMapper mapper = new ObjectMapper();
                        hotelService.postJSON("http://" + cookRoom.getPrinterIp() + "/printClient", mapper.writeValueAsString(printMessage));
                    } else {//网口打印
                        reportService.printBuffet(cookRoom.getPrinter(), paramList);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 自助餐冲减
     */
    @RequestMapping(value = "buffetCancel")
    @Transactional(rollbackFor = Exception.class)
    public Integer buffetCancel(@RequestBody List<DeskInHistory> deskInHistoryList) throws Exception {
        timeService.getNow();
        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        Double totalMoney = 0.0;
        Integer totalNum = 0;
        deskInHistoryService.setDisabled(deskInHistoryList);//先把原始数据设置为冲减了
        String serialString = "";
        for (DeskInHistory deskInHistory : deskInHistoryList) {
            deskDetailHistoryService.setDisabledBySerial(deskInHistory.getCkSerial());//明细数据设置为不可用
            deskPayService.setDisabledBySerial(deskInHistory.getCkSerial());//明细数据设置为不可用
            List<DeskPay> deskPayList = deskPayService.getByCkSerial(deskInHistory.getCkSerial());
            for (DeskPay deskPay : deskPayList) {
                String currency = deskPay.getCurrency();
                String currencyAdd = deskPay.getCurrencyAdd();
                Double money = deskPay.getPayMoney();
                debtPayService.cancelPay(currency, currencyAdd, money, deskPay.getCkSerial(), "餐饮", "自助餐");
            }
            /*处理报表数据*/
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(deskInHistory.getCkSerial());
            fieldTemplate.setField2(timeService.dateToStringLong(deskInHistory.getDoTime()));
            fieldTemplate.setField3(ifNotNullGetString(deskInHistory.getNum()));
            fieldTemplate.setField4(ifNotNullGetString(deskInHistory.getFinalPrice()));
            fieldTemplateList.add(fieldTemplate);
            totalNum += deskInHistory.getNum();
            totalMoney += deskInHistory.getFinalPrice();
            serialString += deskInHistory.getCkSerial() + ",";
        }
        serialString = serialString.substring(0, serialString.length() - 1);
        userLogService.addUserLog("自助餐冲减:" + totalNum + "份," + "金额:" + totalMoney, userLogService.desk, "冲减", serialString);
        /*
        * 报表参数
        * 1.酒店名称
        * 2.操作员
        * 3.合计人数
        * 4.合计金额
        * */
        String[] params = new String[]{otherParamService.getValueByName("酒店名称"), userService.getCurrentUser(), ifNotNullGetString(totalNum), ifNotNullGetString(totalMoney)};
        return reportService.generateReport(fieldTemplateList, params, "buffetCancel", "pdf");
    }

    private void generateDeskPay(String pointOfSale, CurrencyPost currencyPost, List<DeskPay> deskPayList) throws Exception {
        DeskPay deskPay = new DeskPay();
        deskPay.setCkSerial(serialService.getCkSerial());
        deskPay.setDoneTime(timeService.getNow());
        deskPay.setPointOfSale(pointOfSale);
        deskPay.setCurrency(currencyPost.getCurrency());
        deskPay.setCurrencyAdd(currencyPost.getCurrencyAdd());
        deskPay.setUserId(userService.getCurrentUser());
        deskPay.setPayMoney(currencyPost.getMoney());
        deskPayList.add(deskPay);
    }
}
