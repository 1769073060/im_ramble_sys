package com.rzk.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @PackageName : com.rzk.websocket
 * @FileName : WSServerInitialzer
 * @Description :
 * @Author : rzk
 * @CreateTime : 2021/2/4 10:42
 * @Version : 1.0.0
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //获取管道(pipeline)
        ChannelPipeline pipeline = channel.pipeline();
        //websocket基于http协议,所需要的http 编解码
        pipeline.addLast(new HttpServerCodec());
        //在http上有一些数据流产生,有大有小,我们对其进行处理,既然如此,我们需要使用netty,对数据流写提供支持,这个类ChunkedWriteHandler
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage  进行聚合处理,聚合成request 或 response
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        /**
         * 本 handler 会帮你处理一些繁重复杂的事情
         * 会帮你处理握手动作: handshaking (close,ping,pong)
         * 对于websocket 来讲  都是以frams进行传输的,不同的数据类型对应的frams 也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //创建自定义的handler
        pipeline.addLast(new ChatHandler());

    }
}
