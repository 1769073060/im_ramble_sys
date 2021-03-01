package com.rzk.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @PackageName : com.rzk.netty
 * @FileName : DataContent
 * @Description :
 * @Author : rzk
 * @CreateTime : 2021/2/28 17:40
 * @Version : 1.0.0
 */
@Data
public class DataContent implements Serializable {
    private Integer action;//动作类型
    private ChatMsg chatMsg;//用户聊天内容
    private String extand;//扩张字段
}
