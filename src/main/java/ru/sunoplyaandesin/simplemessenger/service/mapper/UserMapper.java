package ru.sunoplyaandesin.simplemessenger.service.mapper;

import org.mapstruct.Mapper;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toUser(UserDTO userDTO);
}
