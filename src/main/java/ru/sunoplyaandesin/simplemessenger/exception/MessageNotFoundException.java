package ru.sunoplyaandesin.simplemessenger.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(long id) {
        super("Message with id " + id + " not found.");
    }
}