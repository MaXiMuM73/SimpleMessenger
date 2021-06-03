package ru.sunoplyaandesin.simplemessenger.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("User with id " + id + " not found.");
    }

    public UserNotFoundException(String name) {
        super("User with name " + name + " not found.");
    }
}