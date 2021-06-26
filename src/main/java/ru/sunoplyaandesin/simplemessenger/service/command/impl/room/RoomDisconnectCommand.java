package ru.sunoplyaandesin.simplemessenger.service.command.impl.room;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RoomDisconnectCommand implements Command {

    public static final String ROOM_CONNECT_MESSAGE = "Room disconnect command. Disconnects user from room.";

    private final YBotService yBotService;

    public RoomDisconnectCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        String userNameToDisconnect;
        String roomTitle;
        long banTime = 0;
        if (command.contains(" -l ")) {
            roomTitle = command.split("-l")[0].trim();
            userNameToDisconnect = command.split("-l")[1].trim().split(" ")[0];
            if (command.contains(" -m ")) {
                banTime = Long.parseLong(command.split("-m")[1].trim());
            }
        } else {
            userNameToDisconnect = "";
            roomTitle = command;
        }
        return yBotService.disconnectFromRoom(userId, roomTitle, userNameToDisconnect, banTime);
    }
}
