package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;

import java.util.Date;


@RestController
@Tag(name = "Room controller", description = "Room controller description")
@RequestMapping("/room")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(
            summary = "Creating rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to create a room"
    )
    @PostMapping("/create")
    public ResponseEntity create(
            @RequestBody @Parameter(description = "RoomDTO", required = true) RoomDTO roomDTO) {
        Room room = roomDTO.toRoom();
        room.setCreatedDate(new Date());
        if (roomService.create(room)) {
            return ResponseEntity.ok("Room " + room.getTitle() + " created.");
        } else return ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "Deleting rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to delete a room"
    )
    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity delete(
            @RequestParam(value = "title") @Parameter(description = "room title", required = true) String title) {
        if (roomService.deleteByTitle(title)) {
            return ResponseEntity.ok("Room " + title + " deleted.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}