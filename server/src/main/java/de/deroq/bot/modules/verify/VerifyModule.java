package de.deroq.bot.modules.verify;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.modules.Module;
import de.deroq.bot.modules.verify.commands.VerifyCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class VerifyModule extends Module {

    public VerifyModule(DiscordBot discordBot) {
        super(discordBot, "VerifyModule",
                new VerifyCommand("verify", discordBot));
    }

    public boolean verifyMember(Guild guild, Member member, String name) {
        Long roleId = discordBot.getFileManager().getRolesConfig().getRoles().get("verified");
        if(roleId == null) {
            return false;
        }

        Role role = discordBot.getBot().getJda().getRoleById(roleId);
        if(role == null) {
            return false;
        }

        guild.addRoleToMember(member, role).queue();
        member.modifyNickname(name).queue();
        return true;
    }

    public boolean isVerified(Guild guild, Member member) {
        Long roleId = discordBot.getFileManager().getRolesConfig().getRoles().get("verified");
        if(roleId == null) {
            return false;
        }

        return member.getRoles().contains(guild.getRoleById(roleId));
    }
}
