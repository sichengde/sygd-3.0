package com.sygdsoft.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by 舒展 on 2016-06-23.
 */
@Component
public class SzMath {
    private DecimalFormat df = new DecimalFormat("##0.00%");
    private DecimalFormat twoDecimal = new DecimalFormat("##0.00");
    private NumberFormat nt = NumberFormat.getPercentInstance();

    public String getPercent(Double var1, Double var2) {
        return df.format(var1 / var2);
    }

    public String getPercent(Integer var1, Double var2) {
        return df.format(var1 * 1.0 / var2);
    }

    public String getPercent(Double var1, Integer var2) {
        return df.format(var1 / (var2 * 1.0));
    }

    public String getPercent(Integer var1, Integer var2) {
        return df.format(var1 * 1.0 / (var2 * 1.0));
    }

    public String formatPercent(Double number) {
        nt.setMinimumFractionDigits(2);
        return nt.format(number);
    }

    public String formatPercent(Double var1, Double var2) {
        nt.setMinimumFractionDigits(2);
        return nt.format(var1 / var2);
    }

    public String formatPercent(Integer var1, Double var2) {
        nt.setMinimumFractionDigits(2);
        return nt.format(var1 * 1.0 / var2);
    }

    public String formatPercent(Double var1, Integer var2) {
        nt.setMinimumFractionDigits(2);
        return nt.format(var1 / (var2 * 1.0));
    }

    public String formatPercent(Integer var1, Integer var2) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(var1 * 1.0 / (var2 * 1.0));
    }

    public String formatTwoDecimal(Double var1) {
        return twoDecimal.format(var1);
    }

    public String formatTwoDecimal(String var1) {
        return twoDecimal.format(var1);
    }

    public String formatTwoDecimal(Double var1, Double var2) {
        if(var2==0){
            var2=1.0;
        }
        return twoDecimal.format(var1 / var2);
    }

    public String formatTwoDecimal(Integer var1, Double var2) {
        if(var2==0){
            var2=1.0;
        }
        return twoDecimal.format(var1 * 1.0 / var2);
    }

    public String formatTwoDecimal(Double var1, Integer var2) {
        if(var2==0){
            var2=1;
        }
        return twoDecimal.format(var1 / (var2 * 1.0));
    }

    public String formatTwoDecimal(Integer var1, Integer var2) {
        if(var2==0){
            var2=1;
        }
        return twoDecimal.format(var1 * 1.0 / (var2 * 1.0));
    }

    public Double formatTwoDecimalReturnDouble(Double var1) {
        if(var1==null){
            return 0.0;
        }
        return Double.valueOf(twoDecimal.format(var1));
    }

    public Double formatTwoDecimalReturnDouble(String var1) {
        if(var1==null){
            return 0.0;
        }
        return Double.valueOf(var1);
    }

    public Double formatTwoDecimalReturnDouble(Double var1, Double var2) {
        if(var2==0){
            var2=1.0;
        }
        return Double.valueOf(twoDecimal.format(var1 / var2));
    }

    public Double formatTwoDecimalReturnDouble(Integer var1, Double var2) {
        if(var2==0){
            var2=1.0;
        }
        return Double.valueOf(twoDecimal.format(var1 * 1.0 / var2));
    }

    public Double formatTwoDecimalReturnDouble(Double var1, Integer var2) {
        if(var2==0){
            var2=1;
        }
        return Double.valueOf(twoDecimal.format(var1 / (var2 * 1.0)));
    }

    public Double formatTwoDecimalReturnDouble(Integer var1, Integer var2) {
        if(var2==0){
            var2=1;
        }
        return Double.valueOf(twoDecimal.format(var1 * 1.0 / (var2 * 1.0)));
    }

    public Integer nullToZero(Integer integer){
        return integer==null?0:integer;
    }
    public Double nullToZero(Double d){
        return d==null?0:d;
    }
    public String nullToZero(String d){
        return d==null?"0":d;
    }
    public Boolean nullToZero(Boolean d){
        return d != null;
    }
    public String ifNotNullGetString(Double d){
        if(d!=null){
            return String.valueOf(d);
        }else {
            return null;
        }
    }
    public String ifNotNullGetString(Integer d){
        if(d!=null){
            return String.valueOf(d);
        }else {
            return null;
        }
    }
    public String ifNotNullGetString(String s){
        if(s==null){
            return "";
        }else {
            return s;
        }
    }
}
