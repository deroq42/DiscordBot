package de.deroq.bot;

import de.deroq.bot.command.CommandManager;
import de.deroq.bot.config.FileManager;
import de.deroq.bot.internal.StopCommand;
import de.deroq.bot.listeners.MessageReceivedListener;
import de.deroq.bot.listeners.protocol.PacketListener;
import de.deroq.bot.modules.ModuleManager;
import de.deroq.bot.models.Bot;
import de.deroq.bot.models.misc.BotBuilder;
import de.deroq.bot.modules.fun.FunModule;
import de.deroq.bot.modules.moderation.ModerationModule;
import de.deroq.bot.modules.verify.VerifyModule;
import de.deroq.protocol.ServerNettyBootstrap;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.Arrays;

public class DiscordBot {

    private FileManager fileManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private Bot bot;
    private PacketListener packetListener;
    private ServerNettyBootstrap serverNettyBootstrap;

    public static void main(String[] args) {
        DiscordBot discordBot = new DiscordBot();

        discordBot.initBot();
        discordBot.initManagers();
        discordBot.initNetty();
        discordBot.registerModules();
        discordBot.registerInternalCommands();
        discordBot.registerModuleCommands();
        discordBot.getCommandManager().startReadingInternalCommands();
    }

    private void initBot() {
        this.bot = new BotBuilder()
                .setToken("SECRET")
                .setActivity(Activity.playing("Minecraft"))
                .setStatus(OnlineStatus.ONLINE)
                .addListeners(new MessageReceivedListener(this))
                .setMemberCachePolicy(MemberCachePolicy.NONE)
                .setCacheFlags()
                .setAutoReconnect(true)
                .build();

        bot.run();
    }

    private void initManagers() {
        this.fileManager = new FileManager();
        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        this.packetListener = new PacketListener(this);
        this.serverNettyBootstrap = new ServerNettyBootstrap(8000, packetListener);
    }

    private void initNetty() {
        new Thread(() -> serverNettyBootstrap.run()).start();
    }

    private void registerModules() {
        moduleManager.registerModule(new ModerationModule(this));
        moduleManager.registerModule(new FunModule(this));
        moduleManager.registerModule(new VerifyModule(this));
    }

    private void registerInternalCommands() {
        commandManager.registerCommand("stop", new StopCommand("stop", this));
    }

    private void registerModuleCommands() {
        moduleManager.getModules().forEach(module -> Arrays.stream(module.getCommands()).forEach(command -> commandManager.registerCommand(command.getName(), command)));
    }

    public Bot getBot() {
        return bot;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public PacketListener getPacketListener() {
        return packetListener;
    }

    public ServerNettyBootstrap getServerNettyBootstrap() {
        return serverNettyBootstrap;
    }
}
