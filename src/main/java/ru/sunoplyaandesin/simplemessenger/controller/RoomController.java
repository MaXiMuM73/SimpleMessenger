package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;

import java.util.List;

@Tag(name = "Room controller", description = "Room controller description")
@RequestMapping("/rooms")
public interface RoomController {

    @Operation(
            summary = "Creating rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to create a room"
    )
    @PostMapping("/create")
    ResponseEntity<RoomDTO> create(
            @RequestBody @Parameter(description = "RoomDTO", required = true) RoomDTO roomDTO,
            @AuthenticationPrincipal User user);

    @Operation(
            summary = "Deleting rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to delete a room"
    )
    @DeleteMapping("/delete")
    ResponseEntity<String> delete(
            @RequestParam(value = "roomId")
            @Parameter(description = "room id", required = true) long id,
            @AuthenticationPrincipal User user);

    @Operation(
            summary = "Renaming rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to rename a room"
    )
    @PutMapping("/rename")
    ResponseEntity<String> rename(
            @RequestParam(value = "roomId")
            @Parameter(description = "room id", required = true) long id,
            @RequestParam(value = "newTitle")
            @Parameter(description = "new title", required = true) String newTitle,
            @AuthenticationPrincipal User user);

    @Operation(
            summary = "Finding rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to find all rooms"
    )
    @GetMapping("{userId}")
    ResponseEntity<List<RoomDTO>> findAll(
            @PathVariable(value = "userId") long userId);

    @Operation(
            summary = "Connecting user to room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to connect user to room"
    )
    @PutMapping("/connect")
    ResponseEntity<String> connect(
            @RequestParam(value = "userId")
            @Parameter(description = "user id", required = true) long userId,
            @RequestParam(value = "roomId")
            @Parameter(description = "room title", required = true) long roomId);

    @Operation(
            summary = "Connecting all users to room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to connect all users to room"
    )
    @GetMapping("/connectAll")
    ResponseEntity<String> connectAll(
            @RequestParam(value = "roomId") long roomId,
            @AuthenticationPrincipal User user);

    @Operation(
            summary = "Disconnecting user from room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to disconnect user from room"
    )
    @DeleteMapping("/disconnect")
    ResponseEntity<String> disconnect(
            @RequestParam(value = "roomId")
            @Parameter(description = "room title", required = true) long roomId,
            @AuthenticationPrincipal User user);

    @Operation(
            summary = "Disconnecting user from room and ban",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to disconnect user from room and ban"
    )
    @DeleteMapping("/disconnect/{roomTitle}")
    ResponseEntity<String> disconnect(
            @PathVariable(name = "roomTitle") String roomTitle,
            @RequestParam(value = "userId")
            @Parameter(description = "user id", required = true) long userIdToDisconnect,
            @RequestParam(value = "banTime")
            @Parameter(description = "ban param", required = false) long banTime,
            @AuthenticationPrincipal User user);
}