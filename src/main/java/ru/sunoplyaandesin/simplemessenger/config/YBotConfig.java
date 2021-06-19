package ru.sunoplyaandesin.simplemessenger.config;

import com.google.api.services.youtube.YouTube;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sunoplyaandesin.simplemessenger.auth.YoutubeAuth;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

@Configuration
@RequiredArgsConstructor
public class YBotConfig {

    private final UserService userService;

    private final RoomService roomService;

    @Bean
    public YouTube youTube() {
        return new YouTube.Builder(YoutubeAuth.HTTP_TRANSPORT, YoutubeAuth.JSON_FACTORY, request -> {
        })
                .setApplicationName("youtube-cmdline-search-sample").build();
    }

    @Bean
    public UserDTO yBot() {
        return userService.find("ybot");
    }

    @Bean
    public RoomDTO yBotRoom() {
        return roomService.find("YOUTUBE BOT ROOM");
    }
}