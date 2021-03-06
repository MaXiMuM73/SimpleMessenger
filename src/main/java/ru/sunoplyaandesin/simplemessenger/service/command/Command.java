package ru.sunoplyaandesin.simplemessenger.service.command;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;

import java.util.List;

public interface Command {
    String COMMANDS =
            "//help - print all commands " +
                    "//channelinfo {channel title} - print last five video links " +
                    "//randomcomment {channel title}||{video title} - print random comment " +
                    "//find {channel title}||{video title} " +
                    "//room create {room title} - create room for user " +
                    "//room rename {room title}||{new room title} " +
                    "//room remove {room title} " +
                    "//room connect {room title} -l {user name} " +
                    "//room disconnect {room title} " +
                    "//user rename {user name}||{new user name} " +
                    "//user moderator {user name} -n (moderator) -d (user) " +
                    "//user ban -l {user name} -m {ban time} ";

    List<MessageDTO> execute(String command, long userId);
}