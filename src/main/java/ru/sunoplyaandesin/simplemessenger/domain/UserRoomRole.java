package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_room_roles")
public class UserRoomRole {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * User id
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Room id the user is in
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * User room role
     */
    @Column(name = "room_role")
    @Enumerated(value = EnumType.STRING)
    private RoomRoles roomRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoomRole userRoomRole = (UserRoomRole) o;

        if (!user.equals(userRoomRole.user)) return false;
        return room.equals(userRoomRole.room);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + room.hashCode();
        return result;
    }
}