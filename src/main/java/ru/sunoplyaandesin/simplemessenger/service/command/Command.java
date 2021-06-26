package ru.sunoplyaandesin.simplemessenger.service.command;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;

import java.util.List;

public interface Command {
    String COMMANDS =
            "//help - print all commands " +
                    "//channelinfo {channel title} - print last five video links " +
                    "//randomcomment {channel title}||{video title} - print random comment " +
                    "//room create {room title} - create room for user " +
                    "//room rename {room totle}||{new room title} " +
                    "//room remove {room title} " +
                    "//room connect {room title} -l {user name} " +
                    "//room disconnect {room title} " +
                    "//user rename {user name}||{new user name} " +
                    "//user moderator {user name} -n (moderator) -d (user) ";

    List<MessageDTO> execute(String command, long userId);
}