package org.mysise.natplus.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.mysise.natplus.common.protocol.DataPacket;

/**
 * <p>
 *  本地代理处理
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 22:50
 */
public class LocalProxyHandler extends ChannelInboundHandlerAdapter {

    private ClientHandler clientHandler;

    private String channelId;

    public LocalProxyHandler(ClientHandler clientHandler, String channelId) {
        this.clientHandler = clientHandler;
        this.channelId = channelId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        byte[] data = (byte[]) msg;
        DataPacket dataPacket = new DataPacket();
        dataPacket.setBytes(data);
        dataPacket.setChannelId(channelId);
        clientHandler.getCtx().writeAndFlush(dataPacket);
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("本地连接断开");
    }
}
