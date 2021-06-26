package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.controller.MessageController;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.MessageService;
import ru.sunoplyaandesin.simplemessenger.service.YBotService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final MessageService messageService;

    private final YBotService yBotService;

    @Override
    public ResponseEntity<MessageDTO> create(String text, long roomId, User user) {
        return ResponseEntity.ok(messageService.create(text, roomId, user.getId()));
    }

    @Override
    public ResponseEntity<String> delete(long messageId) {
        messageService.delete(messageId);
        return ResponseEntity.ok("Message with id " + messageId + " deleted.");
    }

    @Override
    public ResponseEntity<String> update(String text, long messageId) {
        messageService.update(messageId, text);
        return ResponseEntity.ok("Message with id " + messageId + " updated.");
    }

    @Override
    public ResponseEntity<List<MessageDTO>> findAll(long roomId) {
        return ResponseEntity.ok(messageService.findAll(roomId));
    }

    @Override
    public ResponseEntity<String> deleteAll(long roomId) {
        messageService.deleteAll(roomId);
        return ResponseEntity.ok("All messages in room with id " + roomId + " deleted.");
    }

    @Override
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseEntity<String> sendMessage(MessageDTO messageDTO) {
        return ResponseEntity.ok(yBotService.sendMessage(messageDTO.getText()));
    }
}