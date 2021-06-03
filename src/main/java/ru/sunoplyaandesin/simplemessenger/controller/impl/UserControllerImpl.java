package ru.sunoplyaandesin.simplemessenger.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.controller.UserController;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDTO> create(UserDTO userDTO) {
        User user = userService.create(userDTO.toUser());
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @Override
    public ResponseEntity<UserDTO> find(long userId) {
        User user = userService.find(userId);
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @Override
    public ResponseEntity<String> update(UserDTO userDTO) {
        userService.update(userDTO.toUser());
        return ResponseEntity.ok("User with name " + userDTO.getName() + " updated.");
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> allUsers = userService.findAll();
        List<UserDTO> allUsersDTO = allUsers.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUsersDTO);
    }

    @Override
    public ResponseEntity<String> auth(UserDTO userDTO) {
        return ResponseEntity.ok(userService.authorize(userDTO.toUser()));
    }

    @Override
    public ResponseEntity<String> deleteByName(String name) {
        userService.delete(name);
        return ResponseEntity.ok("User " + name + " deleted.");
    }
}