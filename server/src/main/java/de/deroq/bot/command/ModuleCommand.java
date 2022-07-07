package de.deroq.bot.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.List;
import java.util.Optional;

public abstract class ModuleCommand {

    private final String name;

    public ModuleCommand(String name) {
        this.name = name;
    }

    /**
     * Executes a module command.
     *
     * @param guild Guild of the bot, guild is like a  synonym for server.
     * @param member The member who typed the command.
     * @param channel The channel where the command got typed.
     * @param mentionedMember An optional member who got mentioned in the command.
     * @param args The arguments of the typed command.
     */
    public abstract void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args);

    /**
     * @return the command name.
     */
    public String getName() {
        return name;
    }
}
