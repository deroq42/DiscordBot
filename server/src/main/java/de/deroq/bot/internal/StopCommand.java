package de.deroq.bot.internal;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.command.InternalCommand;

public class StopCommand extends InternalCommand {

    private final DiscordBot discordBot;

    public StopCommand(String name, DiscordBot discordBot) {
        super(name);
        this.discordBot = discordBot;
    }

    @Override
    public void executeCommand(String[] args) {
        discordBot.getBot().stop();
    }
}
