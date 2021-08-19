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
        log.info("方法开始byteBuf的读写指针[{},{}]", byteBuf.readerIndex(), byteBuf.writerIndex());
        String hex = ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes()).toUpperCase();
        log.info("服务器收到:{}", hex);
        ByteBuf heapBuffer = ctx.alloc().heapBuffer(byteBuf.readableBytes());
        byteBuf.readBytes(heapBuffer,byteBuf.resetReaderIndex().readableBytes());
        in.add(hex);
    }
}
