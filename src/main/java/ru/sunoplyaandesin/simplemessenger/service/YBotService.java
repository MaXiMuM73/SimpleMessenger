package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;

import java.util.List;

public interface YBotService {

    List<MessageDTO> channelInfo(String title);

    List<MessageDTO> videoCommentRandom(String channelTitle, String videoTitle);

    List<MessageDTO> help(String helpMessage);

    String sendMessage(String command);

    List<MessageDTO> processCommand(String commandText, long userId);

    List<MessageDTO> createRoom(long userId, String roomTitle, boolean privateRoom);

    List<MessageDTO> deleteRoom(long userId, String roomTitle);

    List<MessageDTO> renameRoom(long userId, String roomTitle, String newTitle);

    List<MessageDTO> connectToRoom(long userId, String roomTitle, String userNameToConnect);

    List<MessageDTO> unknownCommand();

    List<MessageDTO> disconnectFromRoom(long userId, String roomTitle, String userNameToDisconnect, long banTime);

    List<MessageDTO> renameUser(long userId, String userToRename, String newName);

    List<MessageDTO> assignRoleToUser(long userId, String userToAssign, String tag);
}