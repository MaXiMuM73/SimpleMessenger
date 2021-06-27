package ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class VideoLinkLikeViewCommand implements Command {

    private final YBotService yBotService;

    public static final String VIDEO_LINK_LIKE_VIEW_MESSAGE = "Video link like view command. Returns video link, likes, views.";

    public VideoLinkLikeViewCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {

        int endOfChannelTitle = command.indexOf("|");
        String channelTitle = command.substring(0, endOfChannelTitle);

        int beginOfVideoTitle = command.lastIndexOf("|");
        String videoTitle = command
                .substring(beginOfVideoTitle + 1);

        return yBotService.getVideoLinkLikeView(channelTitle, videoTitle);
    }
}