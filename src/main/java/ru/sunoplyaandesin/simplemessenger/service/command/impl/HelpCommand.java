package ru.sunoplyaandesin.simplemessenger.service.command.impl;

import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

public class HelpCommand implements Command {

    private final YBotService yBotService;

    public static final String HELP_MESSAGE = "Commands: " +
            "//help - print all commands, " +
            "//channelinfo {channel title} - print last five video links, " +
            "//randomcomment {channel title}||{video title} - print random comment.";

    public HelpCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public String execute(String command) {
        yBotService.help(HELP_MESSAGE);
        return HELP_MESSAGE;
    }
}