package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;

import java.util.List;


public interface RoomService {

    /**
     * Creates new room {@link Room room}
     *
     * @param roomDTO of {@link RoomDTO roomDTO}
     * @param userId  of {@link User user}
     * @return {@link RoomDTO roomDTO}
     */
    RoomDTO create(RoomDTO roomDTO, long userId);

    /**
     * Finds room by id {@link Room room}
     *
     * @param id of {@link Room room}
     * @return {@link RoomDTO roomDTO}
     */
    RoomDTO find(long id);

    /**
     * Finds room by title {@link Room room}
     *
     * @param title of {@link Room room}
     * @return {@link RoomDTO roomDTO}
     */
    RoomDTO find(String title);

    /**
     * Deletes room by id
     *
     * @param id     of {@link Room room}
     * @param userId of {@link User user}
     * @return true if the deletion is successful or false
     */
    boolean delete(long id, long userId);

    /**
     * Deletes room by title
     *
     * @param roomTitle of {@link Room room}
     * @param userId    of {@link User user}
     * @return true if the deletion is successful or false
     */
    boolean delete(String roomTitle, long userId);

    /**
     * Renames room by id
     *
     * @param id       of {@link Room room}
     * @param newTitle of {@link Room room}
     * @param userId   of {@link User user}
     * @return true if the renaming is successful or false
     */
    boolean rename(long id, String newTitle, long userId);

    /**
     * Renames room by title
     *
     * @param roomTitle of {@link Room room}
     * @param newTitle  of {@link Room room}
     * @param userId    of {@link User user}
     * @return true if the renaming is successful or false
     */
    boolean rename(long userId, String roomTitle, String newTitle);

    /**
     * Returns list of all user rooms {@link RoomDTO roomDTO}
     *
     * @param userId of {@link User user}
     * @return list of {@link RoomDTO roomDTO}
     */
    List<RoomDTO> findAll(long userId);

    /**
     * Connects user to room by user ID
     *
     * @param userIdToConnect of {@link User user}
     * @param roomId          of {@link Room room}
     * @return true if the connecting is successful or false
     */
    boolean connect(long userIdToConnect, long roomId);

    boolean connect(String userName, String roomTitle);

    /**
     * Connects user to room
     *
     * @param userIdToConnect of {@link User user}
     * @param roomTitle       of {@link Room room}
     * @return true if the connecting is successful or false
     */
    boolean connect(long userIdToConnect, String roomTitle);

    /**
     * Connects all users to room
     *
     * @param roomId of {@link Room room}
     * @param userId
     */
    void connectAll(long roomId, long userId);

    /**
     * Disconnects user from room
     *
     * @param roomId of {@link Room room}
     * @param userId of {@link User user}
     * @return true if the disconnecting is successful or false
     */
    boolean disconnect(long roomId, long userId);

    /**
     * Disconnects and ban user
     *
     * @param roomTitle            of {@link Room room}
     * @param userNameToDisconnect of {@link User user}
     * @param banTime              in seconds
     * @param userId               of {@link User user}
     * @return true if the disconnecting is successful or false
     */
    boolean disconnect(String roomTitle, String userNameToDisconnect, long banTime, long userId);
}