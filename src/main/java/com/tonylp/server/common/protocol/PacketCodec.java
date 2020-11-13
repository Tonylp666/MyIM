package com.tonylp.server.common.protocol;

import com.tonylp.server.common.serialize.Serializer;
import com.tonylp.server.common.serialize.SerializerAlgorithm;
import com.tonylp.server.common.serialize.impl.JSONSerializer;
import com.tonylp.server.common.protocol.request.*;
import com.tonylp.server.common.protocol.response.*;
import com.tonylp.server.utils.EnumUtil;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.tonylp.server.common.protocol.OperationType.*;

public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<OperationType,Class<? extends Packet>> packetTypeMap;
    private final Map<SerializerAlgorithm, Serializer>  serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(REGISTER_REQUEST, RegistRequestPacket.class);
        packetTypeMap.put(REGISTER_RESPONSE, RegistResponsePacket.class);
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);
        packetTypeMap.put(ADD_FRIEND_REQUEST, AddFriendRequestPacket.class);
        packetTypeMap.put(ADD_FRIEND_RESPONSE, AddFriendResponsePacket.class);
        packetTypeMap.put(LIST_FRIEND_REQUEST, ListFriendRequestPacket.class);
        packetTypeMap.put(LIST_FRIEND_RESPONSE, ListFrienResponsePacket.class);
        packetTypeMap.put(SYNC_MESSAGE_REQUEST, SyncMessageRequestPacket.class);
        packetTypeMap.put(SYNC_MESSAGE_RESPONSE, SyncMessageResponsePacket.class);
        packetTypeMap.put(REMOVE_FRIEND_RESPONSE, RemoveFriendRequestPacket.class);
        packetTypeMap.put(REMOVE_FRIEND_REQUEST, RemoveBeatResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf,Packet packet){
        // 1、 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        /**
         *  encode :
         *      magic_num(4 bytes) | version(1 byte) | serializer_type(1 byte)
         *      command_type(1 byte) | data_length(4 bytes) | data
         *
         *      这里对应了 Spliter 拆包。
         */
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm().getSerializerType());
        byteBuf.writeByte(packet.getCommand().getType());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializerType = byteBuf.readByte();

        byte commandType = byteBuf.readByte();

        int data_len = byteBuf.readInt();

        byte[] data = new byte[data_len];
        byteBuf.readBytes(data);

        Class<? extends  Packet> requestType = getRequestType(commandType);

        Serializer serializer = getSerializer(serializerType);

        if (requestType != null && serializer != null){
            return serializer.deserialize(requestType,data);
        }

        return null;
    }

    private Serializer getSerializer(byte serializer) {
        return serializerMap.get(EnumUtil.getByCode(SerializerAlgorithm.class,serializer));
    }

    private Class<? extends Packet> getRequestType(byte commandType) {
        return packetTypeMap.get(EnumUtil.getByCode(OperationType.class,commandType));
    }
}
