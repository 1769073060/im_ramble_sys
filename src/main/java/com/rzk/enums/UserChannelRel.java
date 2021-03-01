package com.rzk.enums;


import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName : com.rzk.enums
 * @FileName : UserChannelRel
 * @Description :用户id 和channel 的关联关系处理
 * @Author : rzk
 * @CreateTime : 2021/2/28 18:07
 * @Version : 1.0.0
 */
public class UserChannelRel {
    private static HashMap<String, Channel> manage = new HashMap<>();

    public static void put(String senderId,Channel channel){
        manage.put(senderId,channel);
    }

    public static Channel get(String senderId){//获取发送者id
        return manage.get(senderId);
    }

    public static void ouotput(){
        for (Map.Entry<String,Channel> entry : manage.entrySet()) {
            System.out.println("UserId:"+entry.getKey()
            +",ChannelId:"+entry.getValue().id().asLongText());
        }
    }
}
