package com.tonylp.server.server.handler;

import com.tonylp.server.common.protocol.OperationType;
import com.tonylp.server.common.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

import static com.tonylp.server.common.protocol.OperationType.*;

public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    private static final Log LOG = LogFactory.getLog(IMHandler.class);

    public static final IMHandler INSTANCE = new IMHandler() ;

    private Map<OperationType,SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler(){
        handlerMap = new HashMap<>();
        //todo 具体执行任务的handler

        handlerMap.put(REGISTER_REQUEST, RegistRequestHandler.INSTANCE);
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(ADD_FRIEND_REQUEST, AddFriendHandler.INSTANCE);
        handlerMap.put(LIST_FRIEND_REQUEST, ListFriendHandler.INSTANCE);
        handlerMap.put(SYNC_MESSAGE_REQUEST, SyncMessageHandler.INSTANCE);
        handlerMap.put(REMOVE_FRIEND_REQUEST, RemoveFriendHandler.INSTANCE);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
