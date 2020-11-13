package com.tonylp.server.common.protocol.response;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public OperationType getCommand() {
        return OperationType.CREATE_GROUP_RESPONSE;
    }

}
