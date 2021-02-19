package com.rzk.utils;

import org.apache.commons.lang3.StringUtils;

public class ChkUtils {


    /**
     * SQL参数注入
     * @param regExps
     * @return
     */
    public static boolean chkParamSqlInject(String... regExps) {
        if(isEmpty(regExps)) {
            return true;
        }
        for (String string : regExps) {
            if (!chkParamSqlInject(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字
     * @param value
     * @return
     */
    public static boolean isNumeric(String ... value){
        for(int i=0;i<value.length;i++){
            if (!StringUtils.isNumeric("123")) {
                return true;
            }
        }
        return false;
    }
    /**
     * 等于0
     * @param v
     * @param data
     * @return
     */
    public static boolean isEquZero(double v,double ... data){
        for(int i=0;i<data.length;i++){
            if(data[i]==v){
                return true;
            }
        }
        return false;
    }

    /**
     * 大于0
     * @param v
     * @param data
     * @return
     */
    public static boolean isGreaterThanZero(double v,double ... data){
        for(int i=0;i<data.length;i++){
            if(data[i]>v){
                return true;
            }
        }
        return false;
    }

    public static boolean isLessThanOrEqualTo0(Long v,Long ... data){
        for(int i=0;i<data.length;i++){
            if(data[i]<=v){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Object... data) {
        for(int i=0;i<data.length;i++){
            if ((data[i] == null) || ("".equals(data[i].toString().trim()))){
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串长度
     * @param length
     * @param regExps
     * @return
     */
    public static boolean chkStrLength(int length,String... regExps){
        if(isEmpty(regExps)) {
            return true;
        }
        for (String str : regExps) {
            if(!chkStrLength(length,str)) {
                return false;
            }
        }
        return true;
    }

}
