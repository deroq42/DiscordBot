package de.deroq.protocol.packets.verify;

import de.deroq.protocol.packets.Packet;

public class MemberVerifyPacket extends Packet {

    private final long memberId;
    private final String name;

    public MemberVerifyPacket(long memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }
}
