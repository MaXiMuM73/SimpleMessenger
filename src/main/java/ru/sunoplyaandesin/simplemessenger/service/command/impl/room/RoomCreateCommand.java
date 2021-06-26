package ru.sunoplyaandesin.simplemessenger.service.command.impl.room;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class RoomCreateCommand implements Command {

    public static final String ROOM_CREATE_MESSAGE = "Room create command. Creates room for user.";

    private final YBotService yBotService;

    public RoomCreateCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        boolean privateRoom = command.contains("-c");
        String roomTitle = command.replaceAll("-c", "").trim();
        return yBotService.createRoom(userId, roomTitle, privateRoom);
    }
}
