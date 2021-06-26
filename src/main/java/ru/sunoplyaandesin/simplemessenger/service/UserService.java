package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

import java.util.List;


public interface UserService {

    /**
     * Creates new user {@link User user}
     *
     * @param userDTO of {@link UserDTO userDTO}
     * @return {@link UserDTO userDTO}
     */
    UserDTO create(UserDTO userDTO);

    /**
     * Finds user by id {@link User user}
     *
     * @param id of {@link User user}
     * @return {@link UserDTO userDTO}
     */
    UserDTO find(long id);

    /**
     * Finds user by name {@link User user}
     *
     * @param name of {@link User user}
     * @return {@link UserDTO userDTO}
     */
    UserDTO find(String name);

    User findUser(long id);

    User findUser(String name);

    /**
     * Updates user {@link User user}
     *
     * @param userDTO of {@link UserDTO userDTO}
     * @return {@link UserDTO userDTO}
     */
    UserDTO update(UserDTO userDTO);

    boolean rename(long userId, String userToRename, String newUserName);

    /**
     * Deletes user by name {@link User user}
     *
     * @param name of {@link UserDTO userDTO}
     */
    void delete(String name);

    /**
     * Returns list of all users {@link User user}
     *
     * @return list of {@link User user}
     */
    List<User> findAllUsers();

    /**
     * Returns list of all users {@link UserDTO userDTO}
     * @return list of {@link UserDTO userDTO}
     */
    List<UserDTO> findAll();

    /**
     * Authorizes user
     *
     * @param userDTO of {@link UserDTO userDTO}
     * @return jwt token
     */
    String authorize(UserDTO userDTO);

    /**
     * Sets user room role
     *
     * @param userName of {@link User user}
     * @param roomRole of {@link User user}
     * @param roomId   of {@link Room room}
     * @param userId   of {@link User user}
     */
    void setRoomRole(String userName, String roomRole, long roomId, long userId);

    boolean assignRoleToUser(long userId, String roomTitle, String userToAssign, String tag);
}