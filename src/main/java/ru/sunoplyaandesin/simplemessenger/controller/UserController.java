package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.service.UserService;


@RestController
@Tag(name = "User controller", description = "User controller description")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "User registration",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to register a user")
    @PostMapping("/register")
    public ResponseEntity registerUser(
            @RequestBody @Parameter(description = "UserDTO", required = true) UserDTO userDTO) {
        if (userService.createUser(userDTO.toUser())) {
            return ResponseEntity.ok("User " + userDTO.getName() + " created.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}