package org.mysise.natplus.common.protocol;


import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>
 *  链接注册消息体
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 10:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterPacket extends Packet {


    /**
     * <p>
     *  用户令牌
     * <p>
     *
     * @since 2020/3/1 10:39
     */
    private String token;

    private Boolean success;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.REGISTER;
    }
}
