package org.mysise.natplus.common.serializer;

import org.mysise.natplus.common.serializer.impl.JSONSerializer;

/**
 * <p>
 * 消息序列化
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 10:40
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
