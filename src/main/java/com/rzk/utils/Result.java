package com.rzk.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @PackageName : com.rzk.utils
 * @FileName : Result
 * @Description : 返回结果 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理,它主要是做一些响应的状态以及消息进行返回，并且你也需要包装一些自己的的数据，都是没有问题，
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 * @Author : rzk
 * @CreateTime : 31/1/2021 上午3:32
 * @Version : 1.0.0
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 返回响应的代码
     */
    private int code;

    /**
     * 返回响应消息
     */
    private String msg;

    /**
     * 返回响应数据
     */
    private T data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
