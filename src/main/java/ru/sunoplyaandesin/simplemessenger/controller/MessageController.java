package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Tag(name = "Message controller", description = "Message controller description")
@RequestMapping("/messages")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(
            summary = "Message creation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to create a message"
    )
    @PostMapping("/create/{roomTitle}")
    public ResponseEntity create(
            @RequestBody @Parameter(description = "MessageDTO", required = true) MessageDTO messageDTO,
            @PathVariable(name = "roomTitle") String roomTitle,
            @AuthenticationPrincipal User user) {
        if (messageService.create(messageDTO.toMessage(), roomTitle, user.getId())) {
            return ResponseEntity.ok("Message " + messageDTO.getText() + " in room " + roomTitle + " created.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(
            @PathVariable(name = "id") long messageId) {
        if (messageService.delete(messageId)) {
            return ResponseEntity.ok("Message with id " + messageId + " deleted.");
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(
            @RequestParam(value = "text") String text,
            @PathVariable(name = "id") long messageId) {
        if (messageService.update(messageId, text)) {
            return ResponseEntity.ok("Message with id " + messageId + " updated.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findAll(
            @PathVariable(name = "id") long roomId) {
        List<MessageDTO> allMessages =
                messageService.findAll(roomId)
                        .stream().map(
                        MessageDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(allMessages);
    }
}