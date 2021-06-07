package ru.sunoplyaandesin.simplemessenger.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(long id) {
        super("Room with id " + id + " not found.");
    }

    public RoomNotFoundException(String title) {
        super("Room with title " + title + " not found.");
    }
}