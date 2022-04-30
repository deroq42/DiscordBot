package de.deroq.protocol.handlers;

import de.deroq.protocol.api.ProtocolPacketListener;
import de.deroq.protocol.packets.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketChannelHandler extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;
    private final ProtocolPacketListener PACKET_LISTENER;

    public PacketChannelHandler(ProtocolPacketListener packetListener) {
        this.PACKET_LISTENER = packetListener;
    }

    /**
     * Triggers when the channel gets activated.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.channel = ctx.channel();
    }

    /**
     * Sends a packet.
     *
     * @param packet the packet to send.
     */
    public void send(Packet packet) {
        channel.writeAndFlush(packet);
    }

    /**
     * Triggers when a packet arrives.
     *
     * @param channelHandlerContext unused.
     * @param packet the incoming packet.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) {
        PACKET_LISTENER.onPacketReceive(packet);
    }
}
