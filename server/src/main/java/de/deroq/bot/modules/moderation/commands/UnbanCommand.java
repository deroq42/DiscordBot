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

public class UnbanCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public UnbanCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (channel.getIdLong() != discordBot.getFileManager().getChannelsConfig().getModerationChannelId()) {
            return;
        }

        if (args.length != 2) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "unban <id>**");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        ModerationModule module = (ModerationModule) discordBot.getModuleManager().getModule(ModerationModule.class);
        long id;

        try {
            id = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib eine valide Zahl an");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        discordBot.getBot().getJda().retrieveUserById(id).queue(
                //ON SUCCESS
                (user -> {
                    guild.retrieveBanList().queue(bans -> {
                        if(bans.stream().noneMatch(ban -> ban.getUser().getIdLong() == id)) {
                            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Dieser Nutzer ist nicht gebannt.");
                            channel.sendMessageEmbeds(messageEmbed).queue();
                            return;
                        }

                        module.unbanUser(guild, user);
                        String timestamp = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());

                        MessageEmbed messageEmbed = new EmbedBuilder()
                                .setColor(Color.GREEN)
                                .setTitle("Ein Mitglied wurde entbannt")
                                .addField("Name:", user.getName() + "#" + user.getDiscriminator() + " (" + user.getIdLong() + ")", true)
                                .addField("Timestamp:", timestamp, true)
                                .addField("Von:", member.getUser().getName() + "#" + member.getUser().getDiscriminator() + " (" + member.getIdLong() + ")", true)
                                .build();

                        channel.sendMessageEmbeds(messageEmbed).queue();
                    });
                }),
                //ON FAILURE
                (failure -> {
                    MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Dieser Nutzer konnte nicht gefunden werden");
                    channel.sendMessageEmbeds(messageEmbed).queue();
                }));
    }
}
