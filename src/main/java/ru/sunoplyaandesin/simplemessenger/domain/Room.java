package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "rooms")
public class Room {

    /**
     * Unique identifier
     */
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    /**
     * Date of creation
     */
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    /**
     * Room title
     */
    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Privacy Flag
     */
    @NotBlank
    @Column(name = "private", nullable = false)
    private boolean privateRoom;

    /**
     * Room owner
     */
    @OneToOne(fetch = FetchType.EAGER)
    //@MapsId
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Room users
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_in_room",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id"),}
    )
    private Set<User> users = new HashSet<>();

    /**
     * Room user role
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_in_room",
            joinColumns = {@JoinColumn(name = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<RoomRole> roomRoles = new ArrayList<>();
}