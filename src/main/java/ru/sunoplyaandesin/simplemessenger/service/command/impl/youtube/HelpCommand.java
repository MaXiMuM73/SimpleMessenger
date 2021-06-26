package ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.Command;

import java.util.List;

public class HelpCommand implements Command {

    private final YBotService yBotService;

    public static final String HELP_MESSAGE = "Help command. Returns help info";

    public HelpCommand(YBotService yBotService) {
        this.yBotService = yBotService;
    }

    @Override
    public List<MessageDTO> execute(String command, long userId) {
        return yBotService.help(COMMANDS);
    }
}