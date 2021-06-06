package ru.sunoplyaandesin.simplemessenger.exception;

public class UserRoomRoleNotFoundException extends RuntimeException {
    public UserRoomRoleNotFoundException(long userId, long roomId) {
        super("User with id " + userId +
                " not found in room with id " + roomId + ".");
    }
}