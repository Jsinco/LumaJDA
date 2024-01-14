package dev.jsinco.lumajda.util;

import github.scarsz.discordsrv.DiscordSRV;

import java.util.UUID;

public class DiscordSRVUtil {

    public static String getDiscordIDFromUUID(UUID uuid) {
        return DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(uuid);
    }

    public static String getDiscordIDFromUUID(String uuid) {
        return DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(UUID.fromString(uuid));
    }

    public static UUID getUUIDFromDiscordID(String discordID) {
        return DiscordSRV.getPlugin().getAccountLinkManager().getUuid(discordID);
    }

    public static String getUUIDFromDiscordIDAsString(String discordID) {
        return DiscordSRV.getPlugin().getAccountLinkManager().getUuid(discordID).toString();
    }
}
