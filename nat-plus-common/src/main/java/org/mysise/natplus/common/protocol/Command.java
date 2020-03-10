package org.mysise.natplus.common.protocol;

/**
 * <p>
 *  协议指令集合
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 10:35
 */

public interface Command {
    /**
     * <p>
     *  登录指令
     * <p>
     *
     * @since 2020/3/1 10:36
     */
    Byte REGISTER = 1;

    Byte DATA = 2;

    Byte CONNECT = 3;
}
