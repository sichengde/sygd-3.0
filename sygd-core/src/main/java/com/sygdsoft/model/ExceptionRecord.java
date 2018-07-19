package com.sygdsoft.model;

public class ExceptionRecord extends BaseEntity {
    private String hotelId;
    private String method;
    private String param;
    private String exception;

    public ExceptionRecord() {
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
