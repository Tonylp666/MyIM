package com.tonylp.server.common.protocol.request;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String uname;
    private String passwd;
    private String nickname;
    private String uid;

    @Override
    public OperationType getCommand() {
        return OperationType.LOGIN_REQUEST;
    }
}
