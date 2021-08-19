package com.jyd.core.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.*;

import java.nio.ByteOrder;
import java.util.List;

/**
 * @author Zhenlin Jin
 * @date 2021/8/18 14:47
 */
public class ByteDecoder extends ByteToMessageDecoder {
    /** 字节顺序（大端模式/小端模式） */
    private final ByteOrder byteOrder;
    /** 缓冲区接收的最大[字节]长度 */
    private final int maxFrameLength;
    /** 长度字段偏移量 */
    private final int lengthFieldOffset;
    /** 长度字段的长度（字节数） */
    private final int lengthFieldLength;
    /** 长度字段字节结束（末尾）的偏移量 */
    private final int lengthFieldEndOffset;
    /** 长度调整值 */
    private final int lengthAdjustment;
    /** 单条需要跳过的字节数 */
    private final int initialBytesToStrip;
    /** 快速失败（默认为 true） */
    private final boolean failFast;
    /** 丢弃过长的缓冲区数据 */
    private boolean discardingTooLongFrame;
    /** 过长数据帧的长度 */
    private long tooLongFrameLength;
    /** 丢弃的字节数 */
    private long bytesToDiscard;

    /**
     *
     * @param maxFrameLength 缓冲区接收的最大[字节]长度
     * @param lengthFieldOffset 长度字段偏移量
     * @param lengthFieldLength 长度字段的长度（字节数）
     */
    public ByteDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }

    /**
     *
     * @param maxFrameLength 缓冲区接收的最大[字节]长度
     * @param lengthFieldOffset 长度字段偏移量
     * @param lengthFieldLength 长度字段的长度（字节数）
     * @param lengthAdjustment 长度调整值
     * @param initialBytesToStrip 单条报文需要跳过的字节数
     */
    public ByteDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, true);
    }

    /**
     *
     * @param maxFrameLength 缓冲区接收的最大[字节]长度
     * @param lengthFieldOffset 长度字段偏移量
     * @param lengthFieldLength 长度字段的长度（字节数）
     * @param lengthAdjustment 长度调整值
     * @param initialBytesToStrip 单条报文需要跳过的字节数
     * @param failFast 快速失败
     */
    public ByteDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        // ByteOrder.BIG_ENDIAN
        // 常量表示大端字节序。 按照此顺序，多字节值的字节按从最重要到最不重要的顺序排列
        this(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    /**
     *
     * @param byteOrder 字节顺序
     * @param maxFrameLength 缓冲区接收的最大[字节]长度
     * @param lengthFieldOffset 长度字段偏移量
     * @param lengthFieldLength 长度字段的长度（字节数）
     * @param lengthAdjustment 长度调整值
     * @param initialBytesToStrip 单条报文需要跳过的字节数
     * @param failFast 快速失败
     */
    public ByteDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        // 必须设置字节顺序，默认大端排序。 按照此顺序，多字节值的字节按从最重要到最不重要的顺序排列
        if (byteOrder == null) {
            throw new NullPointerException("byteOrder");
        }
        // 缓冲区最大长度必须大于0
        else if (maxFrameLength <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength);
        }
        // 长度字段偏移量必须 >= 0，即必须规定长度字段的位置
        else if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset);
        }
        // 单条报文需要跳过的字节数必须 >= 0，即最多不跳过字节
        else if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip);
        }
        // 缓冲区最大长度必须 >= 长度字段偏移量 + 长度字段的长度
        else if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + lengthFieldOffset + ") + " + "lengthFieldLength (" + lengthFieldLength + ").");
        }
        // 初始化入站处理器（Netty的解析器属于入站处理器的一种）
        else {
            this.byteOrder = byteOrder;
            this.maxFrameLength = maxFrameLength;
            this.lengthFieldOffset = lengthFieldOffset;
            this.lengthFieldLength = lengthFieldLength;
            this.lengthAdjustment = lengthAdjustment;
            // 长度字段值末尾的偏移量 = 长度字段值的偏移量 + 长度字段的长度
            this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
            this.initialBytesToStrip = initialBytesToStrip;
            this.failFast = failFast;
        }
    }

    /**
     * 解析方法（Netty流水线入站处理器调用的方法），不能被重写
     * @param ctx 处理器上下文
     * @param in 缓冲区
     * @param out 解析完向后传递的容器
     * @throws Exception 解析出错抛出异常
     */
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 真正的解析方法，可以根据需要重写
        Object decoded = this.decode(ctx, in);
        // 如果解析完不为空，表示解析成功，将数据向后传递（到下一个入站处理器）
        // 解析为空，也就是发生了半包现象，需要等待剩余的数据一起解析
        if (decoded != null) {
            // 解析成功后，得到是只包含单条报文中的主要数据的一个新缓冲区
            // 传入下一个处理器
            out.add(decoded);
        }
    }

    /**
     * 解析缓冲区的数据，可以根据需要重写
     *
     * @param ctx 通道处理器上下文
     * @param in 缓冲区
     * @return 解析后的数据（如果解析成功，返回一个 ByteBuf）
     * @throws Exception 抛出解析时的异常
     */
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 是否丢弃过长的数据（超过maxFrameLength），默认值为 false，所以第一次解析时不走这个逻辑
        // 根据长度字段计算得到的数据长度已经大于设置的单条最大长度了，并且传过来的可读字节没有全部
        // 获取并跳过这个过长数据，需要在下一次读取时将剩下的数据丢弃掉
        if (this.discardingTooLongFrame) {
            // 丢弃的字节数。本次读取时包含上条过长报文未跳过的剩下的需要丢弃的字节
            // 具体的计算 在下文
            long bytesToDiscard = this.bytesToDiscard;
            // 计算 本次需要丢弃字节数 和 缓冲区可读字节数 两者的最小值
            int localBytesToDiscard = (int)Math.min(bytesToDiscard, (long)in.readableBytes());
            // 缓冲区跳过读取上行的最小值
            in.skipBytes(localBytesToDiscard);
            // 需要丢弃的字节数 - 已经丢弃的字节数 = 还需要继续丢弃的字节数
            // 本质就是：这次的可读字节数小于需要丢弃的字节数，还需要继续丢弃
            bytesToDiscard -= (long)localBytesToDiscard;
            // 将 还需要继续丢弃的字节数 赋值给全局变量，方便下次读取时继续丢弃字节
            this.bytesToDiscard = bytesToDiscard;
            // 是否需要失败（参数：不是第一次检测过长数据帧）
            this.failIfNecessary(false);
        }

        // 如果可读字节数 没有 达到长度字段末尾，就返回null继续等待数据。
        // 即解决半包问题
        if (in.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        } else { // 可读字节可以解析到 长度字段了
            // 实际长度字段偏移量 = 缓冲区读指针 +（右移） 长度字段的偏移量
            int actualLengthFieldOffset = in.readerIndex() + this.lengthFieldOffset;
            // 获取未调整的数据帧长度（此方法是根据长度字段的值计算要传输的数据帧长度）
            long frameLength = this.getUnadjustedFrameLength(in, actualLengthFieldOffset, this.lengthFieldLength, this.byteOrder);
            // 如果计算出 数据帧长度 小于0
            if (frameLength < 0L) {
                // 跳过读取长度字段末尾之前的字节（无效嘛）
                in.skipBytes(this.lengthFieldEndOffset);
                // 并抛出异常（接收到的长度字段为负值）
                throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
            } else { // 经过计算得到正确的数据帧长度了
                // 数据帧长度 加上 长度字段末尾的偏移量 再加上 调整偏移值。
                // 其实就是计算出了 一次传输的所有的字节（在我的业务中就是需要获取的一个完整报文）
                frameLength += (long)(this.lengthAdjustment + this.lengthFieldEndOffset);

                // 这个判断的意思是：上面已经得到了数据帧的长度，说明所有的字节长度一定比长度字段的末尾偏移量大
                if (frameLength < (long)this.lengthFieldEndOffset) {
                    in.skipBytes(this.lengthFieldEndOffset);
                    throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
                } else if (frameLength > (long)this.maxFrameLength) {
                    // 如果一次传输的所有字节数 > 设置的最大数据帧长度（构造函数的参数）

                    // 计算 丢弃字节数 = 计算出的一次传输的所有字节数 - 可读的字节数
                    long discard = frameLength - (long)in.readableBytes();
                    // 设置过长数据帧长度
                    this.tooLongFrameLength = frameLength;
                    // 如果可读字节数 > 过长数据帧长度 > 设置的最大数据帧长度
                    if (discard < 0L) {
                        // 直接跳过读取（读指针右移跳过）
                        in.skipBytes((int)frameLength);
                    } else { // 如果可读字节数 <= 过长数据帧长度
                        // 设置丢弃过长数据帧为 true
                        this.discardingTooLongFrame = true;
                        // 设置需要丢弃的字节数。这个是下次读取时需要丢弃的，因为过长数据帧长度比可读字节数还大，
                        // 说明过长的字节还没传输完，下次读取缓冲区时要把剩下的字节丢弃掉
                        this.bytesToDiscard = discard;
                        // 直接跳过读取（读指针右移跳过）
                        in.skipBytes(in.readableBytes());
                    }
                    // 是否需要失败（参数：是否是第一次检测过长数据帧）
                    this.failIfNecessary(true);
                    // 进入下一次读取
                    return null;
                } else { // 这个 else 就是正常的读取了，传输的字节数 <= 设置的最大数据帧长度
                    // 再一个局部变量保存传输的字节数（增加局部变量是为了便于代码阅读，是一种编程习惯）
                    int frameLengthInt = (int)frameLength;
                    // 如果可读字节数不够计算出的一条完整报文，返回 null 等待后续数据一起读取
                    if (in.readableBytes() < frameLengthInt) {
                        return null;
                    } else if (this.initialBytesToStrip > frameLengthInt) {
                        // 设置的单条报文需要跳过的字节数 > 一条完整报文。说明有问题呀，跳过读取，并抛异常
                        in.skipBytes(frameLengthInt);
                        throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
                    } else { // 终于到正确的解析逻辑啦。（单条需要跳过的字节 < 一条完整报文 < 设置的最大传输字节）
                        // 跳过单条需要跳过的字节（一般就是报文头（或者叫请求头））
                        in.skipBytes(this.initialBytesToStrip);
                        // 获取读指针位置，这个位置往后就是传输的主要数据了
                        int readerIndex = in.readerIndex();
                        // 计算实际数据长度（上行说的传输的主要数据）= 完整报文字节数 - 单条需要跳过的字节数
                        int actualFrameLength = frameLengthInt - this.initialBytesToStrip;
                        // 提取数据帧
                        ByteBuf frame = this.extractFrame(ctx, in, readerIndex, actualFrameLength);
                        // 设置新的读指针位置（向后移动 一个完整报文的位置，就是到了下一个报文的头部）
                        in.readerIndex(readerIndex + actualFrameLength);
                        // 返回只包含单条报文主要数据的一个新缓冲区
                        return frame;
                    }
                }
            }
        }
    }

    /**
     * 获取未调整的数据帧
     *
     * @param buf 缓冲区
     * @param offset 长度字段偏移量
     * @param length 长度字段的长度值
     * @param order 字节顺序（大端/小端）
     * @return 返回数据帧
     */
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        // 局部变量：数据帧长度
        long frameLength;
        // 根据长度字段的长度值 计算数据帧长度。（为什么到 8 呢？因为基本数据类型最大 8 个字节）
        switch(length) {
            case 1:
                frameLength = (long)buf.getUnsignedByte(offset);
                break;
            case 2:
                frameLength = (long)buf.getUnsignedShort(offset);
                break;
            case 3:
                frameLength = (long)buf.getUnsignedMedium(offset);
                break;
            case 4:
                frameLength = buf.getUnsignedInt(offset);
                break;
            case 5:
            case 6:
            case 7:
            default:
                throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
            case 8:
                frameLength = buf.getLong(offset);
        }
        // 返回计算出的数据帧长度
        return frameLength - 6;
    }

    /**
     * 是否需要失败
     * 这个方法时不管怎么样，都要进行失败抛出异常（专门用于传输数据长度 > 设置的最大数据长度）
     * 只是根据需要是在第一次检测时就快速失败，还是在后续检测中再进行失败抛出异常的区别
     *
     * @param firstDetectionOfTooLongFrame 是否是第一次检测过长的数据帧
     */
    private void failIfNecessary(boolean firstDetectionOfTooLongFrame) {
        // 如果需要丢弃的字节数为 0
        if (this.bytesToDiscard == 0L) {
            // 用局部变量接收 过长的数据长度，用来记录异常的
            long tooLongFrameLength = this.tooLongFrameLength;
            // 将全局变量过长的数据长度 赋值为 0
            this.tooLongFrameLength = 0L;
            // 将全局变量 是否丢弃过长数据 设置为 false
            this.discardingTooLongFrame = false;
            // 判断是否快速失败（即抛出异常）
            if (!this.failFast || this.failFast && firstDetectionOfTooLongFrame) {
                this.fail(tooLongFrameLength);
            }
        }
        // 如果需要丢弃的字节数大于 0
        else if (this.failFast && firstDetectionOfTooLongFrame) {
            this.fail(this.tooLongFrameLength);
        }

    }

    public static void main(String[] args) {
        System.out.println(!false || false && true);
        System.out.println(!false || (false && false));
        System.out.println(!false || false && false);
    }

    /**
     * 提取数据帧（这下就是真的 传输的主要数据了）
     * @param ctx 通道处理器上下文
     * @param buffer 缓冲区
     * @param index 开始读取字节的位置（readerIndex）
     * @param length 需要读取的字节数
     * @return 返回一个缓冲区
     */
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        // 简单说就是把需要的字节数据 复制到一个新的缓冲区
        return buffer.retainedSlice(index, length);
    }

    private void fail(long frameLength) {
        if (frameLength > 0L) {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
        } else {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
        }
    }
}
