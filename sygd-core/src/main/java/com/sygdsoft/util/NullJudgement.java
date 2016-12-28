package com.sygdsoft.util;

import org.springframework.stereotype.Component;

/**
 * Created by 舒展 on 2016-05-05.
 */
@Component
public class NullJudgement {
    static public Integer nullToZero(Integer integer){
        return integer==null?0:integer;
    }
    static public Double nullToZero(Double d){
        return d==null?0:d;
    }
    static public String nullToZero(String d){
        return d==null?"0.0":d;
    }
    static public Boolean nullToZero(Boolean d){
        return d != null;
    }
    static public String ifNotNullGetString(Double d){
        if(d!=null){
            return String.valueOf(d);
        }else {
            return null;
        }
    }
    static public String ifNotNullGetString(Integer d){
        if(d!=null){
            return String.valueOf(d);
        }else {
            return null;
        }
    }
    static public String ifNotNullGetString(String s){
        if(s==null){
            return "";
        }else {
            return s;
        }
    }
}
