package de.deroq.bot.listeners.protocol;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.modules.verify.VerifyModule;
import de.deroq.protocol.api.ProtocolPacketListener;
import de.deroq.protocol.packets.Packet;
import de.deroq.protocol.packets.verify.MemberVerifyPacket;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class PacketListener implements ProtocolPacketListener {

    private final DiscordBot discordBot;

    public PacketListener(DiscordBot discordBot) {
        this.discordBot = discordBot;
    }

    @Override
    public void onPacketReceive(Packet packet) {
        if(packet instanceof MemberVerifyPacket) {
            MemberVerifyPacket memberVerifyPacket = (MemberVerifyPacket) packet;
            Guild guild = discordBot.getBot().getJda().getGuilds().get(0);
            Member member = guild.getMemberById(memberVerifyPacket.getMemberId());

            if(member == null) {
                return;
            }

            VerifyModule module = (VerifyModule) discordBot.getModuleManager().getModule(VerifyModule.class);
            boolean verified = module.verifyMember(guild, member, memberVerifyPacket.getName());

            if(verified) {
                member.getUser().openPrivateChannel().queue(privateChannel -> {
                    MessageEmbed messageEmbed = new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Verifizierung")
                            .setDescription("Du bist nun verifiziert!")
                            .addField("Neuer Name:", memberVerifyPacket.getName(), true)
                            .build();

                    privateChannel.sendMessageEmbeds(messageEmbed).queue();
                });
            }
        }
    }
}
