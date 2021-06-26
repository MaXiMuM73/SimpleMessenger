package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

import javax.persistence.*;
import java.util.Date;

/**
 * User room role
 */
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

    /**
     * User login time available
     */
    @Column(name = "available_login_time")
    private Date availableLoginTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoomRole that = (UserRoomRole) o;

        if (!user.equals(that.user)) return false;
        return room.equals(that.room);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + room.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserRoomRole{" +
                "id=" + id +
                ", user=" + user +
                ", room=" + room +
                ", roomRole=" + roomRole +
                ", availableLoginTime=" + availableLoginTime +
                '}';
    }
}