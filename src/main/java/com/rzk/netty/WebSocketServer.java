package com.rzk.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @PackageName : com.rzk.websocket
 * @FileName : WebSocketServer
 * @Description :
 * @Author : rzk
 * @CreateTime : 2021/2/4 10:23
 * @Version : 1.0.0
 */
@Component
public class WebSocketServer {

    private static class SingletionWSServer {
        static final WebSocketServer instance = new WebSocketServer();
    }


    public static WebSocketServer getInstance() {
        return SingletionWSServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitializer());
    }

    public void start() throws InterruptedException {
        this.future = server.bind("192.168.3.201",8890);
        Thread.sleep(1000);
        if (future.isSuccess()) {
            System.out.println("启动 Netty 成功");
        }
    }
}
