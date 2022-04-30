package de.deroq.verify.listeners.protocol;

import de.deroq.protocol.api.ProtocolPacketListener;
import de.deroq.protocol.packets.Packet;
import de.deroq.protocol.packets.verify.MemberVerifyProcessPacket;
import de.deroq.verify.VerifyPlugin;

public class PacketListener implements ProtocolPacketListener {

    private final VerifyPlugin verifyPlugin;

    public PacketListener(VerifyPlugin verifyPlugin) {
        this.verifyPlugin = verifyPlugin;
    }

    @Override
    public void onPacketReceive(Packet packet) {
        if(packet instanceof MemberVerifyProcessPacket) {
            MemberVerifyProcessPacket memberVerifyProcessPacket = (MemberVerifyProcessPacket) packet;
            verifyPlugin.getVerifyCodes().put(memberVerifyProcessPacket.getMemberId(), memberVerifyProcessPacket.getVerifyCode());
        }
    }
}
