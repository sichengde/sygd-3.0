package com.sygdsoft.service;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
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
import java.io.IOException;
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
}
