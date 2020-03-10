package org.mysise.natplus.server.core.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.mysise.natplus.common.protocol.ConnectPacket;
import org.mysise.natplus.common.protocol.DataPacket;
import org.mysise.natplus.common.protocol.RegisterPacket;
import org.mysise.natplus.common.session.Session;
import org.mysise.natplus.common.utils.SessionUtil;
import org.mysise.natplus.server.service.ITunnelService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  跟客户端 通信的拦截器
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 23:55
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("有新客户端连接接入。。。"+ctx.channel().remoteAddress());
    }


    @Autowired
    private ITunnelService iTunnelService;
    /**
     * <p>
     *  数据响应
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/2 0:31
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        if (msg instanceof RegisterPacket) {
            RegisterPacket registerPacket = (RegisterPacket) msg;
            if (StringUtils.isNotEmpty(registerPacket.getToken()) && iTunnelService.tokenExist(registerPacket.getToken())){
                SessionUtil.bindSession(new Session(registerPacket.getToken()),ctx.channel());
                log.info("Token: {},链接服务器成功", registerPacket.getToken());
                registerPacket.setSuccess(true);
                registerPacket.setMessage("connect success");
                ctx.channel().writeAndFlush(registerPacket);
            }else {
                registerPacket.setSuccess(false);
                registerPacket.setMessage("Token is wrong");
                ctx.writeAndFlush(registerPacket);
            }
        }else if (msg instanceof ConnectPacket){
            log.info("链接信息");
        }else if(msg instanceof DataPacket){
            DataPacket dataPacket = (DataPacket) msg;
            Channel channel = SessionUtil.getChannelByChannelId(dataPacket.getChannelId());
            channel.writeAndFlush(dataPacket.getBytes());
        }
    }
}
