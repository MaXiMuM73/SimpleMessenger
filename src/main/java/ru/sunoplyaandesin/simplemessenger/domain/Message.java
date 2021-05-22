package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@Data
@Table(name = "messages")
public class Message {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    /**
     * Date of creation
     */
    @NotBlank
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    /**
     * Text in message
     */
    @Column(name = "text", nullable = false)
    private String text;

    /**
     * ID of the user who wrote the message
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * ID of the room where the message is written
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}