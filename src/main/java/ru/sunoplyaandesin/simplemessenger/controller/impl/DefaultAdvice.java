package ru.sunoplyaandesin.simplemessenger.controller.impl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sunoplyaandesin.simplemessenger.dto.response.Response;
import ru.sunoplyaandesin.simplemessenger.exception.*;

import java.net.UnknownHostException;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler({UserNotFoundException.class,
            RoomNotFoundException.class, MessageNotFoundException.class,
            WrongPasswordException.class, UserRoomRoleNotFoundException.class,
            YoutubeNotFoundException.class, UnknownHostException.class,
            UserAlreadyExistException.class})
    public ResponseEntity<Response> handleException(Exception e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}