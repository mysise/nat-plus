package org.mysise.natplus.common.serializer;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SerializerDemo {
    private int age;

    @JSONField(deserialize = false, serialize = false)
    private String name;

    private byte[] bytes;
}
