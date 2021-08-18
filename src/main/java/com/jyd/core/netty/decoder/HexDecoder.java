package com.jyd.core.netty.decoder;

import com.jyd.core.constant.CodeEnum;
import com.jyd.core.domain.BaseDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 22:01
 */
@Slf4j
public class HexDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String s, List<Object> in) throws Exception {
        String code = s.substring(22, 24);
        String number = s.substring(24, 28);
        in.add(BaseDTO.builder()
                .code(code)
                .number(number)
                .content(s)
                .build());
    }
}
