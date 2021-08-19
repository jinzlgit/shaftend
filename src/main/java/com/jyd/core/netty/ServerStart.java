package com.jyd.core.netty;

import com.jyd.core.netty.decoder.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Zhenlin Jin
 * @date 2021/8/11 11:20
 */
@Slf4j
@Component
public class ServerStart implements CommandLineRunner {

    @Value("${shaft.netty.port}")
    private int port;

    private static final ServerBootstrap b = new ServerBootstrap();

    @Override
    @Async
    public void run(String... args) throws Exception {
        // 创建反应器线程组
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 1.设置反应器线程组
            b.group(boss, worker);
            // 2.设置nio类型的通道
            b.channel(NioServerSocketChannel.class);
            // 3.设置监听端口
            b.localAddress(port);
            // 4.设置通道的参数
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 5.装配子通道流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    sc.pipeline()
                            .addLast(new ByteDecoder(1024, 4,
                                    2, 0, 0))
                            .addLast(new StrDecoder())
                            .addLast(new HexDecoder())
                            .addLast(new ParseHandler());
                }
            });
            // 6.开始绑定服务器
            ChannelFuture channelFuture = b.bind().sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("Netty服务启动成功,监听端口[{}]", port);
                    }
                }
            });
            // 7.等待通道关闭的异步任务结束
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
