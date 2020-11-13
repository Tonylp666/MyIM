package com.tonylp.server.common.protocol.request;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;
    @Override
    public OperationType getCommand() {
        return OperationType.CREATE_GROUP_REQUEST;
    }

}
