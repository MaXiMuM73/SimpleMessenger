package ru.sunoplyaandesin.simplemessenger.service.command;

public enum CommandName {

    HELP("//help"),
    CHANNEL_INFO("//channelinfo"),
    RANDOM_COMMENT("//randomcomment");

    public final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}