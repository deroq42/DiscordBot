package de.deroq.bot.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.List;
import java.util.Optional;

public abstract class ModuleCommand {

    private final String NAME;

    public ModuleCommand(String name) {
        this.NAME = name;
    }

    public abstract void executeCommand(Guild guild, Member member, MessageChannel channel, Optional<Member> mentionedMember, String[] args);

    public String getName() {
        return NAME;
    }
}
