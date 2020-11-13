package com.tonylp.server.common.protocol.request;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public OperationType getCommand() {
        return OperationType.HEARTBEAT_REQUEST;
    }
}
