package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;

public interface RoomService {
    boolean create(Room room);

    boolean deleteByTitle(String title);
}
