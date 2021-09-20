package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User controller", description = "User controller description")
@RequestMapping("/users")
public interface UserController {

    @Operation(
            summary = "User registration",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to register a user"
    )
    @PostMapping("/create")
    ResponseEntity<String> create(
            @Valid @RequestBody @Parameter(description = "UserDTO", required = true) UserDTO userDTO);

    @Operation(
            summary = "User finding",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to register a user"
    )
    @GetMapping("{userId}")
    ResponseEntity<UserDTO> find(
            @PathVariable(name = "userId") long userId);

    @Operation(
            summary = "Updating users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to update a user"
    )
    @PutMapping("/update")
    ResponseEntity<UserDTO> update(
            @RequestBody @Parameter(description = "UserDTO", required = true) UserDTO userDTO);

    @Operation(
            summary = "All users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to get all users")
    @GetMapping
    ResponseEntity<List<UserDTO>> findAll();

    @Operation(
            summary = "User Authorization",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to log in")
    @PostMapping("/auth")
    ResponseEntity<String> auth(@RequestBody UserDTO userDTO);

    @Operation(
            summary = "Deleting users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to delete a user"
    )
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteByName(@RequestParam(value = "name") String name);

    @Operation(
            summary = "Set room role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you set or downgrade room role to user"
    )
    @PutMapping("/moderator/{userName}")
    ResponseEntity<String> setModerator(
            @PathVariable(name = "userName") String userName,
            @RequestParam(value = "userRole") String roomRole,
            @RequestParam(value = "roomId") long roomId,
            @AuthenticationPrincipal User user);
}