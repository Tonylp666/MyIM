package com.tonylp.server.common.serialize.impl;


import com.tonylp.server.common.serialize.Serializer;
import com.tonylp.server.common.serialize.SerializerAlgorithm;

public class ProtoBufSerializer implements Serializer {
    @Override
    public SerializerAlgorithm getSerializerAlgorithm() {
        return SerializerAlgorithm.PROTOBUF;
    }

    @Override
    public byte[] serialize(Object object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }
}
