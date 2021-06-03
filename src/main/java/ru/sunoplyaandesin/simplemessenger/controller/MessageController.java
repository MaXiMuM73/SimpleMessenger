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

import java.util.List;

@Tag(name = "Message controller", description = "Message controller description")
@RequestMapping("/messages")
public interface MessageController {

    @Operation(
            summary = "Message creation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to create a message"
    )
    @PostMapping("/create/{roomId}")
    ResponseEntity<MessageDTO> create(
            @RequestBody @Parameter(description = "messageText", required = true) String text,
            @PathVariable(name = "roomId") long roomId,
            @AuthenticationPrincipal User user);

    @Operation(
            summary = "Message deleting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to delete a message"
    )
    @DeleteMapping("delete/{id}")
    ResponseEntity<String> delete(
            @PathVariable(name = "id") long messageId);

    @Operation(
            summary = "Message updating",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to update a message"
    )
    @PutMapping("{id}")
    ResponseEntity<String> update(
            @RequestBody @Parameter(description = "messageText", required = true) String text,
            @PathVariable(name = "id") long messageId);

    @Operation(
            summary = "Message finding",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to find all messages in room"
    )
    @GetMapping("{roomId}")
    ResponseEntity<List<MessageDTO>> findAll(
            @PathVariable(name = "roomId") long roomId);

    @Operation(
            summary = "All messages deleting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to delete all messages in room"
    )
    @DeleteMapping("{roomId}")
    ResponseEntity<String> deleteAll(
            @PathVariable(name = "roomId") long roomId);
}