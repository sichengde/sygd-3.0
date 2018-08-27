package com.sygdsoft.service;

import com.mysql.jdbc.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by 舒展 on 2016-05-20.
 * yyyy-MM-dd+1位模块+注册码
 */
@Service
public class RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
    public SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
    public List<String> securityStr = new ArrayList<>();//秘钥数组，一个秘钥只能用一次
    String key = "shuzhanxinqiaoyi";
    String iv = "shuzhanxinqiaoyi";//初始化向量参数，AES 为16bytes. DES 为8bytes.
    private Date limitTime = null;
    private Boolean pass = false;//接待模块
    private Boolean passCK = false;//餐饮模块
    private Boolean passSN = false;//桑拿模块
    private int alertType=0;//超时后提示方式，0：磁盘空间不足，1：系统运行期限已到
    private List<Integer> module = new ArrayList<>();
    private Integer maxUser;
    private String serial;

    private static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] hexStringToByte(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 定是清空秘钥数组
     */
    @Scheduled(fixedRate = 900000)
    public void clearSecurityStr() {
        this.securityStr.clear();
    }

    /**
     * 初始化验证注册码,初始化httpClient
     */
    @PostConstruct
    public void init() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/hotel?characterEncoding=utf8&useSSL=true";
        String username = "hotel";
        String password = "q123";
        try {
            Connection conn = null;
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement("SELECT * from other_param where other_param='注册码'");
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                this.serial = resultSet.getString("value");
            }
            conn.close();
            ps.close();
            resultSet.close();
            this.check();
        } catch (Exception e) {
            logger.error("数据库里的注册码格式不对！99%是复制了回车换行符,如果冒号后边是null，那就是没连上数据库，99%是没加权限！:"+this.serial,e);
            e.printStackTrace();
        }
    }

    public void check() throws Exception {
        Key keySpec = new SecretKeySpec(key.getBytes(), "AES");    //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] decodeBytes = hexStringToByte(this.serial);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(key.getBytes()));//key长可设为128，192，256位，这里只能设为128
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] result = cipher.doFinal(decodeBytes);
        this.limitTime = shortFormat.parse(new String(result).substring(0, 10));//有效期
        int module = Integer.parseInt(new String(result).substring(10, 11));//模块，1接待，2餐饮，3桑拿，4全部
        this.alertType= Integer.parseInt(new String(result).substring(11, 12));
        decodeBytes = hexStringToByte(new String(result).substring(12));
        keyGenerator.init(128, new SecureRandom(key.getBytes()));//key长可设为128，192，256位，这里只能设为128
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        result = cipher.doFinal(decodeBytes);
        String out = new String(result);
        String code = out.substring(0, out.length() - 3);//机器码
        logger.info("数据库中的注册码:" + this.serial);
        logger.info("解析后的机器码:" + code);
        logger.info("本机码:" + getLocalSerial());
        logger.info("有效期:"+this.limitTime);
        logger.info("模块/人数:"+module);
        logger.info("提示方式"+this.alertType);
        if (code.equals(getLocalSerial())) {
            logger.info("注册成功");
            this.pass = true;
        }else {
            logger.info("注册失败");
        }
        if (code.equals(getLocalSerial())) {
            switch (module) {
                case 1:
                    this.pass = true;
                    break;
                case 2:
                    this.passCK = true;
                    break;
                case 3:
                    this.passSN = true;
                    break;
                case 4:
                    this.pass = true;
                    this.passCK = true;
                    this.passSN = true;
                    break;
            }
        }
    }

    public String getLocalSerial() throws IOException {
        /*获取硬盘序列号*/
        String diskSerial = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"C\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                diskSerial += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diskSerial;
    }

    public String getLocalSerialShow() throws Exception {
        Key keySpec = new SecretKeySpec(key.getBytes(), "AES");    //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        /*获取硬盘序列号*/
        String diskSerial = "";
        File file = File.createTempFile("realhowto", ".vbs");
        file.deleteOnExit();
        FileWriter fw = new java.io.FileWriter(file);
        String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                + "Set colDrives = objFSO.Drives\n"
                + "Set objDrive = colDrives.item(\"C\")\n"
                + "Wscript.Echo objDrive.SerialNumber";  // see note
        fw.write(vbs);
        fw.close();
        Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
        BufferedReader input =
                new BufferedReader
                        (new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            diskSerial += line;
        }
        input.close();
        Random random = new Random();
        String content = diskSerial + String.format("%03d", random.nextInt(999));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        //Cipher cipher = AesUtil.generateCipher(Cipher.ENCRYPT_MODE,"1234567890123456".getBytes(),"1234567890123456".getBytes());
        byte[] byteResult = cipher.doFinal(content.getBytes());
        return byteToHexString(byteResult);
    }

    public List<Integer> getModule() {
        return module;
    }

    public void setModule(List<Integer> module) {
        this.module = module;
    }

    public Integer getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(Integer maxUser) {
        this.maxUser = maxUser;
    }

    public Date getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Boolean getPassCK() {
        return passCK;
    }

    public void setPassCK(Boolean passCK) {
        this.passCK = passCK;
    }

    public Boolean getPassSN() {
        return passSN;
    }

    public void setPassSN(Boolean passSN) {
        this.passSN = passSN;
    }

    public int getAlertType() {
        return alertType;
    }

    public void setAlertType(int alertType) {
        this.alertType = alertType;
    }
}
