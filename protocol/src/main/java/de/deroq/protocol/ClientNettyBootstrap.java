package de.deroq.protocol;

import de.deroq.protocol.api.ProtocolPacketListener;

import de.deroq.protocol.handlers.PacketChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ClientNettyBootstrap {

    private final String HOST;
    private final int PORT;
    public final ProtocolPacketListener PACKET_LISTENER;
    public final PacketChannelHandler PACKET_CHANNEL_HANDLER;

    public ClientNettyBootstrap(String host, int port, ProtocolPacketListener packetListener) {
        this.HOST = host;
        this.PORT = port;
        this.PACKET_LISTENER = packetListener;
        this.PACKET_CHANNEL_HANDLER = new PacketChannelHandler(packetListener);
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(4, true));
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
                            socketChannel.pipeline().addLast(PACKET_CHANNEL_HANDLER);
                        }
                    });

            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
