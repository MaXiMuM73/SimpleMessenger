package ru.sunoplyaandesin.simplemessenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;

import java.io.IOException;
import java.util.List;

@Tag(name = "Youtube bot controller", description = "Youtube bot controller description")
@RequestMapping("/ybot")
public interface YBotController {

    @Operation(
            summary = "Channel info",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to get channel info"
    )
    @GetMapping("/channelInfo")
    ResponseEntity<List<MessageDTO>> channelInfo(@RequestParam(value = "channelTitle") String title) throws IOException;

    @Operation(
            summary = "Random comment of video",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            description = "Allows you to get random comment of video"
    )
    @GetMapping("/videoRandomComment")
    ResponseEntity<List<MessageDTO>> videoRandomComment(@RequestParam(value = "channelTitle") String channelTitle,
                                                        @RequestParam(value = "videoTitle") String videoTitle) throws IOException;

    @GetMapping()
    ResponseEntity<List<MessageDTO>> command(@RequestBody String command,
                                             @AuthenticationPrincipal User user);
}