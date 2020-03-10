package org.mysise.natplus.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mysise.natplus.client.net.TcpConnection;
import org.mysise.natplus.common.protocol.ConnectPacket;
import org.mysise.natplus.common.protocol.DataPacket;
import org.mysise.natplus.common.protocol.RegisterPacket;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  客户端连接器
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 0:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientHandler extends ChannelInboundHandlerAdapter {

    protected ChannelHandlerContext ctx;

    private ConcurrentHashMap<String, Channel> channelHandlerMap = new ConcurrentHashMap<>();

    /**
     *  令牌
     */
    private String token;

    /**
     *  被代理的本地服务器ip地址
     */
    private String proxyAddress;

    /**
     *  被代理程序端口
     */
    private int proxyPort;

    public ClientHandler(String token, String proxyAddress, int proxyPort) {
        this.token = token;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
    }

    /**
     * <p>
     *  注册消息到服务端
     * <p>
     *
     * @author fanwenjie
     * @since 2020/3/2 1:00
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        RegisterPacket registerPacket = new RegisterPacket();
        registerPacket.setToken(token);
        ctx.writeAndFlush(registerPacket);
        this.ctx = ctx;
    }

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
        if (msg instanceof RegisterPacket){
            RegisterPacket registerPacket = (RegisterPacket) msg;
            System.out.println(registerPacket.getMessage());
            if (!registerPacket.getSuccess()){
                ctx.close();
            }
        }else if (msg instanceof ConnectPacket){
            ConnectPacket connectPacket = (ConnectPacket) msg;
            TcpConnection connection = new TcpConnection();
            try{
                ClientHandler clientHandler = this;
                connection.connect(proxyAddress, proxyPort, new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel){
                        LocalProxyHandler localProxyHandler = new LocalProxyHandler(clientHandler, connectPacket.getChannelId());
                        channel.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(),
                                localProxyHandler);
                        channelHandlerMap.put(connectPacket.getChannelId(),channel);
                    }
                });
            }catch (Exception e){
                System.out.println("本地代理服务器连接失败");
                channelHandlerMap.remove(connectPacket.getChannelId());
            }
        }if (msg instanceof DataPacket){
            DataPacket dataPacket = (DataPacket) msg;
            Channel channel = channelHandlerMap.get(dataPacket.getChannelId());
            channel.writeAndFlush(dataPacket.getBytes());
            System.out.println(JSON.toJSONString(dataPacket));
        }
    }
}
