package org.mysise.natplus.common.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  连接协议
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 23:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConnectPacket extends Packet {

    private String channelId;

    @Override
    public Byte getCommand() {
        return Command.CONNECT;
    }
}
