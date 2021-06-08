package ru.sunoplyaandesin.simplemessenger.service.mapper;

import org.mapstruct.Mapper;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDTO toDto(Room room);

    Room toRoom(RoomDTO roomDTO);
}