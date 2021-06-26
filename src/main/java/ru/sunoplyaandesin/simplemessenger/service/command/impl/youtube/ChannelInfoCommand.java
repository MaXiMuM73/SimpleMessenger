package ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class ChannelInfoCommand implements Command {

    private final YBotService yBotService;

    public static final String CHANNEL_INFO_MESSAGE = "Channel info command. Returns last five uploaded videos links.";

    public ChannelInfoCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String channelTitle, long userId) {
        return yBotService.channelInfo(channelTitle);
    }
}