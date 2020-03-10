package org.mysise.natplus.common.protocol;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


/**
 * <p>
 *  通信抽象类
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 10:33
 */
@Data
public abstract class Packet {

    /**
     * <p>
     *  协议版本
     * <p>
     *
     * @since 2020/3/1 10:34
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
