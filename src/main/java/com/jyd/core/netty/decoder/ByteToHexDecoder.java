package com.jyd.core.netty.decoder;

import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:09
 */
@Slf4j
public class ByteToHexDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> in) throws Exception {
        String hex = ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes()).toUpperCase();
        log.info("服务器收到:{}", hex);
        if (!StrUtil.equalsIgnoreCase(hex.substring(0, 8), "5AA5A55A")) {
            log.info("报文头不正确，丢弃");
            ByteBuf buffer = ctx.alloc().buffer(byteBuf.readableBytes());
            byteBuf.readBytes(buffer, byteBuf.readerIndex(), byteBuf.readableBytes());
            return;
        }
        if (hex.length() < 12) {
            log.info("报文长度不足：未解析到报文长度值");
            return;
        } else {
            String lengthStr = hex.substring(8, 12);
            int length = Integer.parseInt(lengthStr, 16);
            if (hex.length() != length) {
                log.info("报文长度不足：解析到长度值[{}],报文实际长度[{}]", length, hex.length());
                return;
            }
            ByteBuf buffer = ctx.alloc().buffer(byteBuf.readableBytes());
            byteBuf.readBytes(buffer, byteBuf.readerIndex(), length);
            in.add(hex);
        }
    }
}
