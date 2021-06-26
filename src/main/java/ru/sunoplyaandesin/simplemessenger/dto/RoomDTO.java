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
public class RoomDTO {

    private long id;

    private Date createdDate;

    private String title;

    private boolean privateRoom;
}