package org.mysise.natplus.common.serializer.impl;


import com.alibaba.fastjson.JSON;
import org.mysise.natplus.common.serializer.Serializer;
import org.mysise.natplus.common.serializer.SerializerAlogrithm;
import org.mysise.natplus.common.serializer.SerializerDemo;

/**
 * <p>
 *  json 序列化 阿里巴巴 fastjson 作为序列化框架
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 10:42
 */
public class JSONSerializer implements Serializer {


    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }


    public static void main(String[] args){
        byte[] a = {1,2,3};
        SerializerDemo demo = new SerializerDemo();
        demo.setAge(11);
        demo.setName("fan");
        demo.setBytes(a);
        JSONSerializer serializer = new JSONSerializer();
        byte[] bytes = serializer.serialize(demo);
        System.out.println(JSON.toJSONString(serializer.deserialize(SerializerDemo.class,bytes)));
    }
}
