package com.jyd.core.netty.decoder;

import com.jyd.core.domain.BaseDTO;
import com.jyd.core.domain.Online;
import com.jyd.core.mq.MessageBlockingQueue;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:23
 */
@Slf4j
public class ParseHandler extends SimpleChannelInboundHandler<BaseDTO> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseDTO baseDTO) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        Online.on(channelId, baseDTO.getNumber());
        MessageBlockingQueue.offer(baseDTO);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Online.off(ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("连接出错:{}", cause.getMessage());
        Online.off(ctx.channel().id().asShortText());
    }
}
