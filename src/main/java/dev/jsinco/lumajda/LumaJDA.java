package dev.jsinco.lumajda;

import dev.jsinco.lumajda.commands.DiscordCommandManager;
import dev.jsinco.lumajda.listeners.DiscordListeners;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class LumaJDA extends JavaPlugin implements CommandExecutor {

    private static LumaJDA instance;
    private static JDA jda;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        loadJDA();
    }

    @Override
    public void onDisable() {
        shutdownJDA();
    }


    public static LumaJDA plugin() {
        return instance;
    }

    public static JDA jda() {
        return jda;
    }

    public void loadJDA() {
        String botToken = getConfig().getString("bot-token");
        assert botToken != null;
        if (botToken.equals("bot-token-here")) {
            getLogger().severe("You must set your bot token in config.yml!");
            return;
        }
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

    public void shutdownJDA() {
        if (jda != null) {
            jda.shutdownNow();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        reloadConfig();
        shutdownJDA();
        loadJDA();
        sender.sendMessage("Reloaded JDA!");
        return true;
    }
}
