package ru.sunoplyaandesin.simplemessenger.service.command;

import com.google.common.collect.ImmutableMap;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.ChannelInfoCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.HelpCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.RandomCommentCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.UnknownCommand;

import static ru.sunoplyaandesin.simplemessenger.service.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(YBotService yBotService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(HELP.getCommandName(), new HelpCommand(yBotService))
                .put(CHANNEL_INFO.getCommandName(), new ChannelInfoCommand(yBotService))
                .put(RANDOM_COMMENT.getCommandName(), new RandomCommentCommand(yBotService))
                .build();

        this.unknownCommand = new UnknownCommand();
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}