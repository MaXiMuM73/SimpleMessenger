package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.controller.RoomController;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class RoomControllerImpl implements RoomController {

    private final RoomService roomService;

    @Override
    public ResponseEntity<RoomDTO> create(RoomDTO roomDTO, User user) {
        Room room = roomService.create(roomDTO.toRoom(), user.getId());
        return ResponseEntity.ok(RoomDTO.from(room));
    }

    @Override
    public ResponseEntity<String> delete(long id) {
        roomService.delete(id);
        return ResponseEntity.ok("Room with id " + id + " deleted.");
    }

    @Override
    public ResponseEntity<String> rename(long id, String newTitle) {
        roomService.rename(id, newTitle);
        return ResponseEntity.ok("Room with id " + id + " renamed to " +
                newTitle + ".");
    }

    @Override
    public ResponseEntity<List<RoomDTO>> findAll(long userId) {
        List<RoomDTO> allRoomsDTO = roomService.findAll(userId)
                .stream().map(
                        RoomDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(allRoomsDTO);
    }

    @Override
    public ResponseEntity<String> connect(long userId, long roomId) {
        roomService.connect(userId, roomId);
        return ResponseEntity.ok("User with id " + userId + " connected to room with id " + roomId);
    }

    @Override
    public ResponseEntity<String> connectAll(long roomId) {
        roomService.connectAll(roomId);
        return ResponseEntity.ok("All users connected to room with id " + roomId);
    }
}