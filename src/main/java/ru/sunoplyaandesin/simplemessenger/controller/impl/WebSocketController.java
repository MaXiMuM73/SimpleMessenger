package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.dto.response.MessageText;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MessageText sendMessage(MessageDTO messageDTO) {
        MessageDTO response = new MessageDTO();
        response.setText(messageDTO.getText());
        response.setCreatedDate(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return new MessageText(simpleDateFormat.format(response.getCreatedDate()) + " "
                + response.getText());
    }
}