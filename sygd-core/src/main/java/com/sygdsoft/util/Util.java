package com.sygdsoft.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 舒展 on 2016-06-24.
 */
@Component
public class Util {
    /*数字转大写*/
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆",
            "伍", "陆", "柒", "捌", "玖"};
    /*数字转大写*/
    private static final String[] CN_UPPER_MONETRAY_UNIT = {"分", "角", "元",
            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
            "佰", "仟"};
    private static final String CN_FULL = "整";
    private static final String CN_NEGATIVE = "负";
    /*金额的精度，默认值为2*/
    private static final int MONEY_PRECISION = 2;
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

    /**
     * 为字符串前后加上单引号
     */
    public String wrapWithBrackets(String s) {
        return "\'" + s + "\'";
    }

    /**
     * 为字符串前后加上百分号（用于模糊查询）
     */
    public String wrapWithPercent(String s) {
        return "%" + s + "%";
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney 输入的金额
     * @return 对应的汉语大写
     */
    public String number2CNMontrayUnit(BigDecimal numberOfMoney) {
        if(numberOfMoney==null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
// -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
// positive.
        int signum = numberOfMoney.signum();
// 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
//这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
// 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
// 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
// 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }

    /**
     * 判断两个对象的不同值，并且写入到message中
     */
    public String classOfSrc(Object source, Object target) {
        Class<?> srcClass = source.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        String message = "";
        for (Field field : fields) {
            String nameKey = field.getName();
            String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                    .toString();
            String tarValue = getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey)
                    .toString();
            if (!srcValue.equals(tarValue)) {
                message = message + nameKey + "原:" + srcValue + ",改为:" + tarValue + "/";
            }
        }
        if (!message.equals("")) {
            message = message.substring(0, message.length() - 1);
            message = getClassValue(source, fields[0].getName()) == null ? "" : getClassValue(source, fields[0].getName()).toString() + "修改--" + message;
        }
        return message;
    }

    /**
     * 根据字段名称取值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[]{});
                } catch (Exception e) {
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID")
                        && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase()
                        .equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
            // logger.info("取方法出错！" + e.toString());
        }
        return null;
    }
    /**
     * List转成字符串，中间逗号隔开，主要用于日志记录，in查询
     */
    public String listToString(List<String> stringList){
        String out="";
        for (String s : stringList) {
            out+=s+",";
        }
        return out.substring(0,out.length()-1);
    }

    /**
     * List转成字符串，带点''，中间逗号隔开，主要用于日志记录，in查询
     */
    public String listToStringWithPoint(List<String> stringList){
        String out="";
        for (String s : stringList) {
            out+=this.wrapWithBrackets(s)+",";
        }
        return out.substring(0,out.length()-1);
    }

    /**
     * Array转成字符串，中间逗号隔开，主要用于日志记录，in查询
     */
    public String arrayToString(String[] stringList,boolean Brackets){
        String out="";
        for (String aStringList : stringList) {
            if(!Brackets) {
                out += aStringList + ",";
            }else {
                out += this.wrapWithBrackets(aStringList) + ",";
            }
        }
        return out.substring(0,out.length()-1);
    }

    /**
     * 去掉最后一个字符
     */
    public String removeLast(String s){
        return s.substring(0,s.length()-1);
    }
}
