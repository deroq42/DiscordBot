package de.deroq.verify.commands;

import de.deroq.protocol.packets.verify.MemberVerifyPacket;
import de.deroq.verify.VerifyPlugin;
import de.deroq.verify.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

public class VerifyCommand extends Command {

    private final VerifyPlugin verifyPlugin;

    public VerifyCommand(String name, VerifyPlugin verifyPlugin) {
        super(name);
        this.verifyPlugin = verifyPlugin;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length != 1) {
            player.sendMessage(Constants.PREFIX + "Bitte nutze den Befehl /verify <code>");
            return true;
        }

        int code;
        try {
            code = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(Constants.PREFIX + "Bitte gib eine valide Zahl an");
            return true;
        }

        if (verifyPlugin.getVerifyCodes().values().stream().noneMatch(verifyCode -> verifyCode == code)) {
            player.sendMessage(Constants.PREFIX + "Code konnte nicht gefunden werden");
            return true;
        }

        long memberId = 0;
        //Goes through all entries and checks if the key is equal to the typed code.
        Optional<Map.Entry<Long, Integer>> entry = verifyPlugin.getVerifyCodes().entrySet()
                .stream()
                .filter(longIntegerEntry -> verifyPlugin.getVerifyCodes().get(longIntegerEntry.getKey()) == code)
                .findFirst();

        if(!entry.isPresent()) {
            player.sendMessage(Constants.PREFIX + "Diesen Code gibt es nicht");
            return true;
        }


        memberId = entry.get().getKey();
        verifyPlugin.getVerifyCodes().remove(memberId);
        verifyPlugin.getClientNettyBootstrap().PACKET_CHANNEL_HANDLER.send(new MemberVerifyPacket(memberId, player.getName()));
        player.sendMessage(Constants.PREFIX + "Du bist nun verifiziert");
        return false;
    }
}
