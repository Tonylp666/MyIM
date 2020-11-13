package com.tonylp.server.common.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public abstract class Packet {
    /**
     *  Protocol version
     */
    @JSONField(deserialize = false,serialize = false)
    private Byte version = 1;


    @JSONField(serialize = false)
    public abstract OperationType getCommand();
}
