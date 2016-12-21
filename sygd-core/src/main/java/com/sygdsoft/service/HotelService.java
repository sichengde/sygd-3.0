package com.sygdsoft.service;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 舒展 on 2016-10-11. 获取请求所在的酒店名称（数据库名称），在云端服务器上使用时
 * on 2016-10-12 获取配置文件里的本地数据库名称，云下客户端使用
 */
@Service
public class HotelService {
    @Autowired
    HttpServletRequest request;
    @Value("${hotel.hotelId}")
    private String hotelId;
    @Value("${hotel.ip}")
    private String ip;
    @Value("${server.port}")
    private String port;

    /**
     * 获得客户端当前登陆的酒店名称
     */
    public String getCurrentHotel() {
        return request.getHeader("database");
    }

    public String post(String url, Map<String, String> params) throws Exception {
        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //处理参数
        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        //结果
        CloseableHttpResponse response = null;
        String content = "";
        //提交的参数
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
        //将参数给post方法
        httpPost.setEntity(uefEntity);
        //执行post方法
        response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            content = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(content);
        }
        return content;
    }

    public String postJSON(String url, String param) throws Exception {
        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
        //结果
        CloseableHttpResponse response = null;
        String content = "";
        //执行post方法
        response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            content = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(content);
        }
        return content;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getIp() {
        /*如果不是80端口，则要加上端口号*/
        if(this.port.equals("80")){
            return ip;
        }else {
            return ip+":"+port;
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
