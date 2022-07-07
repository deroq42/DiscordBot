package de.deroq.bot.modules.moderation.commands;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.command.ModuleCommand;
import de.deroq.bot.modules.moderation.ModerationModule;
import de.deroq.bot.utils.Constants;
import de.deroq.bot.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class KickCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public KickCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (channel.getIdLong() != discordBot.getFileManager().getChannelsConfig().getModerationChannelId()) {
            return;
        }

        if (args.length < 3) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "kick <member> <reason>**");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (!mentionedMember.isPresent()) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib ein Mitglied im Format `@name` an. Beispiel: @deroq");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        Member target = mentionedMember.get();

        if (target.equals(member)) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Du kannst dich nicht selber kicken");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (target.isOwner()) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Du kannst den Owner dieses Servers nicht kicken");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        ModerationModule module = (ModerationModule) discordBot.getModuleManager().getModule(ModerationModule.class);

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(" ").append(args[i]);
        }

        channel.deleteMessageById(channel.getLatestMessageIdLong()).queue();
        module.kickMember(target, reason.toString());
        String timestamp = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());

        MessageEmbed messageEmbed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Ein Mitglied wurde gekickt")
                .addField("Name:", target.getEffectiveName() + "#" + target.getUser().getDiscriminator() + " (" + target.getIdLong() + ")", true)
                .addField("Timestamp:", timestamp, true)
                .addField("Grund:", reason.toString(), true)
                .addField("Von:", member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + " (" + member.getIdLong() + ")", true)
                .build();

        channel.sendMessageEmbeds(messageEmbed).queue();
    }
}
