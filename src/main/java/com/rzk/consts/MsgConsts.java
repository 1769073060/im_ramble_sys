package com.rzk.consts;

public class MsgConsts {
    /**
     * 200：表示成功
     * 500：表示错误，错误信息在msg字段中
     * 501：bean验证错误，不管多少个错误都以map形式返回
     * 502：拦截器拦截到用户token出错
     * 555：异常抛出信息
     */
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final int Error_Map_CODE = 501;
    public static final int Error_Token_CODE = 502;
    public static final int Error_Exception_CODE = 555;

    /**
     * 类型
     * 1个人认证
     * 2商家认证
     */
    public static final int PERSONAL_AUTHENTICATION = 1;
    public static final int MERCHANT_CERTIFICATION = 2;
    /**
     * 设置状态值
     * 0
     * 1
     * 2
     * ...
     * 7
     */
    public static final int ZERO_STATUS = 0;
    public static final int FIRST_STATUS = 1;
    public static final int SECOND_STATUS = 2;
    public static final int THIRD_STATUS = 3;
    public static final int FOURTH_STATUS = 4;
    public static final int FIFTH_STATUS = 5;
    public static final int SIXTH_STATUS = 6;
    public static final int SEVENTH_STATUS = 7;
    /*类型 商家id，用户id*/
    public static final Long BusinessId_Status = 0L;
    public static final Long UserId_Status = 0L;
    /**
     * 经纬度
     * RADIUS 半径千米
     * Double = 2 倍数
     * DEGREES = 180 度数
     */
    public static final int RADIUS = 6371;
    public static final int DOUBLE = 2;
    public static final int DEGREES = 180;

    /**
     * UNPAID 未支付
     * RAID   已支付
     */
    public static final int UNPAID = 0;
    public static final int RAID = 1;
    /**
     * NO_CLICK_RAID 未点击支付
     * CLICK_RAID    已点击支付
     */
    public static final int NO_CLICK_RAID = 0;
    public static final int CLICK_RAID = 1;

    public static final String SUCCESS_MSG = "操作成功";
    public static final String FAIL_MSG = "操作失败";
    public static final String SYSTEM_ERROR_MSG = "系统错误";
    public static final String PARAM_ERROR_MSG = "参数错误";
    public static final String MISS_PARAM_MSG = "缺少参数";
    public static final String AMOUNT_ERROR_MSG = "金额有误";
    public static final String PAY_ERROR_MSG = "支付有误";
    public static final String ALREADY_EXISTS= "不能重复添加";
    public static final String SMS_VALIDATE_FAILE = "验证码不正确";
    public static final String USER_IS_EXIST = "用户已存在";
    public static final String USER_IS_NOT_EXIST = "用户不存在";
    public static final String ACCOUNT_OR_PASSWORD_ERROR = "账号或密码错误";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String Enter_User_Name = "请输入用户名";
    public static final String SEND_SUCCESS = "发送成功";

}
