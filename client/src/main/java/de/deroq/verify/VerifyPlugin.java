package de.deroq.verify;

import de.deroq.protocol.ClientNettyBootstrap;
import de.deroq.verify.commands.VerifyCommand;
import de.deroq.verify.listeners.protocol.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class VerifyPlugin extends JavaPlugin {

    private Map<Long, Integer> verifyCodes;
    private ClientNettyBootstrap clientNettyBootstrap;

    @Override
    public void onEnable() {
        makeInstances();
        registerCommands();

        getLogger().info("VerifyPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("VerifyPlugin has been disabled.");
    }

    private void makeInstances() {
        this.verifyCodes = new HashMap<>();
        this.clientNettyBootstrap = new ClientNettyBootstrap("localhost", 8000, new PacketListener(this));

        new Thread(() -> clientNettyBootstrap.run()).start();
    }

    private void registerCommands() {
        ((CraftServer) Bukkit.getServer()).getCommandMap().register("verify", new VerifyCommand("verify", this));
    }

    public ClientNettyBootstrap getClientNettyBootstrap() {
        return clientNettyBootstrap;
    }

    public Map<Long, Integer> getVerifyCodes() {
        return verifyCodes;
    }
}
