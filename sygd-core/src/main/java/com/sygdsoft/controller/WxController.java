package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class WxController {
    private static final Logger logger = LoggerFactory.getLogger(WxController.class);
    @Autowired
    HotelService hotelService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "weChatTokenCheck", method = RequestMethod.GET)
    public String doGet(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestParam(value = "echostr") String echostr) {
        logger.info("wxCheck");
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

    @RequestMapping(value = "/weChatSetSession")
    // post 方法用于接收微信服务端消息
    public void weChatSetSession(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            @RequestParam(value = "openid", required = true) String openid,
            @RequestParam(value = "nickname", required = true) String nickname,
            @RequestParam(value = "headimgurl", required = true) String headimgurl
            ) throws Exception {
        session.setAttribute("openid", openid);
        session.setAttribute("nickname", nickname);
        session.setAttribute("headimgurl", headimgurl);
        response.sendRedirect("http://aaa.sygdsoft.com:8081/wx/index.html");
    }
    @RequestMapping(value = "/wxGetSession")
    // post 方法用于接收微信服务端消息
    public JSONObject wxGetSession() throws Exception {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("openid", request.getSession().getAttribute("openid"));
        jsonObject.put("nickname", request.getSession().getAttribute("nickname"));
        jsonObject.put("headimgurl", request.getSession().getAttribute("headimgurl"));
        return jsonObject;
    }

}
