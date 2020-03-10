package org.mysise.natplus.common.utils;

import io.netty.channel.Channel;
import org.mysise.natplus.common.attribute.Attributes;
import org.mysise.natplus.common.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  映射关系绑定  token -channel
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 11:49
 */
public class SessionUtil {
    // token -> channel 的映射
    private static final Map<String, Channel> TOKEN_CHANNEL = new ConcurrentHashMap<>();

    // channel_id -> CHANNEL
    private static final Map<String, Channel> CHANNEL_ID_CHANNEL = new ConcurrentHashMap<>();
    public static void bindSession(Session session, Channel channel) {
        TOKEN_CHANNEL.put(session.getToken(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            TOKEN_CHANNEL.remove(getSession(channel).getToken());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String token) {
        return TOKEN_CHANNEL.get(token);
    }

    public static void bindChannel(String channelId, Channel channel){
        CHANNEL_ID_CHANNEL.put(channelId,channel);
    }

    public static void unBindChannel(String channelId){
        CHANNEL_ID_CHANNEL.remove(channelId);
    }


    public static Channel getChannelByChannelId(String channelId){
        return CHANNEL_ID_CHANNEL.get(channelId);
    }
}
