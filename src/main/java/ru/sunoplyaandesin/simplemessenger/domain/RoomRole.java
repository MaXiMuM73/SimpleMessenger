package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

import javax.persistence.*;

@Entity
@Data
@Table(name = "room_roles")
public class RoomRole {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    long id;

    /**
     * User room role
     */
    @Column(name = "room_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    RoomRoles roomRole;
}