package org.mysise.natplus.common.protocol;

import lombok.Data;

import java.util.Map;

/**
 * <p>
 *  message 传输协议
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/3 15:54
 */
@Data
public class MessagePacket {

    private int cmd;

    private Map<String,Object> metaData;

    private byte[] data;
}
