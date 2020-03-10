package org.mysise.natplus.common.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 *  数据转换
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 22:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataPacket extends Packet {

    private byte[] bytes;

    private String token;

    private String channelId;

    @Override
    public Byte getCommand() {
        return Command.DATA;
    }
}
