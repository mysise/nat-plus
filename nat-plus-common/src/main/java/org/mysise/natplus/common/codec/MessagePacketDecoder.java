package org.mysise.natplus.common.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.mysise.natplus.common.protocol.MessagePacket;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  消息解码
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/3 15:57
 */
public class MessagePacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> list) throws Exception {
        int type = msg.readInt();
        int metaDataLength = msg.readInt();
        CharSequence metaDataString = msg.readCharSequence(metaDataLength, CharsetUtil.UTF_8);
        Map<String, Object> metaData = JSON.parseObject(metaDataString.toString(), Map.class);
        byte[] data = null;
        if (msg.isReadable()) {
            data = ByteBufUtil.getBytes(msg);
        }
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.setCmd(type);
        messagePacket.setMetaData(metaData);
        messagePacket.setData(data);
        list.add(messagePacket);
    }
}
