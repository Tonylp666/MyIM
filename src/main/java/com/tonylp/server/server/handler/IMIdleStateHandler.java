package com.tonylp.server.server.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {
    private static final Log LOG  = LogFactory.getLog(IMIdleStateHandler.class);

    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler(){
        super(READER_IDLE_TIME,0,0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        LOG.info(READER_IDLE_TIME + " s, has not msg received，close connection!");
        ctx.channel().close();
    }
}
