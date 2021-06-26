package ru.sunoplyaandesin.simplemessenger.service.command.impl.room;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RoomConnectCommand implements Command {

    public static final String ROOM_CONNECT_MESSAGE = "Room connect command. Connects user to room.";

    private final YBotService yBotService;

    public RoomConnectCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        String userNameToConnect;
        String roomTitle;
        if (command.contains(" -l ")) {
            userNameToConnect = command.split("-l")[1].trim();
            roomTitle = command.split("-l")[0].trim();
        } else {
            userNameToConnect = "";
            roomTitle = command;
        }
        return yBotService.connectToRoom(userId, roomTitle, userNameToConnect);
    }
}
