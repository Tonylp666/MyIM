package com.tonylp.server.common.protocol.request;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;

public class GroupMessageRequestPacket extends Packet {
    @Override
    public OperationType getCommand() {
        return null;
    }
}