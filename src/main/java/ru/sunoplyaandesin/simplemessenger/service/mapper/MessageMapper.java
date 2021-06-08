package ru.sunoplyaandesin.simplemessenger.service.mapper;

import org.mapstruct.Mapper;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDTO toDto(Message message);

    Message toMessage(MessageDTO messageDTO);

    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);
}
