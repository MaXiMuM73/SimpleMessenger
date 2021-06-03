package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;

import java.util.List;


public interface RoomService {

    Room create(Room room, long userId);

    Room find(long id);

    void delete(long id);

    void rename(long id, String newTitle);

    List<Room> findAll(long userId);

    void connect(long userId, long roomId);

    void connectAll(long roomId);
}