package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.controller.RoomController;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RoomControllerImpl implements RoomController {

    private final RoomService roomService;

    @Override
    public ResponseEntity<RoomDTO> create(RoomDTO roomDTO, User user) {
        return ResponseEntity.ok(roomService.create(roomDTO, user.getId()));
    }

    @Override
    public ResponseEntity<String> delete(long id, User user) {
        if (roomService.delete(id, user.getId())) {
            return ResponseEntity.ok("Room with id " + id + " deleted.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<String> rename(long id, String newTitle, User user) {
        if (roomService.rename(id, newTitle, user.getId())) {
            return ResponseEntity.ok("Room with id " + id + " renamed.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<RoomDTO>> findAll(long userId) {
        return ResponseEntity.ok(roomService.findAll(userId));
    }

    @Override
    public ResponseEntity<String> connect(long userId, long roomId) {
        if (roomService.connect(userId, roomId)) {
            return ResponseEntity.ok("User with id " + userId
                    + " connected to room with id " + roomId);
        } else {
            return ResponseEntity.ok("User with id " + userId
                    + " can't connect to room with id " + roomId);
        }
    }

    @Override
    public ResponseEntity<String> connectAll(long roomId, User user) {
        roomService.connectAll(roomId, user.getId());
        return ResponseEntity.ok("All users connected to room with id " + roomId);
    }

    @Override
    public ResponseEntity<String> disconnect(long roomId, User user) {
        roomService.disconnect(roomId, user.getId());
        return ResponseEntity.ok("User with id " + user.getId()
                + " disconnected from room with id " + roomId);
    }

    @Override
    public ResponseEntity<String> disconnect(String roomTitle, String userNameToDisconnect,
                                             long banTime, User user) {
        roomService.disconnect(roomTitle, userNameToDisconnect, banTime, user.getId());
        return ResponseEntity.ok("User : " + userNameToDisconnect
                + " disconnected from room with title " + roomTitle);
    }
}