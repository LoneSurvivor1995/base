package com.example.baseio.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerInHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(NettyServerInHandler.class);


    //每当从客户端接收到新数据时，都会使用收到的消息调用此方法。
    @Override
    // ChannelHandlerContext对象提供的各种操作，使您可以触发各种I / O的事件和操作。
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("HelloServerInHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(result1);
        String resultStr = new String(result1);
        // 接收并打印客户端的信息
        System.out.println("Client said:" + resultStr);
        // 释放资源，这行很关键
        result.release();

        // 向客户端发送消息
        String response = "I am ok!";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
        // ByteBuf是一个引用计数对象，必须通过该release()方法显式释放
        ReferenceCountUtil.release(msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }
}

