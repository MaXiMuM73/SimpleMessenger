package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;

import java.util.List;


public interface RoomService {
    boolean create(Room room, long userId);

    boolean deleteByTitle(String title);

    boolean rename(String title, String newTitle);

    boolean connectUser(String name, String roomTitle);

    void connectAll(String title);

    List<Room> findAll();
}