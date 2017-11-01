package com.sygdsoft.service;

import com.mysql.jdbc.Connection;
import com.sygdsoft.model.InCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by 舒展 on 2016-05-20.
 * 验证注册码，没有就是测试版，试用版只有前三个模块（接待餐饮桑拿）
 */
@Service
public class RegisterService {
    private int pass=0;//0是正式版,1是试用版，没有试用版，试用版就是没通过
    private int passCK=0;//餐饮模块有没
    private int passSN=0;//桑拿模块有没
    private List<Integer> module = new ArrayList<>();
    private Integer maxUser;
    private String serial;
    private String serialCK;
    private String serialSN;
    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
    public List<String> securityStr=new ArrayList<>();//秘钥数组，一个秘钥只能用一次
    /**
     * 定是清空秘钥数组
     */
    @Scheduled(fixedRate = 900000)
    public void clearSecurityStr(){
        this.securityStr.clear();
    }

    /**
     * 初始化验证注册码,初始化httpClient
     */
    @PostConstruct
    public void init() throws Exception {
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
                String[] serial = resultSet.getString("value").split(",");
                this.serial = serial[0];
                logger.debug("roomSerial:"+this.serial);
                this.serialCK = serial[1];
                logger.debug("ckSerial:"+this.serialCK);
                this.serialSN = serial[2];
                logger.debug("snSerial:"+this.serialSN);
            }
            conn.close();
            ps.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.check();
        //this.checkCK();
        //this.checkSN();
    }

    public void check() throws IOException {
        if (this.serial == null) {
            this.pass = 1;
        } else {
            String parse = "";
            String password = this.serial;
            for (int i = 0; i < password.length(); i++) {
                Integer int1 = Integer.valueOf(String.valueOf(password.charAt(i)), 16);
                parse += Integer.toHexString(int1 ^ 7);
            }
            if (parse.equals(getLocalSerial())) {
                this.pass = 0;
            } else {
                this.pass = 1;
            }
        }
    }

    public void checkCK() throws IOException {
        if (this.serialCK == null) {
            this.passCK = 1;
        } else {
            String parse = "";
            String password = this.serialCK;
            for (int i = 0; i < password.length(); i++) {
                Integer int1 = Integer.valueOf(String.valueOf(password.charAt(i)), 16);
                parse += Integer.toHexString(int1 ^ 8);
            }
            if (parse.equals(getLocalSerial())) {
                this.passCK = 0;
            } else {
                this.passCK = 1;
            }
        }
    }

    public void checkSN() throws IOException {
        if (this.serialSN == null) {
            this.passSN = 1;
        } else {
            String parse = "";
            String password = this.serialSN;
            for (int i = 0; i < password.length(); i++) {
                Integer int1 = Integer.valueOf(String.valueOf(password.charAt(i)), 16);
                parse += Integer.toHexString(int1 ^ 9);
            }
            if (parse.equals(getLocalSerial())) {
                this.passSN = 0;
            } else {
                this.passSN = 1;
            }
        }
    }

    public String getLocalSerial() throws IOException {
        /*获取cpu序列号*/
        /*long start = System.currentTimeMillis();
        Process process = Runtime.getRuntime().exec(
                new String[]{"wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String cpuSerial = sc.next();*/
        //System.out.println("cpuSerial:" + cpuSerial);

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
        //System.out.println("diskSerial:" + diskSerial);
        /*获取主板序列号*/
        /*String motherBoardSerial = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                motherBoardSerial += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //System.out.println("motherBoardSerial:" + motherBoardSerial.trim());
        String s = diskSerial;
            /*单数的话补个0*/
        if (s.length() % 2 == 1) {
            s += "0";
        }
        /*去除乱七八糟的字符，设置为0*/
        StringBuilder sb = new StringBuilder(s);
        Integer int1 = null;
        for (int i = 0; i < sb.length(); i++) {
            try {
                int1 = Integer.valueOf(String.valueOf(sb.charAt(i)), 16);
            } catch (NumberFormatException e) {
                sb.replace(i, i + 1, "0");
            }
        }
        return String.valueOf(sb);
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
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

    public int getPassCK() {
        return passCK;
    }

    public void setPassCK(int passCK) {
        this.passCK = passCK;
    }

    public int getPassSN() {
        return passSN;
    }

    public void setPassSN(int passSN) {
        this.passSN = passSN;
    }
}
