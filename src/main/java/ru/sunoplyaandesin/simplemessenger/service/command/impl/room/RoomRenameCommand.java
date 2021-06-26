package ru.sunoplyaandesin.simplemessenger.service.command.impl.room;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RoomRenameCommand implements Command {

    public static final String ROOM_RENAME_MESSAGE = "Room rename command. Renames room for user.";

    private final YBotService yBotService;

    public RoomRenameCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        int endOfRoomTitle = command.indexOf("|");
        String roomTitle = command.substring(0, endOfRoomTitle);

        int beginOfNewRoomTitle = command.lastIndexOf("|");
        String newRoomTitle = command
                .substring(beginOfNewRoomTitle + 1);

        return yBotService.renameRoom(userId, roomTitle, newRoomTitle);
    }
}