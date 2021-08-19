package com.jyd.core.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * @author Zhenlin Jin
 * @date 2021/8/18 14:47
 */
public class ByteDecoder extends LengthFieldBasedFrameDecoder {
    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthFieldEndOffset;
    private final int initialBytesToStrip;

    public ByteDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;
        this.lengthFieldLength = lengthFieldLength;
        this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
        this.initialBytesToStrip = initialBytesToStrip;
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        long unadjustedFrameLength = super.getUnadjustedFrameLength(buf, offset, length, order);
        return unadjustedFrameLength - this.lengthFieldEndOffset;
    }
}
