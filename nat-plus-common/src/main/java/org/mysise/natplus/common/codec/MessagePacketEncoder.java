package org.mysise.natplus.common.codec;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import org.mysise.natplus.common.protocol.MessagePacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * <p>
 *  消息编码
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/3 15:57
 */
public class MessagePacketEncoder extends MessageToByteEncoder<MessagePacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessagePacket msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
            int cmd = msg.getCmd();
            dataOutputStream.writeInt(cmd);
            JSONObject metaDataJson = new JSONObject(msg.getMetaData());
            byte[] metaDataBytes = metaDataJson.toString().getBytes(CharsetUtil.UTF_8);
            dataOutputStream.writeInt(metaDataBytes.length);
            dataOutputStream.write(metaDataBytes);
            if (msg.getData() != null && msg.getData().length > 0) {
                dataOutputStream.write(msg.getData());
            }
            byte[] data = byteArrayOutputStream.toByteArray();
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
