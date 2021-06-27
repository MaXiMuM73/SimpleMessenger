package ru.sunoplyaandesin.simplemessenger.service.command;

import com.google.common.collect.ImmutableMap;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.*;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.room.*;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.user.UserBanCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.user.UserModeratorCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.user.UserRenameCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube.ChannelInfoCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube.HelpCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube.RandomCommentCommand;
import ru.sunoplyaandesin.simplemessenger.service.command.impl.youtube.VideoLinkLikeViewCommand;

import static ru.sunoplyaandesin.simplemessenger.service.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(YBotService yBotService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(HELP.getCommandName(), new HelpCommand(yBotService))
                .put(CHANNEL_INFO.getCommandName(), new ChannelInfoCommand(yBotService))
                .put(RANDOM_COMMENT.getCommandName(), new RandomCommentCommand(yBotService))
                .put(FIND.getCommandName(), new VideoLinkLikeViewCommand(yBotService))
                .put(ROOM_CREATE.getCommandName(), new RoomCreateCommand(yBotService))
                .put(ROOM_DELETE.getCommandName(), new RoomDeleteCommand(yBotService))
                .put(ROOM_RENAME.getCommandName(), new RoomRenameCommand(yBotService))
                .put(ROOM_CONNECT.getCommandName(), new RoomConnectCommand(yBotService))
                .put(ROOM_DISCONNECT.getCommandName(), new RoomDisconnectCommand(yBotService))
                .put(USER_RENAME.getCommandName(), new UserRenameCommand(yBotService))
                .put(USER_MODERATOR.getCommandName(), new UserModeratorCommand(yBotService))
                .put(USER_BAN.getCommandName(), new UserBanCommand(yBotService))
                .build();

        this.unknownCommand = new UnknownCommand(yBotService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}