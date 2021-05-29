package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.auth.JwtProvider;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Tag(name = "User controller", description = "User controller description")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @Operation(
            summary = "User registration",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to register a user"
    )
    @PostMapping("/create")
    public ResponseEntity create(
            @RequestBody @Parameter(description = "UserDTO", required = true) UserDTO userDTO) {
        if (userService.create(userDTO.toUser())) {
            return ResponseEntity.ok("User " + userDTO.getName() + " created.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "All users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to get all users")
    @GetMapping
    public ResponseEntity getAll() {
        List<User> allUsers = userService.getAllUsers();
        List<UserDTO> allUsersDTO = allUsers.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUsersDTO);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.authorize(userDTO.toUser()));
    }

    @Operation(
            summary = "Updating users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to update a user"
    )
    @PutMapping("/update")
    public ResponseEntity update(
            @RequestBody @Parameter(description = "UserDTO", required = true) UserDTO userDTO) {
        if (userService.update(userDTO.toUser())) {
            return ResponseEntity.ok("User " + userDTO.getName() + " updated.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Deleting users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to delete a user"
    )
    @DeleteMapping("/delete")
    public ResponseEntity deleteByName(@RequestParam (value = "name") String name) {
        if (userService.delete(name)) {
            return ResponseEntity.ok("User " + name + " deleted.");
        } else  {
            return ResponseEntity.badRequest().build();
        }
    }
}