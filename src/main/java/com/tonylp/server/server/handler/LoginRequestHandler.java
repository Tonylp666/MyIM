package com.tonylp.server.server.handler;

import com.tonylp.server.common.protocol.request.LoginRequestPacket;
import com.tonylp.server.common.protocol.response.LoginResponsePacket;
import com.tonylp.server.common.session.Session;
import com.tonylp.server.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final Log LOG = LogFactory.getLog(LoginRequestHandler.class);
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    private LoginRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket request) throws Exception {
        LoginResponsePacket response = new LoginResponsePacket();
        response.setVersion(request.getVersion());
        response.setUserName(request.getUname());

        if (valid(request)){
            response.setSuccess(true);
            String uid = request.getUid();
            response.setUserId(uid);
            LOG.info("[ "+ request.getUname() + " ], login success.");
            SessionUtil.bindSession(new Session(uid,request.getUname()),ctx.channel());
        }else{
            response.setReason("账号密码校验失败！");
            response.setSuccess(false);
            LOG.error(new Date() + " " + request.getUname() + " login faild.");
            //todo 应该转到重新登陆
        }
        ctx.writeAndFlush(response);
    }

    private boolean valid(LoginRequestPacket request) {
        // todo 判断是否登录成功，走数据库。
        return false;
    }

    /**
     *  channelInactive, 当服务端、客户端 close.或者handler 没有捕获异常 会触发此操作。
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.debug("LoginRequestHandler#channelInactive() was triggered. ");
        SessionUtil.unBindSession(ctx.channel());
    }
}
