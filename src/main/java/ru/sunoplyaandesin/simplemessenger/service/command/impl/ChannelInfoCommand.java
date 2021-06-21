package ru.sunoplyaandesin.simplemessenger.service.command.impl;

import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

public class ChannelInfoCommand implements Command {

    private final YBotService yBotService;

    public static final String CHANNEL_INFO_MESSAGE = "Channel info command. Returns last five uploaded videos links.";

    public ChannelInfoCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public String execute(String channelTitle) {
        yBotService.channelInfo(channelTitle);
        return CHANNEL_INFO_MESSAGE;
    }
}