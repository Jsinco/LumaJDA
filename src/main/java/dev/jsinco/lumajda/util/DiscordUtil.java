package dev.jsinco.lumajda.util;

import dev.jsinco.lumajda.LumaJDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordUtil {

    public static void sendMessageDiscordUser(String userID, String msg) {
        LumaJDA.jda().retrieveUserById(userID).queue(user -> {
            user.openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(msg))
                    .queue();
        });
    }

    public static void sendMessageDiscordChannel(String channelID, String msg, boolean silent) {
        TextChannel channel = LumaJDA.jda().getTextChannelById(channelID);
        if (channel != null) {
            channel.sendMessage(msg).setSuppressedNotifications(silent).queue();
        }
    }
}
