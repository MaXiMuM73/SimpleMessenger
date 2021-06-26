package ru.sunoplyaandesin.simplemessenger.service.command.impl.user;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class UserRenameCommand implements Command {

    public static final String USER_RENAME_MESSAGE = "User rename command. Renames user.";

    private final YBotService yBotService;

    public UserRenameCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        int endOfUserToRename = command.indexOf("|");
        String userToRename = command.substring(0, endOfUserToRename);

        int beginOfNewUserName = command.lastIndexOf("|");
        String newUserName = command
                .substring(beginOfNewUserName + 1);
        return yBotService.renameUser(userId, userToRename, newUserName);
    }
}
