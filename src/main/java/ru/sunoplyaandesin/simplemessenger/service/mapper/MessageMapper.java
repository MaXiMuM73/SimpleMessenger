package ru.sunoplyaandesin.simplemessenger.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDTO toDto(Message message);

    Message toMessage(MessageDTO messageDTO);

    @Mapping(target = "rooms", ignore = true)
    UserDTO userToUserDTO(User user);

    @Mapping(target = "rooms", ignore = true)
    User userDTOtoUser(UserDTO userDTO);
}
