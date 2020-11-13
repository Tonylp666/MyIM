package com.tonylp.server.common.protocol.request;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import lombok.Data;

import static com.tonylp.server.common.protocol.OperationType.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message){
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public OperationType getCommand() {
        return MESSAGE_REQUEST;
    }

}
