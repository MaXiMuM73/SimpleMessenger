package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Date createdDate;

    private String text;

    private UserDTO user;

    private RoomDTO room;
}