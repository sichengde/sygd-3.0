package com.sygdsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 舒展 on 2016-04-28.
 * 获得当前时间
 */
@Service
public class TimeService {
    private Date now;
    public SimpleDateFormat longFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat numberLongFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    public SimpleDateFormat numberShortFormat = new SimpleDateFormat("yyMMdd");
    public SimpleDateFormat reportFormat = new SimpleDateFormat("MM.dd");
    public SimpleDateFormat serialFormat = new SimpleDateFormat("yyMMddHH");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private Integer adjustDay=0;


    public TimeService() {
    }

    public Date getNow() {
        if (now != null) {
            return now;
        } else {
            return new Date();
        }
    }

    public Date setNow() {
        this.now = new Date();
        return now;
    }

    public Date stringToDateLong(String s) throws Exception {
        return longFormat.parse(s);
    }

    public Date stringToDateShort(String s) throws Exception {
        return shortFormat.parse(s);
    }

    /**
     * 加天数
     * @param date
     * @param days
     * @return
     */
    public Date addDay(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public Date addDay(String date, Integer days) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(this.stringToDateShort(date));
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    /**
     * 加月数
     */
    public Date addMonth(String date,Integer months) throws Exception{
        Calendar c = Calendar.getInstance();
        c.setTime(this.stringToDateShort(date));
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }

    public Date addMonth(Date date,Integer months) throws Exception{
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months);
        return c.getTime();
    }

    /**
     * 加年数
     * @param date
     * @param years
     * @return
     */
    public Date addYear(Date date, Integer years) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    public Date addYear(String date, Integer years) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(this.stringToDateShort(date));
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    public String getNowLong() {
        return longFormat.format(now);
    }

    public String getNowShort() {
        return shortFormat.format(now);
    }

    public String getNowNumberLong() {
        return numberLongFormat.format(now);
    }

    public String getNowNumberShort() {
        if (now != null) {
            return numberShortFormat.format(now);
        } else {
            return numberShortFormat.format(new Date());
        }
    }

    public String getSerialShort() {
        if (now != null) {
            return serialFormat.format(now);
        } else {
            return serialFormat.format(new Date());
        }
    }

    /**
     * 只获取时间，当前时间
     */
    public String getNowTimeString() {
        if (now != null) {
            return timeFormat.format(now);
        } else {
            return timeFormat.format(new Date());
        }
    }

    /**
     * 只获取时间
     * @param date
     * @return
     */
    public String getTimeString(Date date){
        return timeFormat.format(date);
    }

    public synchronized String dateToStringLong(Date date) {
        if (date == null) {
            return "";
        }
        return longFormat.format(date);
    }

    public synchronized String dateToStringShort(Date date) {
        if (date == null) {
            return "";
        }
        return shortFormat.format(date);
    }

    public String stringLongToShort(String date) throws ParseException {
        return shortFormat.format(shortFormat.parse(date));
    }

    /**
     * 获得最小时间(时间部分设置为0)
     */
    public Date getMinTime(Date date) throws ParseException {
        return longFormat.parse(shortFormat.format(date)+" 00:00:00");
    }

    /**
     * 获得最大时间(日期部分设置为第二天，时间设置为0)
     */
    public Date getMaxTime(Date date) throws ParseException {
        return longFormat.parse(shortFormat.format(date)+" 24:00:00");
    }

    /**
     * 获得月首
     */
    public Date getMinMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return this.getMinTime(calendar.getTime());
    }

    /**
     * 获得月末
     */
    public Date getMaxMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return this.getMaxTime(calendar.getTime());
    }

    /**
     * 获得季首
     */
    public Date getMinQuarter(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) / 3) * 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return this.getMinTime(calendar.getTime());
    }

    /**
     * 获得季末
     * beginTime:季首
     */
    public Date getMaxQuarter(Date date,Date beginTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        calendar.setTime(date);
        startCalendar.setTime(beginTime);
        calendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return this.getMaxTime(calendar.getTime());
    }

    /**
     * 获得年首
     */
    public Date getMinYear(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return this.getMinTime(calendar.getTime());
    }

    /**
     * 获得年末
     */
    public Date getMaxYear(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return this.getMaxTime(calendar.getTime());
    }

    /**
     * 获得中文月份
     */
    public String getMonthCN(Date date)throws Exception{
        String m=this.dateToStringShort(date).substring(5,7);
        switch (m){
            case "01":
                m="一月";
                break;
            case "02":
                m="二月";
                break;
            case "03":
                m="三月";
                break;
            case "04":
                m="四月";
                break;
            case "05":
                m="五月";
                break;
            case "06":
                m="六月";
                break;
            case "07":
                m="七月";
                break;
            case "08":
                m="八月";
                break;
            case "09":
                m="九月";
                break;
            case "10":
                m="十月";
                break;
            case "11":
                m="十一月";
                break;
            case "12":
                m="十二月";
                break;
        }
        return m;
    }

    public Integer getAdjustDay() {
        return adjustDay;
    }

    public void setAdjustDay(Integer adjustDay) {
        this.adjustDay = adjustDay;
    }

    public Date adjustDay(Date date) throws Exception {
        if(adjustDay!=0){
            date=this.addDay(this.addMonth(date,-1),adjustDay);
        }
        return date;
    }
    public String getReportFormat(Date date){
        return reportFormat.format(date);
    }
}
