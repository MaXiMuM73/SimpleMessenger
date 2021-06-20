package ru.sunoplyaandesin.simplemessenger.service.command.impl;

import ru.sunoplyaandesin.simplemessenger.service.command.Command;

public class UnknownCommand implements Command {

    public static final String UNKNOWN_MESSAGE = "Unknown command. Enter //help to get all commands.";

    @Override
    public String execute(String command) {
        return UNKNOWN_MESSAGE;
    }
}