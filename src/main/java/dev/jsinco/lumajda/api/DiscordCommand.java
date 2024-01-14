package dev.jsinco.lumajda.api;

import dev.jsinco.lumajda.api.CommandOption;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DiscordCommand {

    @NotNull String name();

    String description();

    void execute(@NotNull SlashCommandInteractionEvent event);

    List<CommandOption> options();

    Permission permission();

    @Nullable Plugin plugin();
}
