package de.deroq.bot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedUtils {

    public static MessageEmbed getErrorEmbed(String errorMessage) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .addField("Fehler:", errorMessage, true)
                .build();
    }
}
