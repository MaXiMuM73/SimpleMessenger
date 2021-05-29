package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Tag(name = "Room controller", description = "Room controller description")
@RequestMapping("/rooms")
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
            @RequestBody @Parameter(description = "RoomDTO", required = true) RoomDTO roomDTO,
            @AuthenticationPrincipal User user) {
        if (roomService.create(roomDTO.toRoom(), user.getId())) {
            return ResponseEntity.ok("Room " + roomDTO.getTitle() + " created.");
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

    @Operation(
            summary = "Renaming rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to rename a room"
    )
    @PutMapping("/rename")
    public ResponseEntity rename(
            @RequestParam(value = "title")
            @Parameter(description = "room title", required = true) String title,
            @Parameter(description = "New title", required = true) String newTitle) {
        if (roomService.rename(title, newTitle)) {
            return ResponseEntity.ok("Room " + title + " renamed.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<RoomDTO> allRoomsDTO = roomService.findAll()
                .stream().map(
                        RoomDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(allRoomsDTO);
    }

    @PutMapping("/connect")
    public ResponseEntity connect(
            @RequestParam(value = "user")
            @Parameter(description = "user name", required = true) String name,
            @RequestParam(value = "room_title")
            @Parameter(description = "room title", required = true) String roomTitle) {
        if (roomService.connectUser(name, roomTitle)) {
            return ResponseEntity.ok("User " + name + " added to Room " + roomTitle + ".");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/connectAll")
    public ResponseEntity connectAll(
            @RequestParam(value = "title") String title) {
        roomService.connectAll(title);
        return ResponseEntity.ok("All users connected to room " + title);
    }
}