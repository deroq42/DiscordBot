package de.deroq.bot.modules.fun;

import de.deroq.bot.DiscordBot;
import de.deroq.bot.modules.Module;
import de.deroq.bot.modules.fun.commands.AvatarCommand;
import de.deroq.bot.modules.fun.commands.ResizeCommand;
import net.dv8tion.jda.api.entities.Member;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class FunModule extends Module {

    public FunModule(DiscordBot discordBot) {
        super(discordBot, "FunModule",
                new AvatarCommand("avatar", discordBot),
                new ResizeCommand("resize", discordBot));
    }

    public Optional<InputStream> getAvatarAsInputStream(Member member) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(member.getEffectiveAvatarUrl()).openConnection();
            httpURLConnection.setRequestMethod("GET");
            return Optional.of(httpURLConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<InputStream> getResizedAvatarAsInputStream(Member member, int width, int height) {
        try {
            BufferedImage avatar = ImageIO.read(new URL(member.getEffectiveAvatarUrl()));
            BufferedImage resizedAvatar = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resizedAvatar.createGraphics();
            graphics2D.drawImage(avatar, 0, 0, width, height, null);
            graphics2D.dispose();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedAvatar, "png", byteArrayOutputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            return Optional.of(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
