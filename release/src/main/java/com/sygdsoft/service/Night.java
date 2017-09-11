package com.sygdsoft.service;

import com.sygdsoft.conf.CloudServiceConfig;
import com.sygdsoft.controller.NightController;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.model.*;
import com.sygdsoft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 舒展 on 2016-05-04. 阿里云上夜审之后没有提交，否则会无限循环夜审
 */
@Service
public class Night implements ApplicationListener<BrokerAvailabilityEvent> {
    private final MessageSendingOperations<String> messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(Night.class);
    private AtomicBoolean brokerAvailable = new AtomicBoolean();
    @Autowired
    HotelService hotelService;

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }

    @Autowired
    public Night(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

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

    /**
     * 自动夜审
     */
    @Scheduled(cron = "${night.action}")
    public void autoNightAction() throws Exception {
        logger.info("开始自动夜审");
        /*Map<String,String> map=new HashMap<>();
        map.put("hotelId",hotelService.getHotelId());
        hotelService.post(CloudServiceConfig.cloudAddress+"/manualNightCloud",map);*/
        timeService.setNow();
        userLogService.addUserLogWithoutUserIp("自动夜审",userLogService.reception,userLogService.night);
        this.messagingTemplate.convertAndSend("/beginNight", false);
        nightService.nightActionLogic();
        this.messagingTemplate.convertAndSend("/beginNight", true);
    }

    /**
     * 夜审前保护（屏幕变黑禁止进行操作）
     */
    @Scheduled(cron = "${night.protect}")
    public void nightProtect() throws Exception {
        this.messagingTemplate.convertAndSend("/beginNight", true);
    }

    /**
     * 重置序列号
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetSerial(){
        logger.info("重设序列号");
        /*序列号重设为0*/
        serialService.setAllToOne();
    }

    /**
     * 手动夜审
     */
    public void manualNightAction() throws Exception{
        this.messagingTemplate.convertAndSend("/beginNight", false);
        nightService.nightActionLogic();
        this.messagingTemplate.convertAndSend("/beginNight", true);
    }
}
