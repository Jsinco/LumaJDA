package dev.jsinco.lumajda.api;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class CommandOption {

    private final OptionType optionType;
    private final String name;
    private final String description;
    private final boolean required;

    public CommandOption(OptionType optionType, String name, String description, boolean required) {
        this.optionType = optionType;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRequired() {
        return required;
    }
}
