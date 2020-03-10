package org.mysise.natplus.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.mysise.natplus.common.protocol.Packet;

/**
 * <p>
 *  编码
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 15:33
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCode.INSTANCE.encode(out, packet);
    }
}
