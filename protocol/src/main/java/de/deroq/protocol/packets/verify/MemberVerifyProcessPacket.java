package de.deroq.protocol.packets.verify;

import de.deroq.protocol.packets.Packet;

public class MemberVerifyProcessPacket extends Packet {

    private final long memberId;
    private final int verifyCode;

    public MemberVerifyProcessPacket(long memberId, int verifyCode) {
        this.memberId = memberId;
        this.verifyCode = verifyCode;
    }

    public long getMemberId() {
        return memberId;
    }

    public int getVerifyCode() {
        return verifyCode;
    }
}
