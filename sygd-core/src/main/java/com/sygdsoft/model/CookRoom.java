package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-11-04.
 */
public class CookRoom extends BaseEntity{
    private String cookName;//厨房名称
    private String printer;//打印机名称
    private String printerIp;//打印机ip，主要用来测试能不能ping同
    private Integer num;//打印几份
    private Boolean cut;//是否切纸，不切纸就是传菜
    private Boolean usbPort;//是否是u口打印

    public CookRoom() {
    }

    public Boolean getNotNullUPort(){
        if(usbPort==null){
            return false;
        }else {
            return usbPort;
        }
    }

    public Boolean getNotNullCut(){
        if(cut==null){
            return false;
        }else {
            return cut;
        }
    }
    public Integer getNotNullNum(){
        if(num==null){
            return 1;
        }else {
            return num;
        }
    }
    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getPrinterIp() {
        return printerIp;
    }

    public void setPrinterIp(String printerIp) {
        this.printerIp = printerIp;
    }

    public Boolean getCut() {
        return cut;
    }

    public void setCut(Boolean cut) {
        this.cut = cut;
    }

    public Boolean getUsbPort() {
        return usbPort;
    }

    public void setUsbPort(Boolean usbPort) {
        this.usbPort = usbPort;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
