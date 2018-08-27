package com.sygdsoft.service;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 舒展 on 2016-05-04. 阿里云上夜审之后没有提交，否则会无限循环夜审
 */
@Service
public class Night implements ApplicationListener<BrokerAvailabilityEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Night.class);
    private final MessageSendingOperations<String> messagingTemplate;
    @Autowired
    HotelService hotelService;
    @Autowired
    DebtService debtService;
    @Autowired
    CheckInMapper checkInMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    BookService bookService;
    @Autowired
    BookHistoryService bookHistoryService;
    @Autowired
    RoomService roomService;
    @Autowired
    SerialService serialService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    DeskBookService deskBookService;
    @Autowired
    DeskBookHistoryService deskBookHistoryService;
    @Autowired
    RoomPriceService roomPriceService;
    @Autowired
    Util util;
    @Autowired
    ReportService reportService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    NightService nightService;
    @Autowired
    RegisterService registerService;
    private AtomicBoolean brokerAvailable = new AtomicBoolean();

    @Autowired
    public Night(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }

    /**
     * 自动夜审
     */
    @Scheduled(cron = "${night.action}")
    public void autoNightAction() throws Exception {
        logger.info("开始自动夜审");
        if (userLogService.getRecentDate().getTime() - System.currentTimeMillis() > 24 * 60 * 60 * 1000) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "服务器时间不准确，无法夜审，请及时处理");
            this.messagingTemplate.convertAndSend("/beginNight", jsonObject);
            return;
        }
        if (!registerService.getPass() || System.currentTimeMillis() > this.registerService.getLimitTime().getTime()) {
            JSONObject jsonObject = new JSONObject();
            if (registerService.getAlertType() == 1) {
                jsonObject.put("msg", "系统运行期限已到");
            } else {
                jsonObject.put("msg", "数据空间不够，无法夜审，请及时处理");
            }
            this.messagingTemplate.convertAndSend("/beginNight", jsonObject);
            return;
        }
        timeService.setNow();
        userLogService.addUserLogWithoutUserIp("自动夜审", userLogService.reception, userLogService.night);
        this.messagingTemplate.convertAndSend("/beginNight", false);
        nightService.nightActionLogic();
        if (this.registerService.getLimitTime().getTime() - System.currentTimeMillis() < 7 * 24 * 60 * 60 * 1000) {
            JSONObject jsonObject = new JSONObject();
            if (registerService.getAlertType() == 1) {
                //jsonObject.put("msg", "系统运行期限已到");七天内不进行到期提醒
            } else {
                jsonObject.put("msg", "数据空间不够，无法夜审，请及时处理");
            }
            this.messagingTemplate.convertAndSend("/beginNight", jsonObject);
        } else {
            this.messagingTemplate.convertAndSend("/beginNight", true);
        }
    }

    /**
     * 重置序列号
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetSerial() {
        logger.info("重设序列号");
        /*序列号重设为0*/
        serialService.setAllToOne();
    }

    /**
     * 手动夜审
     */
    public void manualNightAction() throws Exception {
        if (userLogService.getRecentDate().getTime() - System.currentTimeMillis() > 24 * 60 * 60 * 1000) {
            throw new Exception("服务器时间不准确，无法夜审，请及时处理");
        }
        if (!registerService.getPass() || System.currentTimeMillis() > this.registerService.getLimitTime().getTime()) {
            if (registerService.getAlertType() == 1) {
                throw new Exception("系统运行期限已到");
            } else {
                throw new Exception("数据空间不够，无法夜审，请及时处理");
            }
        }
        this.messagingTemplate.convertAndSend("/beginNight", false);
        nightService.nightActionLogic();
        if (this.registerService.getLimitTime().getTime() - System.currentTimeMillis() < 7 * 24 * 60 * 60 * 1000) {
            JSONObject jsonObject = new JSONObject();
            if (registerService.getAlertType() == 1) {
                //jsonObject.put("msg", "系统运行期限已到");//七天内不进行到期提醒
            } else {
                jsonObject.put("msg", "数据空间不够，无法夜审，请及时处理");
            }
            this.messagingTemplate.convertAndSend("/beginNight", jsonObject);
        } else {
            this.messagingTemplate.convertAndSend("/beginNight", true);
        }
    }

}
