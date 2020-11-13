package com.tonylp.server.server.handler;

import com.tonylp.server.common.protocol.request.MessageRequestPacket;
import com.tonylp.server.common.protocol.response.MessageResponsePacket;
import com.tonylp.server.common.session.Session;
import com.tonylp.server.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    private static Log LOG = LogFactory.getLog(MessageRequestHandler.class);
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        long beginTime = System.currentTimeMillis();

        Session session = SessionUtil.getSession(ctx.channel());

        //通过 session 构造要发送的消息
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromId(session.getUid());
        responsePacket.setFromName(session.getUname());
        responsePacket.setMessage(msg.getMessage());

        //拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        //将消息发送给接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(responsePacket).addListener(future -> {
                //todo 接收 ack, 确认消息对方是否收到
                if (future.isDone()){

                }
            });
        }else{
            LOG.debug("");
        }
    }
}
