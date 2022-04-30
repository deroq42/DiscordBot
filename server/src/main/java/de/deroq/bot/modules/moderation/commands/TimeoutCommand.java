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
import java.util.List;
import java.util.Optional;

public class TimeoutCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public TimeoutCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (channel.getIdLong() != discordBot.getFileManager().getChannelsConfig().getModerationChannelId()) {
            return;
        }

        if (args.length != 3) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "timeout <member> <minutes>**");
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
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Du kannst dich nicht selber timeouten");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (target.isOwner()) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Du kannst den Owner dieses Servers nicht in den Timeout versetzen");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        ModerationModule module = (ModerationModule) discordBot.getModuleManager().getModule(ModerationModule.class);

        if (module.isTimeOuted(target)) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Dieses Mitglied befindet sich bereits im Timeout");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        int minutes;
        try {
            minutes = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib eine valide Zahl an");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }


        channel.deleteMessageById(channel.getLatestMessageIdLong()).queue();
        module.setTimeout(target, minutes);
        String timestamp = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());

        MessageEmbed messageEmbed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Ein Mitglied wurde in den Timeout versetzt")
                .addField("Name:", target.getEffectiveName() + "#" + target.getUser().getDiscriminator() + " (" + target.getIdLong() + ")", true)
                .addField("Timestamp:", timestamp, true)
                .addField("Dauer:", minutes + " Minuten", true)
                .addField("Von:", member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + " (" + member.getIdLong() + ")", true)
                .build();

        channel.sendMessageEmbeds(messageEmbed).queue();
    }
}