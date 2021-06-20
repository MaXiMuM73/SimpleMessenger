package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;

import java.util.List;

public interface YBotService {

    MessageDTO channelInfo(String title);

    List<MessageDTO> videoCommentRandom(String channelTitle, String videoTitle);

    void help(String helpMessage);

    void sendMessage(String command);
}