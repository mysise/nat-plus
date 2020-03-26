package org.mysise.natplus.client;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.cli.*;
import org.mysise.natplus.client.handler.ClientHandler;
import org.mysise.natplus.client.net.TcpConnection;
import org.mysise.natplus.common.codec.PacketDecoder;
import org.mysise.natplus.common.codec.PacketEncoder;
import org.mysise.natplus.common.codec.Spliter;

/**
 * <p>
 *  客户端启动器
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/2 0:36
 */
public class ClientApplication {

    public static void main(String[] args) throws ParseException, InterruptedException {
        // args
        Options options = new Options();
        options.addOption("help", false, "Help");
        options.addOption("server_addr", true, "nat-plus server address");
        options.addOption("server_port", true, "nat-plus server port");
        options.addOption("token", true, "nat-plus server password");
        options.addOption("proxy_addr", true, "Proxy server address");
        options.addOption("proxy_port", true, "Proxy server port");
//        options.addOption("remote_port", true, "Proxy server remote port");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("help")) {
            // print help
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("options", options);
        } else {

            String serverAddress = cmd.getOptionValue("server_addr");
            if (serverAddress == null) {
                System.out.println("server_addr cannot be null");
                return;
            }
            String serverPort = cmd.getOptionValue("server_port");
            if (serverPort == null) {
                System.out.println("server_port cannot be null");
                return;
            }
            String token = cmd.getOptionValue("token");
            if (token == null) {
                System.out.println("token cannot be null");
                return;
            }
            String proxyAddress = cmd.getOptionValue("proxy_addr");
            if (proxyAddress == null) {
                System.out.println("proxy_addr cannot be null");
                return;
            }
            String proxyPort = cmd.getOptionValue("proxy_port");
            if (proxyPort == null) {
                System.out.println("proxy_port cannot be null");
                return;
            }
//            String remotePort = cmd.getOptionValue("remote_port");
//            if (remotePort == null) {
//                System.out.println("remote_port cannot be null");
//                return;
//            }

            TcpConnection connection = new TcpConnection();
            connection.connect(serverAddress, Integer.parseInt(serverPort), new ChannelInitializer<NioSocketChannel>() {

                @Override
                public void initChannel(NioSocketChannel nioSocketChannel) {
                    ClientHandler clientHandler = new ClientHandler(token,
                            proxyAddress, Integer.parseInt(proxyPort));
                    //粘包拆包
                    nioSocketChannel.pipeline().addLast(new Spliter());
                    // 解码
                    nioSocketChannel.pipeline().addLast(new PacketDecoder());
                    //编码
                    nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    // 心跳
                    nioSocketChannel.pipeline().addLast(new IdleStateHandler(60,
                            30, 0));
                    //处理器
                    nioSocketChannel.pipeline().addLast(clientHandler);

                }
            });
        }
    }
}
