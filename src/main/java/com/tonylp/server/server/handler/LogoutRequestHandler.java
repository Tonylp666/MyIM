package com.tonylp.server.server.handler;

import com.tonylp.server.common.protocol.Packet;
import com.tonylp.server.common.protocol.request.LogoutRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket>  {
    public static final LogoutRequestHandler INSTANCE= new LogoutRequestHandler();

    private LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        // todo
    }
}
