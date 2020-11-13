package com.tonylp.server.utils;

import com.tonylp.server.common.attribute.Attributes;
import com.tonylp.server.common.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Log LOG = LogFactory.getLog(SessionUtil.class);

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static boolean hasLogin(Channel channel) {
        // todo
        return getSession(channel) != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static void bindSession(Session session, Channel channel) {
        //todo
        userIdChannelMap.put(session.getUid(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        // todo
        if (hasLogin(channel)){
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUid());
            // todo
            channel.attr(Attributes.SESSION).set(null);
            LOG.info(session + " 推出登陆！");
        }
    }

    public static Channel getChannel(String toUserId) {
        return userIdChannelMap.get(toUserId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {

    }
}
