package de.deroq.bot.models.configs;

import de.deroq.bot.models.Config;

import java.io.File;

public class ChannelsConfig extends Config {

    private final long moderationChannelId;
    private final long verifyChannelId;

    private ChannelsConfig(File file, long moderationChannelId, long verifyChannelId) {
        super(file.getName());
        this.moderationChannelId = moderationChannelId;
        this.verifyChannelId = verifyChannelId;
    }

    public long getModerationChannelId() {
        return moderationChannelId;
    }

    public long getVerifyChannelId() {
        return verifyChannelId;
    }

    public static ChannelsConfig create(File file, long moderationChannelId, long verifyChannelId) {
        return new ChannelsConfig(file, moderationChannelId, verifyChannelId);
    }
}
