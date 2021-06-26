package ru.sunoplyaandesin.simplemessenger.service.command.impl.room;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RoomDeleteCommand implements Command {

    public static final String ROOM_DELETE_MESSAGE = "Room delete command. Deletes user room.";

    private final YBotService yBotService;

    public RoomDeleteCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        return yBotService.deleteRoom(userId, command);
    }
}