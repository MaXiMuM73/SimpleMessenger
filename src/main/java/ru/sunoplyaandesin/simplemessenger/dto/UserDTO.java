package ru.sunoplyaandesin.simplemessenger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;

import java.util.List;

@Data
public class UserDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private SystemRoles systemRole;

    private List<RoomDTO> rooms;
}