package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;

import java.util.List;


public interface RoomService {

    RoomDTO create(RoomDTO roomDTO, long userId);

    RoomDTO find(long id);

    RoomDTO find(String title);

    boolean delete(long id, long userId);

    boolean rename(long id, String newTitle, long userId);

    List<RoomDTO> findAll(long userId);

    boolean connect(long userIdToConnect, long roomId);

    void connectAll(long roomId, long userId);

    boolean disconnect(long roomId, long userId);

    boolean disconnect(String roomTitle, long userIdToDisconnect, long banTime, long userId);
}