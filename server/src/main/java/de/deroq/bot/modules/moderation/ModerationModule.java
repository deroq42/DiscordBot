package de.deroq.bot.modules.moderation;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.modules.Module;
import de.deroq.bot.modules.moderation.commands.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.TimeUnit;

public class ModerationModule extends Module {

    public ModerationModule(DiscordBot discordBot) {
        super(discordBot, "BanModule",
                new BanCommand("ban", discordBot),
                new UnbanCommand("unban", discordBot),
                new TimeoutCommand("timeout", discordBot),
                new UntimeoutCommand("untimeout", discordBot),
                new KickCommand("kick", discordBot)
        );
    }

    public void banUser(Guild guild, Member member, String reason) {
        guild.ban(member, 0, reason).queue();
    }

    public void unbanUser(Guild guild, User user) {
        guild.unban(user).queue();
    }

    public boolean isBanned(Guild guild, long id) {
        return guild.retrieveBanList().stream().anyMatch(ban -> ban.getUser().getIdLong() == id);
    }

    public void setTimeout(Member member, int minutes) {
        member.timeoutFor(minutes, TimeUnit.MINUTES).queue();
    }

    public void removeTimeout(Member member) {
        member.removeTimeout().queue();
    }

    public boolean isTimeOuted(Member member) {
        return member.isTimedOut();
    }

    public void kickMember(Member member, String reason) {
        member.kick(reason).queue();
    }
}
