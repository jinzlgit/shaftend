package com.jyd.core.netty.decoder;

import com.jyd.core.domain.BaseDTO;
import com.jyd.core.domain.Online;
import com.jyd.core.parse.ParsePolicy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:23
 */
@Slf4j
public class ParseHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        BaseDTO baseDTO = (BaseDTO) msg;
        Online.on(channelId, baseDTO.getNumber());
        ParsePolicy.parse(baseDTO);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Online.off(ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("连接出错:{}", cause.getMessage());
        StackTraceElement[] stackTrace = cause.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            System.out.println(element);
        }
        Online.off(ctx.channel().id().asShortText());
    }
}
