package ru.sunoplyaandesin.simplemessenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;


@Data
public class UserDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private SystemRoles systemRole;
}