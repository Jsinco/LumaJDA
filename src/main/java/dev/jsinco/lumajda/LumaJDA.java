package dev.jsinco.lumajda;

import dev.jsinco.lumajda.commands.DiscordCommandManager;
import dev.jsinco.lumajda.listeners.DiscordListeners;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

public final class LumaJDA extends JavaPlugin {

    private static LumaJDA instance;
    private static JDA jda;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        String botToken = getConfig().getString("bot-token");
        jda = JDABuilder.createDefault(botToken).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).setAutoReconnect(true)
                .build();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jda.addEventListener(new DiscordCommandManager(this, jda));
        jda.addEventListener(new DiscordListeners());
    }

    @Override
    public void onDisable() {
        jda.shutdownNow();
    }


    public static LumaJDA plugin() {
        return instance;
    }

    public static JDA jda() {
        return jda;
    }
}
