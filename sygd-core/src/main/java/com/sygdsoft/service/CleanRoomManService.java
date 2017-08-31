package com.sygdsoft.service;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.model.CleanRoomMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
@SzMapper(id = "cleanRoomMan")
@Service
public class CleanRoomManService extends BaseService<CleanRoomMan>  implements ApplicationListener<BrokerAvailabilityEvent> {
    private final MessageSendingOperations<String> messagingTemplate;
    private AtomicBoolean brokerAvailable = new AtomicBoolean();

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }

    @Autowired
    public CleanRoomManService(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void arrangeCleanRoomMan(JSONObject jsonObject){
        this.messagingTemplate.convertAndSend("/"+jsonObject.getString("userId"), jsonObject);
    }
    public void confirmCheckOutRoom(JSONObject jsonObject){
        this.messagingTemplate.convertAndSend("/"+jsonObject.getString("socketId"), jsonObject);
    }

}
