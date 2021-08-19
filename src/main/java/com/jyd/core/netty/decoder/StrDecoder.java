package com.jyd.core.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Zhenlin Jin
 * @date 2021/8/18 16:01
 */
@Slf4j
@ChannelHandler.Sharable
public class StrDecoder extends StringDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> in) throws Exception {
        String s = ByteBufUtil.hexDump(msg).toUpperCase();
        log.info("服务器收到:{}", s);
        in.add(s);
    }
}
