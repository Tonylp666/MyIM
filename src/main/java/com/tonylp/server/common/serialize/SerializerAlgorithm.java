package com.tonylp.server.common.serialize;

public enum SerializerAlgorithm {

    JSON((byte)1),
    PROTOBUF((byte)2);

    private Byte type;

    public int getSerializerType(){
        return type;
    }

    SerializerAlgorithm(Byte serializerType) {
        this.type = serializerType;
    }
}
