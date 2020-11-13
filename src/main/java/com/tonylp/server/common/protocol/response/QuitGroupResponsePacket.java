package com.tonylp.server.common.protocol.response;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;

public class QuitGroupResponsePacket extends Packet {
    @Override
    public OperationType getCommand() {
        return null;
    }
}
