package ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RandomCommentCommand implements Command {

    private final YBotService yBotService;

    public static final String RANDOM_COMMENT_MESSAGE = "Random comment command. Returns random video comment.";

    public RandomCommentCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {

        int endOfChannelTitle = command.indexOf("|");
        String channelTitle = command.substring(0, endOfChannelTitle);

        int beginOfVideoTitle = command.lastIndexOf("|");
        String videoTitle = command
                .substring(beginOfVideoTitle + 1);

        return yBotService.videoCommentRandom(channelTitle, videoTitle);
    }
}