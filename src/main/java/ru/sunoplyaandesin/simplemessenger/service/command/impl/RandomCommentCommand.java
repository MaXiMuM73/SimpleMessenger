package ru.sunoplyaandesin.simplemessenger.service.command.impl;

import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

public class RandomCommentCommand implements Command {

    private final YBotService yBotService;

    public static final String RANDOM_COMMENT_MESSAGE = "Random comment command. Returns random video comment.";

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

        yBotService.videoCommentRandom(channelTitle, videoTitle);

        return RANDOM_COMMENT_MESSAGE;
    }
}