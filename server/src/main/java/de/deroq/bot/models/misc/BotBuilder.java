package de.deroq.bot.models.misc;

import de.deroq.bot.models.Bot;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Arrays;

public class BotBuilder {

    private Bot bot;

    public BotBuilder() {
        this.bot = new Bot();
    }

    public BotBuilder setToken(String token) {
        bot.setToken(token);
        return this;
    }

    public BotBuilder setActivity(Activity activity) {
        bot.setActivity(activity);
        return this;
    }

    public BotBuilder setStatus(OnlineStatus onlineStatus) {
        bot.setOnlineStatus(onlineStatus);
        return this;
    }

    public BotBuilder addListeners(EventListener... listeners) {
        bot.setListeners(Arrays.asList(listeners));
        return this;
    }

    public BotBuilder setMemberCachePolicy(MemberCachePolicy memberCachePolicy) {
        bot.setMemberCachePolicy(memberCachePolicy);
        return this;
    }

    public BotBuilder setCacheFlags(CacheFlag... cacheFlags) {
        bot.setCacheFlags(Arrays.asList(cacheFlags));
        return this;
    }

    public BotBuilder setAutoReconnect(boolean autoReconnect) {
        bot.setAutoReconnect(autoReconnect);
        return this;
    }

    public Bot build() {
        return bot;
    }
}
