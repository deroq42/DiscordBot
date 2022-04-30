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
import java.util.Collection;

public class Bot {

    private JDA jda;
    private String token;
    private Activity activity;
    private OnlineStatus onlineStatus;
    private Collection<EventListener> listeners;
    private MemberCachePolicy memberCachePolicy;
    private Collection<CacheFlag> cacheFlags;
    private boolean autoReconnect;

    public Bot() {
        this.listeners = new ArrayList<>();
        this.cacheFlags = new ArrayList<>();
    }

    public void run() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                .setActivity(activity)
                .setStatus(onlineStatus)
                .setMemberCachePolicy(memberCachePolicy)
                .enableCache(cacheFlags)
                .setAutoReconnect(autoReconnect);

        try {
            jda = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        listeners.forEach(eventListener -> jda.getEventManager().register(eventListener));
        System.out.println("Bot has been started.");
    }

    public void stop() {
        if(jda != null) {
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

    public void setToken(String token) {
        this.token = token;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Collection<EventListener> getListeners() {
        return listeners;
    }

    public void setListeners(Collection<EventListener> listeners) {
        this.listeners = listeners;
    }

    public MemberCachePolicy getMemberCachePolicy() {
        return memberCachePolicy;
    }

    public void setMemberCachePolicy(MemberCachePolicy memberCachePolicy) {
        this.memberCachePolicy = memberCachePolicy;
    }

    public Collection<CacheFlag> getCacheFlags() {
        return cacheFlags;
    }

    public void setCacheFlags(Collection<CacheFlag> cacheFlags) {
        this.cacheFlags = cacheFlags;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }
}
