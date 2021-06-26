package ru.sunoplyaandesin.simplemessenger.service.command.impl.user;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class UserModeratorCommand implements Command {

    public static final String USER_MODERATOR_MESSAGE = "User moderator command.";

    private final YBotService yBotService;

    public UserModeratorCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        String userName = command.split(" ")[0];
        String tag = command.split(" ")[1];
        return yBotService.assignRoleToUser(userId, userName, tag);
    }
}