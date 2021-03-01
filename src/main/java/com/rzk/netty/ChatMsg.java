package com.rzk.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @PackageName : com.rzk.netty
 * @FileName : ChatMsg
 * @Description :
 * @Author : rzk
 * @CreateTime : 2021/2/28 17:42
 * @Version : 1.0.0
 */
@Data
public class ChatMsg implements Serializable {
    private String senderId;//发送者id
    private String receiverId;//接收者id
    private String msg;//聊天内容
    private String msgId;//两个人聊天记录的id   用于消息的签收
}
