package ru.sunoplyaandesin.simplemessenger.controller.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.sunoplyaandesin.simplemessenger.controller.YBotController;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class YBotControllerImpl implements YBotController {

    private final YBotService yBotService;

    @Override
    public ResponseEntity<MessageDTO> channelInfo(String title) {
        return ResponseEntity.ok(yBotService.channelInfo(title));
    }

    @Override
    public ResponseEntity<List<MessageDTO>> videoRandomComment(String channelTitle, String videoTitle) throws IOException {
        return ResponseEntity.ok(yBotService.videoCommentRandom(channelTitle, videoTitle));
    }

    @Override
    public ResponseEntity<String> command(String command) {
        return null;
    }
}