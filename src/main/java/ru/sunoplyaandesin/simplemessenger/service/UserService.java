package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

import java.util.List;


public interface UserService {

    UserDTO create(UserDTO userDTO);

    UserDTO find(long id);

    UserDTO find(String name);

    User findUser(long id);

    UserDTO update(UserDTO userDTO);

    void delete(String name);

    List<User> findAllUsers();

    List<UserDTO> findAll();

    String authorize(UserDTO userDTO);

    String setRoomRole(String userName, String roomRole, long roomId, long id);
}