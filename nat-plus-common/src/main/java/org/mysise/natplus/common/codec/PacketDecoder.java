package org.mysise.natplus.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * <p>
 *  解码
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 15:33
 */
public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        out.add(PacketCode.INSTANCE.decode(in));
    }
}
