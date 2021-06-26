package ru.sunoplyaandesin.simplemessenger.service.command.impl;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class UnknownCommand implements Command {

    public static final String UNKNOWN_MESSAGE = "Unknown command. Enter //help to get all commands.";

    private final YBotService yBotService;

    public UnknownCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        return yBotService.unknownCommand();
    }
}