package dev.jsinco.lumajda.commands;

import dev.jsinco.lumajda.LumaJDA;
import dev.jsinco.lumajda.util.Util;
import dev.jsinco.lumajda.api.CommandOption;
import dev.jsinco.lumajda.api.DiscordCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DiscordCommandManager extends ListenerAdapter {

    private final LumaJDA plugin;
    private final JDA jda;

    public DiscordCommandManager(LumaJDA plugin, JDA jda) {
        this.plugin = plugin;
        this.jda = jda;
    }

    private final static Map<String, DiscordCommand> commands = new HashMap<>();


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (commands.containsKey(event.getName())) {
            DiscordCommand command = commands.get(event.getName());
            if (command.permission() != null && !event.getMember().hasPermission(command.permission())) {
                event.reply("You do not have permission to use this command!").setEphemeral(true).queue();
                return;
            }

            command.execute(event);
        }
    }

    public void registerCommand(DiscordCommand command, boolean global) {
        if (commands.containsKey(command.name())) {
            Util.log("Re-registering command " + command.name());
        }

        commands.put(command.name(), command);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            String where = global ? "globally" : "in guilds";
            if (!global) {
                for (Guild guild : jda.getGuilds()) {
                    CommandCreateAction cmd = guild.upsertCommand(command.name(), command.description());
                    for (CommandOption option : command.options()) {
                        cmd.addOption(option.getOptionType(), option.getName(), option.getDescription(), option.isRequired());
                    }
                    cmd.queue();
                }

            } else {
                CommandCreateAction cmd = jda.upsertCommand(command.name(), command.description());
                for (CommandOption option : command.options()) {
                    cmd.addOption(option.getOptionType(), option.getName(), option.getDescription(), option.isRequired());
                }
                cmd.queue();
            }

            Util.log("&aRegistered command &9" + command.name() + " &ain " + where);
        }, 100L);
    }
}
