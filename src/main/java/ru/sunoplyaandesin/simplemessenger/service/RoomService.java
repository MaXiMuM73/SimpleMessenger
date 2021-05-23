package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;

import java.util.Optional;

public interface RoomService {
    boolean create(Room room);

    boolean deleteByTitle(String title);

    Optional<Room> findByTitle(String title);

    boolean renameRoom(String title, String newTitle);
}
