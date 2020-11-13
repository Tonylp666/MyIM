package com.tonylp.server.server.handler;

import com.tonylp.server.common.protocol.request.CreateGroupRequestPacket;
import com.tonylp.server.common.protocol.response.CreateGroupResponsePacket;
import com.tonylp.server.utils.IDUtil;
import com.tonylp.server.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    private static final Log LOG = LogFactory.getLog(CreateGroupRequestHandler.class);
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        List<String> userList = packet.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null){
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUname());
            }
        }

        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket response = new CreateGroupResponsePacket();
        response.setSuccess(true);
        response.setGroupId(groupId);
        response.setUserNameList(userNameList);

        channelGroup.writeAndFlush(response);

        SessionUtil.bindChannelGroup(groupId,channelGroup);
    }
}
