package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "rooms")
public class Room {

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
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * Room title
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Privacy Flag
     */
    @Column(name = "private", nullable = false)
    private boolean privateRoom;

    /**
     * Room owner
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomRole> roomRoles;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", title='" + title + '\'' +
                ", privateRoom=" + privateRoom +
                ", user=" + user +
                '}';
    }
}