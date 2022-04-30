package de.deroq.bot.modules;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.command.ModuleCommand;

public abstract class Module {

    public final DiscordBot discordBot;
    private final String name;
    private final ModuleCommand[] commands;

    public Module(DiscordBot discordBot, String name, ModuleCommand... commands) {
        this.discordBot = discordBot;
        this.name = name;
        this.commands = commands;
    }

    public DiscordBot getDiscordBot() {
        return discordBot;
    }

    public String getName() {
        return name;
    }

    public ModuleCommand[] getCommands() {
        return commands;
    }
}
