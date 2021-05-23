package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.RoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.User;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RoomDTO {

    private Date createdTime;

    private String title;

    private boolean privateRoom;

    private UserDTO userDTO;

    private Set<UserDTO> usersDTO;

    private List<RoomRoleDTO> roomRolesDTO;

    public static RoomDTO from(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setCreatedTime(room.getCreatedDate());
        roomDTO.setTitle(room.getTitle());
        roomDTO.setPrivateRoom(room.isPrivateRoom());
        roomDTO.setUserDTO(UserDTO.from(room.getUser()));

        Set<UserDTO> usersDTO = room.getUsers().stream()
                .map(UserDTO::from)
                .collect(Collectors.toSet());
        roomDTO.setUsersDTO(usersDTO);

        List<RoomRoleDTO> roomRolesDTO = room.getRoomRoles().stream()
                .map(RoomRoleDTO::from)
                .collect(Collectors.toList());
        roomDTO.setRoomRolesDTO(roomRolesDTO);

        return roomDTO;
    }

    public Room toRoom() {
        Room room = new Room();
        room.setTitle(this.title);
        room.setPrivateRoom(this.privateRoom);
        room.setUser(this.userDTO.toUser());

        if (!CollectionUtils.isEmpty(this.usersDTO)) {
            Set<User> users = this.usersDTO.stream()
                    .map(UserDTO::toUser)
                    .collect(Collectors.toSet());
            room.setUsers(users);
        }

        if (!CollectionUtils.isEmpty(this.roomRolesDTO)){
            List<RoomRole> roomRoles = this.roomRolesDTO.stream()
                    .map(RoomRoleDTO::toRoomRole)
                    .collect(Collectors.toList());
            room.setRoomRoles(roomRoles);
        }
        return room;
    }
}