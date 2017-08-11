package com.sygdsoft.conf;

import com.sygdsoft.model.User;
import com.sygdsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    UserService userService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*手机端启用通知之后会为每一个操作员生成一个endPoint*/
        List<User> users;
        try {
            //users = userService.get(null);
            registry.addEndpoint("/hotelBoot").setAllowedOrigins("*").withSockJS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}