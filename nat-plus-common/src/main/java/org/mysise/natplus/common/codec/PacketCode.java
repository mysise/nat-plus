package org.mysise.natplus.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.mysise.natplus.common.protocol.*;
import org.mysise.natplus.common.serializer.Serializer;
import org.mysise.natplus.common.serializer.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  消息编解码
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 10:49
 */
public class PacketCode {

    /**
     *  魔数 写死
      */
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCode INSTANCE = new PacketCode();
    private static final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;
    private static final Map<Byte, Serializer> SERIALIZER_MAP;

    static {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(Command.REGISTER, RegisterPacket.class);
        PACKET_TYPE_MAP.put(Command.DATA, DataPacket.class);
        PACKET_TYPE_MAP.put(Command.CONNECT, ConnectPacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlogrithm(), serializer);
    }



    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 创建 ByteBuf 对象
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
//        // 2. 序列化 java 对象
//        byte[] bytes = Serializer.DEFAULT.serialize(packet);
//
//        // 3. 实际编码过程
//        byteBuf.writeInt(MAGIC_NUMBER);
//        byteBuf.writeByte(packet.getVersion());
//        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
//        byteBuf.writeByte(packet.getCommand());
//        byteBuf.writeInt(bytes.length);
//        byteBuf.writeBytes(bytes);
//
//        return byteBuf;

        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public ByteBuf encode(Packet packet) {
         //1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return PACKET_TYPE_MAP.get(command);
    }
}
