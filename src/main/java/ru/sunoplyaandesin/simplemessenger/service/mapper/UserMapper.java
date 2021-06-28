package ru.sunoplyaandesin.simplemessenger.service.mapper;

import org.mapstruct.Mapper;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toUser(UserDTO userDTO);

    List<RoomDTO> map(List<Room> rooms);
}
