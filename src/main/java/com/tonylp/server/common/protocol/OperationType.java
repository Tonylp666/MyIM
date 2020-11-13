package com.tonylp.server.common.protocol;

import java.io.Serializable;

/**
 *  定义操作类型，即需要实现的功能。
 */
public enum OperationType implements Serializable {

    REGISTER_REQUEST((byte)0),
    REGISTER_RESPONSE((byte)1),

    LOGIN_REQUEST((byte)2),
    LOGIN_RESPONSE((byte)3),

    MESSAGE_REQUEST((byte)4),
    MESSAGE_RESPONSE((byte)5),

    LOGOUT_REQUEST((byte)6),
    LOGOUT_RESPONSE((byte)7),

    CREATE_GROUP_REQUEST((byte)8),
    CREATE_GROUP_RESPONSE((byte)9),

    LIST_GROUP_MEMBERS_REQUEST((byte)10),
    LIST_GROUP_MEMBERS_RESPONSE((byte)11),

    JOIN_GROUP_REQUEST((byte)12),
    JOIN_GROUP_RESPONSE((byte)13),

    QUIT_GROUP_REQUEST((byte)14),
    QUIT_GROUP_RESPONSE((byte)15),

    //(byte) 发送群聊消息(byte)
    GROUP_MESSAGE_REQUEST((byte)16),
    GROUP_MESSAGE_RESPONSE((byte)17),

    HEARTBEAT_REQUEST((byte)18),
    HEARTBEAT_RESPONSE((byte)19),

    ADD_FRIEND_REQUEST((byte)20),
    ADD_FRIEND_RESPONSE((byte)21),

    LIST_FRIEND_REQUEST((byte)22),
    LIST_FRIEND_RESPONSE((byte)23),

    SYNC_MESSAGE_REQUEST((byte)24),
    SYNC_MESSAGE_RESPONSE((byte)25),

    REMOVE_FRIEND_RESPONSE((byte)26),
    REMOVE_FRIEND_REQUEST((byte)27);
    
    private Byte type;

    public int getType() {
        return type;
    }

    OperationType(Byte type) {
        this.type = type;
    }
}
