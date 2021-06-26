package ru.sunoplyaandesin.simplemessenger.controller.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.sunoplyaandesin.simplemessenger.controller.YBotController;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YBotControllerImpl implements YBotController {

    private final YBotService yBotService;

    @Override
    public ResponseEntity<List<MessageDTO>> channelInfo(String title) {
        return ResponseEntity.ok(yBotService.channelInfo(title));
    }

    @Override
    public ResponseEntity<List<MessageDTO>> videoRandomComment(String channelTitle, String videoTitle) {
        return ResponseEntity.ok(yBotService.videoCommentRandom(channelTitle, videoTitle));
    }

    @Override
    public ResponseEntity<List<MessageDTO>> command(String command, User user) {
        return ResponseEntity.ok(yBotService.processCommand(command, user.getId()));
    }
}