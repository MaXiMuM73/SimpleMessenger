package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.controller.UserController;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<String> create(UserDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @Override
    public ResponseEntity<UserDTO> find(long userId) {
        return ResponseEntity.ok(userService.find(userId));
    }

    @Override
    public ResponseEntity<UserDTO> update(UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Override
    public ResponseEntity<String> auth(UserDTO userDTO) {
        return ResponseEntity.ok(userService.authorize(userDTO));
    }

    @Override
    public ResponseEntity<String> deleteByName(String name) {
        userService.delete(name);
        return ResponseEntity.ok("User " + name + " deleted.");
    }

    @Override
    public ResponseEntity<String> setModerator(String userName, String roomRole,
                                               long roomId, User user) {
        userService.setRoomRole(userName, roomRole, roomId, user.getId());
        return ResponseEntity.ok(userName + " room role: " + roomRole);
    }
}