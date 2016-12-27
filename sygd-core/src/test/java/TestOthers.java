import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 舒展 on 2016-05-13.
 * 生成注册码
 */
public class TestOthers {
    static private byte[] success = {33, 77};//注册成功字符串
    static private byte[] module2 = {1, 2, 3, 4, 5, 6, 7, 8};//拥有模块字符串{1,2,3,4,5,6,7,8}

    @Test
    public void freeTest() throws Exception {
        String filePath = "C://report/qrCode";
        String fileName = "桌子.png";
        JSONObject json = new JSONObject();
        json.put("name", "桌子");
        json.put("time", "2016-11-10 23:10:10");
        json.put("position", "5楼仓库");
        String content = json.toJSONString();// 内容
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println("输出成功.");
    }

    @Test
    public void asd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        System.out.println(calendar.getTime());
    }

    @Test
    public void dsds() {
        StringBuilder sb = new StringBuilder("41029828178BFBFF00630F01-95879982207809719");
        Integer int1 = null;
        for (int i = 0; i < sb.length(); i++) {
            try {
                int1 = Integer.valueOf(String.valueOf(sb.charAt(i)), 16);
            } catch (NumberFormatException e) {
                sb.replace(i, i+1, "0");
            }
        }
        System.out.print(sb);
    }

    @Test
    public void checkSerial() throws UnknownHostException, SocketException {
        System.out.println(this.check("836993337030030038"));
    }

    /**
     * 整体生成函数，在本机上运行，提取ip，打包成密码，运算
     *
     * @throws Exception
     */
    @Test
    public void getLocalMac() throws Exception {
        //得到IP，输出PC-201309011313/122.206.73.83
        InetAddress ia = InetAddress.getLocalHost();
        System.out.println(ia);
        //获取网卡，获取地址
        Byte[] module3 = new Byte[8];//mac生成模块字符串
        int[] module4 = new int[8];//mac生成模块字符串
        String module5 = "";
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        String check = "";
        for (int i = 0; i < 2; i++) {
            //字节转换为整数
            int temp = mac[i] & 0xff;
            int a = success[i] * temp;
            String str = Integer.toString(a);
            check += str;
        }
        check = Integer.toHexString(Integer.parseInt(check) ^ 1234);//158409427
        int j = 0;
        for (int i = 2; i < 6; i++) {
            //字节转换为整数
            int temp = mac[i] & 0xff;
            byte[] b = Integer.toHexString(temp).getBytes();
            if (b.length == 1) {
                module3[j] = 0;
                j++;
                module3[j] = b[0];
                j++;
            } else {
                module3[j] = b[0];
                j++;
                module3[j] = b[1];
                j++;
            }
        }
        for (int i = 0; i < 8; i++) {
            module4[i] = module2[i] ^ module3[i];
            module5 += Integer.toHexString(module4[i]);
        }
        System.out.println("生成的注册序列号:" + check);
        System.out.println("生成的功能序列号:" + module5);
    }

    /**
     * 后续生成函数，客户在自己电脑生成密码并且发送到云服务器上，根据这个生成真码
     */
    @Test
    public void generaBySerial() {
        //得到IP，输出PC-201309011313/122.206.73.83
        //获取网卡，获取地址
        Byte[] module3 = new Byte[8];//mac生成模块字符串
        int[] module4 = new int[8];//mac生成模块字符串
        String module5 = "";
        int[] mac = new int[]{0, 37, 100, 117, 169, 201};//自己在野狗上查找之后填进来603b3a613434343f 0,37,100,117,169,201
        String check = "";
        for (int i = 0; i < 2; i++) {
            //字节转换为整数
            int temp = mac[i];
            int a = success[i] * temp;
            String str = Integer.toString(a);
            check += str;
        }
        check = Integer.toHexString(Integer.parseInt(check) ^ 1234);//158409427
        int j = 0;
        for (int i = 2; i < 6; i++) {
            //字节转换为整数
            int temp = mac[i];
            byte[] b = Integer.toHexString(temp).getBytes();
            if (b.length == 1) {
                module3[j] = 0;
                j++;
                module3[j] = b[0];
                j++;
            } else {
                module3[j] = b[0];
                j++;
                module3[j] = b[1];
                j++;
            }
        }
        for (int i = 0; i < 8; i++) {
            module4[i] = module2[i] ^ module3[i];
            module5 += Integer.toHexString(module4[i]);
        }
        System.out.println("生成的注册序列号:" + check);
        System.out.println("生成的功能序列号:" + module5);
    }

    /**
     * 校验注册号是否正确
     *
     * @param serial
     * @return
     * @throws UnknownHostException
     * @throws SocketException
     */
    private boolean check(String serial) throws UnknownHostException, SocketException {
        InetAddress ia = InetAddress.getLocalHost();
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        String success1 = serial.substring(0, serial.length() - 16);
        String module1 = serial.substring(serial.length() - 16, serial.length());
        Integer[] module = new Integer[8];
        int a = Integer.valueOf(success1, 16);
        String check = "";
        a ^= 1234;
        for (int i = 0; i < 2; i++) {
            //字节转换为整数
            int temp = mac[i] & 0xff;
            int b = success[i] * temp;
            String str = Integer.toString(b);
            check += str;
        }
        if (a != Integer.valueOf(check)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            String m1 = module1.substring(i * 2, i * 2 + 2);
            Byte b = Byte.valueOf(m1, 16);
            module[i] = b ^ module2[i];
        }
        Byte[] m2 = new Byte[8];
        int j = 0;
        for (int i = 2; i < 6; i++) {
            //字节转换为整数
            int temp = mac[i] & 0xff;
            byte[] b = Integer.toHexString(temp).getBytes();
            if (b.length == 1) {
                m2[j] = 0;
                j++;
                m2[j] = b[0];
                j++;
            } else {
                m2[j] = b[0];
                j++;
                m2[j] = b[1];
                j++;
            }
        }
        Integer[] r = new Integer[8];
        for (int i = 0; i < 8; i++) {
            if ((m2[i] & 0xff) == module[i]) {
                r[i] = 1;
            } else {
                r[i] = 0;
            }
        }
        System.out.println(r);
        return true;
    }
}

