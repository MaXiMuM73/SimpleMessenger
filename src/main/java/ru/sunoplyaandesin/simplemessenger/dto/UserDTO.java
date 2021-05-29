package ru.sunoplyaandesin.simplemessenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;


@Data
public class UserDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private SystemRoles systemRole;

    public static UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword("password");
        userDTO.setSystemRole(user.getSystemRole());
        return userDTO;
    }

    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setSystemRole(this.systemRole);
        return user;
    }
}