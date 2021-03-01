package com.rzk.netty;

import com.alibaba.druid.support.json.JSONUtils;
import com.rzk.config.SpringUtil;
import com.rzk.enums.MsgActionEnum;
import com.rzk.enums.UserChannelRel;
import com.rzk.service.IUserService;
import com.rzk.service.impl.UserServiceImpl;
import com.rzk.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName : com.rzk.websocket
 * @FileName : ChatHandler
 * @Description : 用于处理消息的handler  由于它的传输的数据的载体是frame  这个frame 在netty中,是用于websocket专门处理文本对象的,frame是消息的载体,此类叫:TextWebSocketFrame
 * @Author : rzk
 * @CreateTime : 2021/2/4 15:04
 * @Version : 1.0.0
 */
//@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    //用于记录和管理所有客户端的channel
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);



    /**
     *
     * @param ctx 通道上下文
     * @param msg 消息内容,获取客户端存放的消息就放在msg里面
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端所传输的消息
        String content = msg.text();
        //1.获取客户端发来的消息
        //装换对象
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        //2.判断消息类型,根据不同的类型来处理不同的业务
        //获取消息类型
        Integer action = dataContent.getAction();
        Channel channel = ctx.channel();
        if (action == MsgActionEnum.CONNECT.type){
            //2.1  当websocket 第一次 open 的时候,初始化channel ,把用的channel和 userId关联起来
            String senderId = dataContent.getChatMsg().getReceiverId();//获取对象再获取发送者id
            UserChannelRel.put(senderId,channel);

            //测试
            for (Channel c : users) {
                System.out.println(c.id().asLongText());
            }

            UserChannelRel.ouotput();

        }else if (action == MsgActionEnum.CHAT.type){
            //2.2  聊天类型的消息,把聊天记录保存到数据库,同时标记消息的签收状态 [未签收]
            //得到聊天的对象
            ChatMsg chatMsg = dataContent.getChatMsg();
            //得到聊天的内容
            String msgContent = chatMsg.getMsg();
            String senderId = chatMsg.getSenderId();
            String receiverId = chatMsg.getReceiverId();
            //保存消息到数据库,并且标记为未签收
            IUserService userServicesImpl = (IUserService) SpringUtil.getBean("userServiceImpl");//这里的userServicesImpl 是在service实现类的@Service注解命名(默认是实现类名字小写)
            String msgId = userServicesImpl.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            //发送消息
            Channel receiverChannel = UserChannelRel.get(receiverId);
            //判断为不为空
            if (receiverChannel == null){
                //为空就是未建立连接  离线用户
            }else {
                //当receiverChannel 不为空,从ChannelGroup 去查找对应的channel 是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel!=null){//如果不为空,就是查找存在
                    //用户在线
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContentMsg)
                            )
                    );
                }else {
                    //离线用户

                }
            }

        }else if (action == MsgActionEnum.SIGNED.type){
            IUserService userServicesImpl = (IUserService) SpringUtil.getBean("userServiceImpl");
            //2.3  签收消息类型,针对具体的消息进行签收,修改数据库中对应消息的签收状态 [已签收]
            //扩展字段在signed 类型消息中,代表需要去签收的消息id,逗号间隔
            String msgIdsStr = dataContent.getExtand();
            String[] msgId= msgIdsStr.split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String mid : msgId) {
                if (StringUtils.isNoneBlank(mid)){
                    //如果不为空就放入集合中
                    msgIdList.add(mid);
                }
            }
            System.out.println(msgIdList.toString());
            if (msgIdList!=null && !msgIdList.isEmpty() && msgIdList.size()>0){
                //消息批量签收
                userServicesImpl.updateMsgSigned(msgIdList);
            }

        }else if (action == MsgActionEnum.KEEPALIVE.type){
            //2.4  心跳类型的消息
        }

        /**
         *
         *
         *
         *
         *
         *
         *
         */
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
        System.out.println("客户端断开,channel对应的长Id为:"+ctx.channel().id().asLongText());
        System.out.println("客户端断开,channel对应的短Id为:"+ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        //如果发生了异常后关闭连接,同时从channelgroup移除
        users.remove(ctx.channel());
    }
}
















