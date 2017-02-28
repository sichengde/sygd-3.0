package com.sygdsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 舒展 on 2016-05-20.
 * 验证注册码，没有就是测试版，试用版只有前三个模块（接待餐饮桑拿）
 */
@Service
public class RegisterService {
    private int pass;//0是正式版,1是试用版，没有试用版，试用版就是没通过
    private List<Integer> module = new ArrayList<>();
    private Integer maxUser;
    static private byte[] success = {33, 77};//注册成功字符串
    static private byte[] module2 = {1, 2, 3, 4, 5, 6, 7, 8};//拥有模块字符串
    @Autowired
    ApplicationArguments args;
    @Autowired
    OtherParamService otherParamService;

   /* public void check() throws UnknownHostException, SocketException {
        if(args.getSourceArgs().length==0){//没有输入注册号只给两个模块
            module.add(0);
            module.add(7);
            pass=1;
            return;
        }
        String serial=args.getSourceArgs()[0];
        InetAddress ia = InetAddress.getLocalHost();
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        if (mac==null){
            pass=1;
            return;
        }
        String success1=serial.substring(0,serial.length()-18);
        maxUser= Integer.valueOf(serial.substring(serial.length()-18,serial.length()-16));
        String module1=serial.substring(serial.length()-16,serial.length());
        Integer[] z=new Integer[8];
        int a=Integer.valueOf(success1,16);
        String check= "";
        a^=1234;
        for(int i=0; i<2; i++) {
            //字节转换为整数
            int temp = mac[i]&0xff;
            int b=success[i]*temp;
            String str = Integer.toString(b);
            check+=str;
        }
        if (a==Integer.valueOf(check)){
            pass=0;
        }else {
            pass=1;
        }
        for (int i=0;i<8;i++){
            String m1=module1.substring(i*2,i*2+2);
            Byte b=Byte.valueOf(m1,16);
            z[i]=b^module2[i];
        }
        Byte[] m2=new Byte[8];
        int j=0;
        for(int i=2; i<6; i++) {
            //字节转换为整数
            int temp = mac[i]&0xff;
            byte[] b=Integer.toHexString(temp).getBytes();
            if (b.length==1){
                m2[j]=0;
                j++;
                m2[j]=b[0];
                j++;
            }else {
                m2[j]=b[0];
                j++;
                m2[j]=b[1];
                j++;
            }
        }
        for (int i=0;i<8;i++){
            if ((m2[i]&0xff)==z[i]){
                module.add(i);
            }
        }
    }*/

    /**
     * 初始化验证注册码,初始化httpClient
     */
    @PostConstruct
    public void init() throws IOException {
        this.check();
    }

    /**
     * 且把酒店信息发送到云服务器
     */
    public String sendMessage() throws Exception {
        //得到IP，输出PC-201309011313/122.206.73.83
        InetAddress ia = InetAddress.getLocalHost();
        //获取网卡，获取地址
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        String out = "";
        for (byte b : mac) {
            out += String.valueOf(b & 0xff) + ",";
        }
        return out.substring(0, out.length() - 1);
    }

    public void check() throws IOException {
        if (args.getSourceArgs().length == 0) {
            this.pass = 1;
        } else {
            String parse = "";
            String password= args.getSourceArgs()[0];
            for (int i = 0; i < password.length(); i++) {
                Integer int1 = Integer.valueOf(String.valueOf(password.charAt(i)),16);
                parse +=Integer.toHexString(int1^7);
            }
            if (parse.toUpperCase().equals(getLocalSerial())) {
                this.pass = 0;
            } else {
                this.pass = 1;
            }
        }
    }

    private String getLocalSerial() throws IOException {
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
        String motherBoardSerial = "";
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
        }
        //System.out.println("motherBoardSerial:" + motherBoardSerial.trim());
        String s=diskSerial;
            /*单数的话补个0*/
        if(s.length()%2==1){
            s+="0";
        }
        /*去除乱七八糟的字符，设置为0*/
        StringBuilder sb = new StringBuilder(s);
        Integer int1 = null;
        for (int i = 0; i < sb.length(); i++) {
            try {
                int1 = Integer.valueOf(String.valueOf(sb.charAt(i)), 16);
            } catch (NumberFormatException e) {
                sb.replace(i, i+1, "0");
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
}
