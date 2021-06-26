package ru.sunoplyaandesin.simplemessenger.service.command;

public enum CommandName {

    HELP("//help"),
    CHANNEL_INFO("//channelinfo"),
    RANDOM_COMMENT("//randomcomment"),
    ROOM_CREATE("//room create"),
    ROOM_DELETE("//room delete"),
    ROOM_RENAME("//room rename"),
    ROOM_CONNECT("//room connect"),
    ROOM_DISCONNECT("//room disconnect"),
    USER_RENAME("//user rename"),
    USER_MODERATOR("//user moderator");

    public final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}