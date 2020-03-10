package org.mysise.natplus.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>
 *  连接服务端
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 0:42
 */
public class TcpConnection {

    /**
     * <p>
     *   创建链接
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/2 0:43
     */
    public void connect(String host, int port, ChannelInitializer<NioSocketChannel> channelInitializer) throws InterruptedException{

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(channelInitializer);

            Channel channel = b.connect(host, port).sync().channel();
            channel.closeFuture().addListener((ChannelFutureListener) future -> workerGroup.shutdownGracefully());
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            throw e;
        }
    }
}