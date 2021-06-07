package ru.sunoplyaandesin.simplemessenger.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RoomDTO {

    private long id;

    private Date createdDate;

    private String title;

    private boolean privateRoom;
}