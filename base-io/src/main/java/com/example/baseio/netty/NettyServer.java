package com.example.baseio.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NettyServer {
    public void start(int port) throws Exception {
        // NioEventLoopGroup是一个处理I / O操作的多线程事件循环
        // “老板”，接受传入连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // “工人”，一旦老板接受连接并将接受的连接注册到工作人员，就处理被接受连接的流量。使用了多少个线程以及它们如何映射到创建的Channels取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置服务器的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 指定使用NioServerSocketChannel用于实例化new的类Channel来接受传入连接
                    .channel(NioServerSocketChannel.class)
                    // ChannelInitializer是一个特殊的处理程序，旨在帮助用户配置新的Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            // 注册handler

                            ch.pipeline().addLast("server",new NettyServerInHandler());
                            ch.pipeline().addLast(new NettyClientInHandler());
                        }
                    })
                    // option()是为了NioServerSocketChannel接受传入的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // Channel父母接受的ServerChannel
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.start(8000);

    }
}
