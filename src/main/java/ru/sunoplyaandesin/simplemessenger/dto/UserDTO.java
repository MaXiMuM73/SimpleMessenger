package ru.sunoplyaandesin.simplemessenger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @NotNull
    @Size(
            min = 2,
            max = 20,
            message = "Name is required, maximum 20 characters."
    )
    private String name;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(
            min = 2,
            max = 50,
            message = "Password is required, minimum 2 characters."
    )
    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private SystemRoles systemRole;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<RoomDTO> rooms;
}