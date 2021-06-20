package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final YBotService yBotService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public void sendMessage(MessageDTO messageDTO) {
        yBotService.sendMessage(messageDTO.getText());
    }
}