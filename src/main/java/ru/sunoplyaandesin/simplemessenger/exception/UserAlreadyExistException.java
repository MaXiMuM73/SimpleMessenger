package ru.sunoplyaandesin.simplemessenger.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(long id) {
        super("User with id " + id + " already exists.");
    }

    public UserAlreadyExistException(String name) {
        super("User with name " + name + " already exists.");
    }
}