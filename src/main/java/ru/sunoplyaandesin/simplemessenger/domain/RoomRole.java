package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rooms_roles")
public class RoomRole {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    /**
     * User
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    /**
     * User room
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * User room role
     */
    @Column(name = "room_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoomRoles roomRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomRole roomRole1 = (RoomRole) o;

        if (!user.equals(roomRole1.user)) return false;
        if (!room.equals(roomRole1.room)) return false;
        return roomRole == roomRole1.roomRole;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + room.hashCode();
        result = 31 * result + roomRole.hashCode();
        return result;
    }
}