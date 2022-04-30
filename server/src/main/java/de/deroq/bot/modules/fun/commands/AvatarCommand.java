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

public class AvatarCommand extends ModuleCommand {

    private final DiscordBot discordBot;

    public AvatarCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args) {
        if (args.length != 2) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Bitte nutze den Befehl **" + Constants.COMMAND_PREFIX + "avatar <member>**");
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

        if(!module.getAvatarAsInputStream(target).isPresent()) {
            throw new NullPointerException("Error while getting avatar of " + target.getEffectiveName() + ": Member can not be found.");
        }

        InputStream inputStream = module.getAvatarAsInputStream(target).get();
        channel.sendFile(inputStream, target.getEffectiveName() + "-avatar.png").queue();
    }
}
