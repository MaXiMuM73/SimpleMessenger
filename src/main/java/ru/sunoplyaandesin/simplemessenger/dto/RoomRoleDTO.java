package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.RoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

@Data
public class RoomRoleDTO {

    private long id;

    private RoomRoles roomRole;

    public static RoomRoleDTO from(RoomRole roomRole) {
        RoomRoleDTO roomRoleDTO = new RoomRoleDTO();
        roomRoleDTO.setId(roomRole.getId());
        roomRoleDTO.setRoomRole(roomRole.getRoomRole());
        return roomRoleDTO;
    }

    public RoomRole toRoomRole() {
        RoomRole roomRole = new RoomRole();
        roomRole.setId(this.id);
        roomRole.setRoomRole(this.roomRole);
        return roomRole;
    }
}