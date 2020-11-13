package com.tonylp.server.common.protocol.response;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import lombok.Data;

@Data
public class LogoutResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public OperationType getCommand() {
        return OperationType.LOGIN_RESPONSE;
    }
}
