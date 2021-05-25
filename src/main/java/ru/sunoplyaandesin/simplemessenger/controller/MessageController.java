package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.service.MessageService;

import java.util.Date;

@RestController
@Tag(name = "Message controller", description = "Message controller description")
public class MessageController {

    private MessageService messageService;

    public MessageController (MessageService messageService) { this.messageService = messageService;}

    @Operation(
            summary = "Message creation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to create a message"
    )
    @PostMapping("/create")
    public ResponseEntity creatMessage(
            @RequestBody @Parameter(description = "MessageDTO", required = true) MessageDTO messageDTO) {
        if (messageService.createMessage(messageDTO.toMessage())) {
            return ResponseEntity.ok("Message " + messageDTO.getId() + " created.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
