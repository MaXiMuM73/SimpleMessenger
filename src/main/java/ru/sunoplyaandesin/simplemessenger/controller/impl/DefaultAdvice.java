package ru.sunoplyaandesin.simplemessenger.controller.impl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (ObjectError fieldError : e.getBindingResult().getFieldErrors()) {
            message.append(fieldError.getDefaultMessage()).append(" ");
        }
        return ResponseEntity.badRequest().body(message.toString());
    }
}