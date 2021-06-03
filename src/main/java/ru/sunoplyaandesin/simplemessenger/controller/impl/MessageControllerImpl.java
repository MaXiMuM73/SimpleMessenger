package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.controller.MessageController;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final MessageService messageService;

    @Override
    public ResponseEntity<MessageDTO> create(String text, long roomId, User user) {
        Message message = messageService.create(text, roomId, user.getId());
        return ResponseEntity.ok(MessageDTO.from(message));
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
        List<MessageDTO> allMessages =
                messageService.findAll(roomId)
                        .stream().map(
                        MessageDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(allMessages);
    }

    @Override
    public ResponseEntity<String> deleteAll(long roomId) {
        messageService.deleteAll(roomId);
        return ResponseEntity.ok("All messages in room with id " + roomId + " deleted.");
    }
}