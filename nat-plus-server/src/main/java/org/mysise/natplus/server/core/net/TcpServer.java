package org.mysise.natplus.server.core.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  TCP 服务端启动
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/1 11:14
 */
@Slf4j
public class TcpServer {

    private Channel channel;

    private int port;

    public TcpServer(int port) {
        this.port = port;
    }

    /**
     * <p>
     *  绑定服务端口启动
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/1 11:17
     */
    public synchronized void bind(ChannelInitializer<NioSocketChannel> channelInitializer){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            channel = b.bind(port).sync().channel();
            channel.closeFuture().addListener((ChannelFutureListener) future -> {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            });
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.error("服务启动失败：{}", e.getMessage());
        }
    }


    public synchronized void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
