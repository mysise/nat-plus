package org.mysise.natplus.server.core.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.mysise.natplus.common.protocol.ConnectPacket;
import org.mysise.natplus.common.protocol.DataPacket;
import org.mysise.natplus.common.utils.SessionUtil;
import org.mysise.natplus.server.entity.Tunnel;
import org.mysise.natplus.server.service.ITunnelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;

/**
 * <p>
 *  客户端入口拦截器
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 21:05
 */
@Slf4j
public class ProxyHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ITunnelService iTunnelService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ConnectPacket connectPacket = new ConnectPacket();
        connectPacket.setChannelId(ctx.channel().id().asLongText());
        InetSocketAddress address = (InetSocketAddress)ctx.channel().localAddress();
        String hostName = address.getHostName();
        log.info("连接进来。host 为：[{}]", hostName);
        if(StringUtils.isNotEmpty(hostName)){
            Tunnel tunnel = iTunnelService.queryTokenByHostName(hostName);
            if(tunnel == null){
                log.warn("没有找到域名：[{}]，绑定的token", hostName);
                return;
            }
            String token = tunnel.getToken();
            // 获取对应连接的客户端channel
            Channel channel = SessionUtil.getChannel(token);
            if(channel == null){
                log.warn("host ：[{}], 绑定的客户端没有连接成功", hostName);
                return;
            }
            SessionUtil.bindChannel(connectPacket.getChannelId(), ctx.channel());
            channel.writeAndFlush(connectPacket);
        }
    }

    /**
     * <p
     *  处理请求入口、并且通过请求的域名找到相关代理的channel
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/2 21:08
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        byte[] data = (byte[]) msg;
        InetSocketAddress address = (InetSocketAddress)ctx.channel().localAddress();
        String hostName = address.getHostName();
        if(StringUtils.isNotEmpty(hostName)){
            Tunnel tunnel = iTunnelService.queryTokenByHostName(hostName);
            if(tunnel == null){
                log.warn("没有找到域名：[{}]，绑定的token", hostName);
                return;
            }
            String token = tunnel.getToken();
            // 获取对应连接的客户端channel
            Channel channel = SessionUtil.getChannel(token);
            if(channel == null){
                log.warn("host ：[{}], 绑定的客户端没有连接成功", hostName);
                return;
            }
            DataPacket dataPacket = new DataPacket();
            dataPacket.setToken(token);
            dataPacket.setBytes(data);
            dataPacket.setChannelId(ctx.channel().id().asLongText());
            SessionUtil.bindChannel(ctx.channel().id().asLongText(), ctx.channel());
            channel.writeAndFlush(dataPacket);
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindChannel(ctx.channel().id().asLongText());
        log.info("channelId:{},链接关闭", ctx.channel().id().asLongText());
    }
}
