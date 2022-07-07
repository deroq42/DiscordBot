package de.deroq.bot.models;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Bot {

    private JDA jda;
    private final String token;
    private final Activity activity;
    private final OnlineStatus onlineStatus;
    private final EventListener[] listeners;
    private final MemberCachePolicy memberCachePolicy;
    private final CacheFlag[] cacheFlags;
    private final boolean autoReconnect;

    private Bot(String token, Activity activity, OnlineStatus onlineStatus, EventListener[] listeners, MemberCachePolicy memberCachePolicy, CacheFlag[] cacheFlags, boolean autoReconnect) {
        this.token = token;
        this.activity = activity;
        this.onlineStatus = onlineStatus;
        this.listeners = listeners;
        this.memberCachePolicy = memberCachePolicy;
        this.cacheFlags = cacheFlags;
        this.autoReconnect = autoReconnect;
    }

    /**
     * Stats the bot.
     */
    public void run() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                .setActivity(activity)
                .setStatus(onlineStatus)
                .setMemberCachePolicy(memberCachePolicy)
                .enableCache(Arrays.asList(cacheFlags))
                .setAutoReconnect(autoReconnect);

        try {
            jda = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }


        Arrays.asList(listeners).forEach(eventListener -> jda.getEventManager().register(eventListener));
        System.out.println("Bot has been started.");
    }

    /**
     * Stops the bot.
     */
    public void stop() {
        if (jda != null) {
            jda.shutdown();
            System.out.println("Bot has been shutdown.");
            System.exit(0);
        }
    }

    public JDA getJda() {
        return jda;
    }

    public String getToken() {
        return token;
    }

    public Activity getActivity() {
        return activity;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public EventListener[] getListeners() {
        return listeners;
    }

    public MemberCachePolicy getMemberCachePolicy() {
        return memberCachePolicy;
    }

    public CacheFlag[] getCacheFlags() {
        return cacheFlags;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public static class builder {

        private String token;
        private Activity activity;
        private OnlineStatus onlineStatus;
        private EventListener[] listeners;
        private MemberCachePolicy memberCachePolicy;
        private CacheFlag[] cacheFlags;
        private boolean autoReconnect;

        public builder setToken(String token) {
            this.token = token;
            return this;
        }

        public builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public builder setOnlineStatus(OnlineStatus onlineStatus) {
            this.onlineStatus = onlineStatus;
            return this;
        }

        public builder setListeners(EventListener... listeners) {
            this.listeners = listeners;
            return this;
        }

        public builder setMemberCachePolicy(MemberCachePolicy memberCachePolicy) {
            this.memberCachePolicy = memberCachePolicy;
            return this;
        }

        public builder setCacheFlags(CacheFlag... cacheFlags) {
            this.cacheFlags = cacheFlags;
            return this;
        }

        public builder setAutoReconnect(boolean autoReconnect) {
            this.autoReconnect = autoReconnect;
            return this;
        }

        public Bot build() {
            return new Bot(token, activity, onlineStatus, listeners, memberCachePolicy, cacheFlags, autoReconnect);
        }
    }
}
