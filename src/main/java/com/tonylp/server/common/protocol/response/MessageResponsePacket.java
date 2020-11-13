package com.tonylp.server.common.protocol.response;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import lombok.Data;

import static com.tonylp.server.common.protocol.OperationType.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String fromId;

    private String fromName;

    private String message;
    @Override
    public OperationType getCommand() {
        return MESSAGE_RESPONSE;
    }

}
