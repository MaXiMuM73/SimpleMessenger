package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    /**
     * User name
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * User password
     */
    @Column(name = "password")
    private String password;

    /**
     * User system role
     */
    @Column(name = "system_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SystemRoles systemRole;

    /**
     * User rooms
     */
    @ManyToMany(mappedBy = "users")
    private Set<Room> rooms = new HashSet<>();
}