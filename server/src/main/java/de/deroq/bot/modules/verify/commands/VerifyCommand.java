package de.deroq.bot.modules.verify.commands;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.command.ModuleCommand;
import de.deroq.bot.modules.verify.VerifyModule;
import de.deroq.bot.utils.Constants;
import de.deroq.bot.utils.EmbedUtils;
import de.deroq.protocol.packets.verify.MemberVerifyProcessPacket;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Optional;
import java.util.Random;

public class VerifyCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public VerifyCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (channel.getIdLong() != discordBot.getFileManager().getChannelsConfig().getVerifyChannelId()) {
            return;
        }

        if (args.length != 1) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "verify**");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        VerifyModule module = (VerifyModule) discordBot.getModuleManager().getModule(VerifyModule.class);

        if (module.isVerified(guild, member)) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Du bist bereits verifiziert");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        int verifyCode = new Random().nextInt(99999) + 10000;

        member.getUser().openPrivateChannel().queue(privateChannel -> {
            MessageEmbed messageEmbed = new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Verifizierung")
                    .setDescription("Du hast es fast geschafft! Um dich zu verifizieren, führe folgenden Befehl in der Lobby aus:")
                    .addField("Befehl:", "/verify " + verifyCode, true)
                    .build();

            privateChannel.sendMessageEmbeds(messageEmbed).queue();
        });

        discordBot.getServerNettyBootstrap().PACKET_CHANNEL_HANDLER.send(new MemberVerifyProcessPacket(member.getIdLong(), verifyCode));
    }
}
