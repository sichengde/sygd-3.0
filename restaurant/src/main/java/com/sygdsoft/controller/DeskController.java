package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
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
    @Autowired
    DeskInCancelAllService deskInCancelAllService;
    @Autowired
    DeskDetailCancelAllService deskDetailCancelAllService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    PayPointOfSaleService payPointOfSaleService;

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

    @RequestMapping(value = "changeDesk")
    public void changeDesk(@RequestBody JSONObject jsonObject) throws Exception {
        String sourceDesk=jsonObject.getString("sourceDesk");
        String targetDesk=jsonObject.getString("targetDesk");
        String pointOfSale=jsonObject.getString("pointOfSale");
        DeskIn deskIn=deskInService.getByDesk(sourceDesk,pointOfSale);
        deskIn.setDesk(targetDesk);
        deskInService.update(deskIn);
        List<DeskDetail> deskDetailList=deskDetailService.getListByDesk(sourceDesk, pointOfSale, null,null);
        for (DeskDetail deskDetail : deskDetailList) {
            deskDetail.setDesk(targetDesk);
        }
        deskDetailService.update(deskDetailList);
        userLogService.addUserLog(sourceDesk+"->"+targetDesk, userLogService.desk, userLogService.deskChange,deskIn.getDesk());
    }

    @RequestMapping(value = "mergeDesk")
    @Transactional(rollbackFor = Exception.class)
    public void mergeDesk(@RequestBody JSONObject jsonObject) throws Exception {
        String sourceDesk=jsonObject.getString("sourceDesk");
        String targetDesk=jsonObject.getString("targetDesk");
        String pointOfSale=jsonObject.getString("pointOfSale");
        DeskIn deskInTarget=deskInService.getByDesk(targetDesk,pointOfSale);
        DeskIn deskInSource=deskInService.getByDesk(sourceDesk,pointOfSale);
        /*删除原桌台*/
        deskInService.delete(deskInSource);
        deskInTarget.setConsume(deskInTarget.getNotNullConsume()+deskInSource.getNotNullConsume());
        deskInTarget.setNum(deskInTarget.getNotNullNum()+deskInSource.getNotNullNum());
        deskInService.update(deskInTarget);
        List<DeskDetail> sourceDeskDetailList=deskDetailService.getListByDesk(sourceDesk, pointOfSale, null,null);
        for (DeskDetail deskDetail : sourceDeskDetailList) {
            deskDetail.setDesk(targetDesk);
            deskDetail.setFoodName(targetDesk+":"+deskDetail.getFoodName());
        }
        deskDetailService.update(sourceDeskDetailList);
        userLogService.addUserLog(sourceDesk+" 并入 "+targetDesk, userLogService.desk, userLogService.deskChange,sourceDesk);
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
        String guestSource=deskOut.getDeskGuestSource();
        Double discount = deskOut.getDiscount();
        if(discount==null){
            discount=1.0;
        }
        Double finalPrice = deskOut.getFinalPrice();
        List<CurrencyPost> currencyPostList = deskOut.getCurrencyPostList();
        DeskBook deskBook = deskOut.getDeskBook();
        List<DeskPay> deskPayList = new ArrayList<>();
        /*矫正结账金额*/
        Double consume = 0.0;
        List<DeskDetail> deskDetailList = deskDetailService.getListByDesk(desk, pointOfSale, "category,do_time",null);
        for (DeskDetail deskDetail : deskDetailList) {
            consume += deskDetail.getNotNullPrice() * deskDetail.getNum();
        }
        /*生成结账信息*/
        StringBuilder changeDebt = new StringBuilder();//转账信息
        for (CurrencyPost currencyPost : currencyPostList) {
            this.generateDeskPay(pointOfSale, currencyPost, deskPayList);
            String currency = currencyPost.getCurrency();
            String currencyAdd = currencyPost.getCurrencyAdd();
            Double money = currencyPost.getMoney();
            Currency currencyItem=currencyService.getByName(currency);
            String prepareToAdd=debtPayService.parseCurrency(currency, currencyAdd, money, null, null, desk + "餐饮结账", serialService.getCkSerial(), "餐饮", pointOfSale);
            prepareToAdd=prepareToAdd.split("签单人")[0];//不要签单人
            if(currencyItem.getNotNullShowInReport()) {
                changeDebt.append(" 币种:").append(currency).append("/").append(money);
                changeDebt.append(prepareToAdd);
            }
        }
        deskPayService.add(deskPayList);
        for (DeskPay deskPay : deskPayList) {
            PayPointOfSale payPointOfSale = new PayPointOfSale();
            payPointOfSale.setCurrency(deskPay.getCurrency());
            payPointOfSale.setDoTime(timeService.getNow());
            payPointOfSale.setPointOfSale("餐饮");
            payPointOfSale.setMoney(deskPay.getPayMoney());
            payPointOfSale.setDeskPayId(deskPay.getId());
            payPointOfSaleService.add(payPointOfSale);
        }
        /*餐桌信息转移到历史*/
        DeskIn deskIn = deskService.getByDesk(desk, pointOfSale);
        deskIn.setConsume(consume);
        deskIn.setGuestSource(guestSource);
        DeskInHistory deskInHistory = new DeskInHistory(deskIn);
        deskInHistory.setDiscount(discount);
        deskInHistory.setFinalPrice(finalPrice);
        deskInHistory.setDoneTime(timeService.getNow());
        deskInHistory.setCkSerial(serialService.getCkSerial());
        deskInService.delete(deskIn);
        deskInHistoryService.add(deskInHistory);
        /*处理报表*/
        /*需要考虑的
        * 1.菜品聚合
        * 2.包括退菜
        * 3.套餐在前
        * */
        List<FieldTemplate> templateList = new ArrayList<>();
        if ("y".equals(otherParamService.getValueByName("菜品聚合"))) {
            deskControllerService.generateDetail(deskDetailService.getListByDeskGroup(desk, pointOfSale), templateList);
        } else {
            deskControllerService.generateDetail(deskDetailService.getListByDesk(desk, pointOfSale, "category",null), templateList);
        }
        /*菜品明细转移到历史*/
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
        /*获取点菜操作员列表*/
        List<String> userList=deskDetailService.getDistinctUserId(desk, pointOfSale);
        StringBuilder users= new StringBuilder();
        for (String s : userList) {
            users.append(s);
        }
        /*生成操作员日志*/
        userLogService.addUserLog(desk + "台结算", userLogService.desk, userLogService.deskOut, desk);
        /*处理报表(餐饮账单一改得改三！！！！！！！！！！结账，预结，补打！！！！！！！！！)
        * param
        * 1.酒店名称
        * 2.结账序列号
        * 3.结算币种信息(转账信息)
        * 4.合计
        * 5.折扣
        * 6.折后
        * 7.桌台号
        * 8.操作员
        * 9.点菜员
        * 10.人数
        * 11.时间
        * field
        * 1.菜品
        * 2.单价
        * 3.数量
        * 4.小计
        * 5.类别
        * */
        String[] parameters = new String[]{otherParamService.getValueByName("酒店名称"), serialService.getCkSerial(), changeDebt.toString(), ifNotNullGetString(deskIn.getConsume()), ifNotNullGetString(discount), ifNotNullGetString(finalPrice), desk,userService.getCurrentUser(),users.toString(),szMath.ifNotNullGetString(deskIn.getNotNullNum()),timeService.dateToStringLong(timeService.getNow())};
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
        /*矫正结账金额*/
        Double consume = 0.0;
        List<DeskDetail> deskDetailList = deskDetailService.getListByDesk(desk, pointOfSale, "category,do_time",null);
        for (DeskDetail deskDetail : deskDetailList) {
            consume += deskDetail.getNotNullPrice() * deskDetail.getNum();
        }
        deskIn.setConsume(consume);
        deskInService.update(deskIn);
        Double discount = deskOut.getDiscount();
        Double finalPrice = deskOut.getFinalPrice();
        if (deskOut.getDeskDetailList() != null) {
            deskDetailList = deskOut.getDeskDetailList();
        } else {
            if ("y".equals(otherParamService.getValueByName("菜品聚合"))) {
                deskDetailList = deskDetailService.getListByDeskGroup(desk, pointOfSale);
            } else {
                deskDetailList = deskDetailService.getListByDesk(desk, pointOfSale, "category",null);
            }
        }
        List<FieldTemplate> templateList = new ArrayList<>();
        deskControllerService.generateDetail(deskDetailList, templateList);
        /*获取点菜操作员列表*/
        List<String> userList=deskDetailService.getDistinctUserId(desk, pointOfSale);
        StringBuilder users= new StringBuilder();
        for (String s : userList) {
            if(s!=null) {
                users.append(s);
            }
        }
        /*处理报表
        * param
        * 1.酒店名称
        * 2.结账序列号
        * 3.结算币种信息(转账信息)
        * 4.合计
        * 5.折扣
        * 6.折后
        * 7.桌台号
        * 8.操作员
        * 9.点菜员
        * 10.人数
        * 11.时间
        * field
        * 1.菜品
        * 2.单价
        * 3.数量
        * 4.小计
        * */
        String[] parameters = new String[]{otherParamService.getValueByName("酒店名称"), null, null, ifNotNullGetString(deskIn.getConsume()), ifNotNullGetString(discount), ifNotNullGetString(finalPrice), desk,userService.getCurrentUser(),users.toString(),szMath.ifNotNullGetString(deskIn.getNotNullNum()),timeService.dateToStringLong(new Date())};
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
        List<DeskDetailHistory> deskDetailHistoryList = deskDetailHistoryService.getList(ckSerial, "category,do_time",null);
        deskControllerService.generateDetailHistory(deskDetailHistoryList, templateList);
        /*生成结账信息*/
        StringBuilder changeDebt = new StringBuilder();//转账信息
        List<DeskPay> deskPayList = deskPayService.getByCkSerial(ckSerial);
        for (DeskPay deskPay : deskPayList) {
            String currencyAdd = deskPay.getCurrencyAdd();
            Currency currencyItem=currencyService.getByName(deskPay.getCurrency());
            if(currencyItem==null||currencyItem.getNotNullShowInReport()) {
                changeDebt.append(" 币种:").append(deskPay.getCurrency()).append("/").append(deskPay.getPayMoney()).append('/');
                if (currencyAdd != null) {
                    changeDebt.append(currencyAdd);
                }
            }
        }
        String[] parameters = new String[]{otherParamService.getValueByName("酒店名称"), deskInHistory.getCkSerial(), changeDebt.toString(), ifNotNullGetString(deskInHistory.getTotalPrice()), ifNotNullGetString(deskInHistory.getDiscount()), ifNotNullGetString(deskInHistory.getFinalPrice()), deskInHistory.getDesk(),userService.getCurrentUser(),"",szMath.ifNotNullGetString(deskInHistory.getNotNullNum()),timeService.dateToStringLong(deskInHistory.getDoneTime())};
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
        List<DeskDetailHistory> deskDetailHistoryList = deskDetailHistoryService.getList(ckSerial, null,null);
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
        for (DeskPay deskPay : deskPayList) {
            PayPointOfSale payPointOfSale = new PayPointOfSale();
            payPointOfSale.setCurrency(deskPay.getCurrency());
            payPointOfSale.setDoTime(timeService.getNow());
            payPointOfSale.setPointOfSale("餐饮");
            payPointOfSale.setMoney(deskPay.getPayMoney());
            payPointOfSaleService.add(payPointOfSale);
        }
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
                        reportService.printDirect(cookRoom.getPrinter(), paramList,"buffet");
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

    /**
     * 整桌退菜
     * 思路：删除所有退菜，重新退菜
     */
    @RequestMapping("cancelWholeDesk")
    @Transactional(rollbackFor = Exception.class)
    public void cancelWholeDesk(@RequestBody DeskIn deskIn) throws Exception {
        Date now=new Date();
        List<DeskDetail> deskDetailList=deskDetailService.getListByDesk(deskIn.getDesk(),deskIn.getPointOfSale(),null,null);
        List<DeskDetailCancelAll> deskDetailCancelAllList=new ArrayList<>();
        for (DeskDetail deskDetail : deskDetailList) {
            DeskDetailCancelAll deskDetailCancelAll=new DeskDetailCancelAll(deskDetail);
            deskDetailCancelAll.setDoneTime(now);
            deskDetailCancelAllList.add(deskDetailCancelAll);
        }
        deskDetailService.delete(deskDetailList);
        deskInService.delete(deskIn);
        DeskInCancelAll deskInCancelAll=new DeskInCancelAll(deskIn);
        deskInCancelAll.setDoneTime(now);
        deskInCancelAll.setUserIdDone(userService.getCurrentUser());
        deskInCancelAllService.add(deskInCancelAll);
        deskDetailCancelAllService.add(deskDetailCancelAllList);
        this.userLogService.addUserLog("整桌退菜", userLogService.desk, "退菜", deskIn.getDesk());
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
