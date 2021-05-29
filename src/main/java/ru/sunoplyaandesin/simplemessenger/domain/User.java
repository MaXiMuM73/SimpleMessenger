package ru.sunoplyaandesin.simplemessenger.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", systemRole=" + systemRole +
                '}';
    }

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<RoomRole> roomRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(systemRole.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}