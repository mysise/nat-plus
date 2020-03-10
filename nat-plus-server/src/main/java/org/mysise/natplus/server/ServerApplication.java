package org.mysise.natplus.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.mybatis.spring.annotation.MapperScan;
import org.mysise.natplus.common.codec.PacketDecoder;
import org.mysise.natplus.common.codec.PacketEncoder;
import org.mysise.natplus.common.codec.Spliter;
import org.mysise.natplus.server.common.SpringUtils;
import org.mysise.natplus.server.core.handler.ProxyHandler;
import org.mysise.natplus.server.core.handler.ServerHandler;
import org.mysise.natplus.server.core.net.TcpServer;
import org.mysise.natplus.server.service.ITunnelService;
import org.mysise.natplus.server.service.impl.TunnelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *  服務啟動器
 * <p>
 *
 * @author fanwenjie
 * @since 2020/2/27 13:14
 */
@SpringBootApplication
@MapperScan(value = "org.mysise.natplus.server.mapper")
@ComponentScan(basePackages = {"org.mysise"})
public class ServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

        ITunnelService iTunnelService = (ITunnelService) SpringUtils.getBean(TunnelServiceImpl.class);
        TcpServer tcpServer = new TcpServer(10240);
        tcpServer.bind( new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                //粘包拆包
                nioSocketChannel.pipeline().addLast(new Spliter());
                //解码
                nioSocketChannel.pipeline().addLast(new PacketDecoder());
                // 编码
                nioSocketChannel.pipeline().addLast(new PacketEncoder());
                // 处理器
                nioSocketChannel.pipeline().addLast(new ServerHandler(iTunnelService));
            }
        });

        TcpServer proxyServer = new TcpServer(80);
        proxyServer.bind(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                // 处理器
                nioSocketChannel.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), new ProxyHandler(iTunnelService));
            }
        });
    }
}
