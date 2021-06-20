package ru.sunoplyaandesin.simplemessenger.service.command.impl;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RandomCommentCommand implements Command {

    private final YBotService yBotService;

    public RandomCommentCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public String execute(String channelTitleAndVideoTitle) {

        int endOfChannelTitle = channelTitleAndVideoTitle.indexOf("|");
        String channelTitle = channelTitleAndVideoTitle.substring(0, endOfChannelTitle);

        int beginOfVideoTitle = channelTitleAndVideoTitle.lastIndexOf("|");
        String videoTitle = channelTitleAndVideoTitle
                .substring(beginOfVideoTitle + 1);

        List<MessageDTO> messagesDTO = yBotService.videoCommentRandom(channelTitle, videoTitle);

        StringBuilder message = new StringBuilder();

        messagesDTO.forEach(m -> message.append(m.getText()).append(" "));

        return message.toString();
    }
}