package com.sygdsoft.util;

import java.util.List;

/**
 * Created by 舒展 on 2016-05-11.
 */
public class ListToSting {
    static public String valueOf(List<String> stringList){
        String r="";
        for (String s:stringList){
            r=r+","+s;
        }
        r=r.substring(0,r.length()-1);
        return r;
    }
}
