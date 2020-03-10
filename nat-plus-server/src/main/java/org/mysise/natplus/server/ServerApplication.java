package org.mysise.natplus.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.mybatis.spring.annotation.MapperScan;
import org.mysise.natplus.common.codec.PacketDecoder;
import org.mysise.natplus.common.codec.PacketEncoder;
import org.mysise.natplus.common.codec.Spliter;
import org.mysise.natplus.server.core.handler.ProxyHandler;
import org.mysise.natplus.server.core.handler.ServerHandler;
import org.mysise.natplus.server.core.net.TcpServer;
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
                nioSocketChannel.pipeline().addLast(new ServerHandler());
            }
        });

        TcpServer proxyServer = new TcpServer(8888);
        proxyServer.bind(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                // 处理器
                nioSocketChannel.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), new ProxyHandler());
            }
        });
    }
}
