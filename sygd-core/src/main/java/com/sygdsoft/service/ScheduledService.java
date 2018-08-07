package com.sygdsoft.service;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CheckIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ScheduledService implements ApplicationListener<BrokerAvailabilityEvent> {
    private final MessageSendingOperations<String> messagingTemplate;
    private AtomicBoolean brokerAvailable = new AtomicBoolean();

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }

    @Autowired
    public ScheduledService(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    CheckInService checkInService;

    //@Scheduled(fixedRate = 10000)
    @Scheduled(fixedRate = 300000)
    public void hourRoomCheck() throws Exception {
        Query query=new Query("room_price_category=\'小时房\'");
        List<CheckIn> checkInList=checkInService.get(query);
        for (CheckIn checkIn : checkInList) {
            if(checkIn.getLeaveTime().getTime()<System.currentTimeMillis()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("roomId",checkIn.getRoomId());
                this.messagingTemplate.convertAndSend("/hourRoomCheck", jsonObject);
            }
        }
    }
}
