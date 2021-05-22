package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {

    private long id;

    private String name;

    private String password;

    private SystemRoles systemRole;

    private Set<RoomDTO> roomsDTO;

    public static UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setSystemRole(user.getSystemRole());

        Set<RoomDTO> roomsDTO = user.getRooms().stream()
                .map(RoomDTO::from)
                .collect(Collectors.toSet());
        userDTO.setRoomsDTO(roomsDTO);

        return userDTO;
    }

    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setSystemRole(this.systemRole);

        Set<Room> rooms = this.roomsDTO.stream()
                .map(RoomDTO::toRoom)
                .collect(Collectors.toSet());
        user.setRooms(rooms);
        return user;
    }
}