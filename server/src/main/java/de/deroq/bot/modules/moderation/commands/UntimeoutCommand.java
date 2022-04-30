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

public class UntimeoutCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public UntimeoutCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (channel.getIdLong() != discordBot.getFileManager().getChannelsConfig().getModerationChannelId()) {
            return;
        }

        if (args.length != 2) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "untimeout <member>**");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (!mentionedMember.isPresent()) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib ein Mitglied im Format `@name` an. Beispiel: @deroq");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        Member target = mentionedMember.get();
        ModerationModule module = (ModerationModule) discordBot.getModuleManager().getModule(ModerationModule.class);

        if(!module.isTimeOuted(target)) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Dieses Mitglied befindet sich nicht im Timeout");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        module.removeTimeout(target);
        User user = target.getUser();
        String timestamp = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());

        MessageEmbed messageEmbed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Ein Mitglied wurde aus dem Timeout GEHOLT")
                .addField("Name:", user.getName() + "#" + user.getDiscriminator() + " (" + user.getIdLong() + ")", true)
                .addField("Timestamp:", timestamp, true)
                .addField("Von:", member.getUser().getName() + "#" + member.getUser().getDiscriminator() + " (" + member.getIdLong() + ")", true)
                .build();

        channel.sendMessageEmbeds(messageEmbed).queue();

    }
}
