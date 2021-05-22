package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.Message;

import java.sql.Date;

@Data
public class MessageDTO {

    private long id;

    private Date createdDate;

    private String text;

    private UserDTO userDTO;

    private RoomDTO roomDTO;

    public static MessageDTO from(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setCreatedDate(message.getCreatedDate());
        messageDTO.setText(message.getText());
        messageDTO.setUserDTO(UserDTO.from(message.getUser()));
        messageDTO.setRoomDTO(RoomDTO.from(message.getRoom()));
        return messageDTO;
    }

    public Message toMessage() {
        Message message = new Message();
        message.setId(this.id);
        message.setCreatedDate(this.createdDate);
        message.setText(this.text);
        message.setUser(this.userDTO.toUser());
        return message;
    }
}