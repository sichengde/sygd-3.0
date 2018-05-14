package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class WxController {
    @Autowired
    HotelService hotelService;
    @RequestMapping(value = "weChatTokenCheck", method = RequestMethod.GET)
    public String doGet(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestParam(value = "echostr") String echostr) {
        System.out.println(123);
        return echostr;
    }

    @RequestMapping(value = "/weChatUserGet")
    // post 方法用于接收微信服务端消息
    public void wx(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            @RequestParam(value = "code", required = true) String code,
            @RequestParam(value = "state", required = true) String state) throws Exception {
        String appId = "wxf282e4ecec623e89";
        String appSecret = "8d61c4b9a4b5bb75ddf74e8efd33a480";
        JSONObject json = hotelService.wxHttpPost("https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
                .replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code));
        String openid = json.getString("openid");
        String accessToken = json.getString("access_token");
        JSONObject userInfo= hotelService.wxHttpPost("https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN".replace("ACCESS_TOKEN",accessToken).replace("OPENID",openid));
        session.setAttribute("openid", openid);
        session.setAttribute("nickname", userInfo.get("nickname"));
        session.setAttribute("headimgurl", userInfo.get("headimgurl"));
        /*获取localIp*/
        String localIp="http://";

        session.setAttribute("localIp", userInfo.get("headimgurl"));
        response.sendRedirect("http://aaa.sygdsoft.com:8081/sygd2/index.html?openid="+openid+"&nickname="+userInfo.get("nickname")+"headimgurl="+userInfo.get("headimgurl"));
    }
}
