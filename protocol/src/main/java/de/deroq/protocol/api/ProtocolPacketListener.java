package de.deroq.protocol.api;

import de.deroq.protocol.packets.Packet;

public interface ProtocolPacketListener {

    /**
     * Listens to incoming Packets.
     *
     * @param packet the incoming packet.
     */
    void onPacketReceive(Packet packet);
}
