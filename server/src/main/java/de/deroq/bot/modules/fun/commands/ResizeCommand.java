package de.deroq.bot.modules.fun.commands;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.command.ModuleCommand;
import de.deroq.bot.modules.fun.FunModule;
import de.deroq.bot.utils.Constants;
import de.deroq.bot.utils.EmbedUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.io.InputStream;
import java.util.Optional;

public class ResizeCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public ResizeCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (args.length != 4) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "resize <member> <width> <height>**");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (!mentionedMember.isPresent()) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib ein Mitglied im Format `@name` an. Beispiel: @deroq");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        Member target = mentionedMember.get();
        FunModule module = (FunModule) discordBot.getModuleManager().getModule(FunModule.class);

        int width;
        int height;

        try {
            width = Integer.parseInt(args[2]);
            height = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib eine valide Zahl an");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (width < 0 || width > 1000 || height < 0 || height > 1000) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte gib eine Zahl zwischen 0 und 1000 an");
            channel.sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        if (!module.getResizedAvatarAsInputStream(target, width, height).isPresent()) {
            throw new NullPointerException("Error while getting resized avatar of member " + target.getEffectiveName() + ": Member can not be found.");
        }

        InputStream inputStream = module.getResizedAvatarAsInputStream(target, width, height).get();
        channel.sendFile(inputStream, member.getEffectiveName() + "-avatar.png").queue();
    }
}
