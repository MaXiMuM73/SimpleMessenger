package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.Room;

import java.util.Date;

@Data
public class RoomDTO {

    private Date createdTime;

    private String title;

    private boolean privateRoom;

    private long userId;

    public static RoomDTO from(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setCreatedTime(room.getCreatedDate());
        roomDTO.setTitle(room.getTitle());
        roomDTO.setPrivateRoom(room.isPrivateRoom());
        roomDTO.setUserId(room.getUser().getId());
        return roomDTO;
    }

    public Room toRoom() {
        Room room = new Room();
        room.setTitle(this.title);
        room.setPrivateRoom(this.privateRoom);
        return room;
    }
}