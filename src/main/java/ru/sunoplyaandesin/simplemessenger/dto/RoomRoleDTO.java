package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

@Data
public class RoomRoleDTO {

    private long id;

    private RoomRoles roomRole;

    public static RoomRoleDTO from(UserRoomRole userRoomRole) {
        RoomRoleDTO roomRoleDTO = new RoomRoleDTO();
        roomRoleDTO.setId(userRoomRole.getId());
        roomRoleDTO.setRoomRole(userRoomRole.getRoomRole());
        return roomRoleDTO;
    }

    public UserRoomRole toRoomRole() {
        UserRoomRole userRoomRole = new UserRoomRole();
        userRoomRole.setId(this.id);
        userRoomRole.setRoomRole(this.roomRole);
        return userRoomRole;
    }
}