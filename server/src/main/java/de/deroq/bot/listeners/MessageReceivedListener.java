package de.deroq.bot.listeners;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.utils.Constants;
import de.deroq.bot.utils.EmbedUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Optional;

public class MessageReceivedListener extends ListenerAdapter {

    private final DiscordBot discordBot;

    public MessageReceivedListener(DiscordBot discordBot) {
        this.discordBot = discordBot;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String messageAsString = event.getMessage().getContentStripped();

        if(!messageAsString.startsWith(Constants.COMMAND_PREFIX)) {
            return;
        }

        String[] args = messageAsString.split(" ");
        String command = args[0].replace(Constants.COMMAND_PREFIX, "");

        if(!discordBot.getCommandManager().existsModuleCommand(command)) {
            MessageEmbed messageEmbed = EmbedUtils.getErrorEmbed("Befehl konnte nicht gefunden werden");
            event.getChannel().sendMessageEmbeds(messageEmbed).queue();
            return;
        }

        discordBot.getCommandManager().getModuleCommand(command).executeCommand(
                event.getGuild(),
                event.getMember(),
                event.getChannel(),
                Optional.of(message.getMentionedMembers(event.getGuild()).get(0)),
                args);
    }
}
