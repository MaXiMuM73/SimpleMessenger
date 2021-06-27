package ru.sunoplyaandesin.simplemessenger.service.command.impl.user;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class UserBanCommand implements Command {

    public static final String USER_BAN_MESSAGE = "User ban command. Bans user.";

    private final YBotService yBotService;

    public UserBanCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        String userNameToBan;
        long banTime = 0;
        userNameToBan = command.split(" ")[1].trim();
        if (command.contains(" -m ")) {
            banTime = Long.parseLong(command.split("-m")[1].trim());
        }
        return yBotService.banUser(userId, userNameToBan, banTime);
    }
}