package ru.sunoplyaandesin.simplemessenger.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;

public class TestData {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static UserDTO givenUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("test");
        userDTO.setPassword(passwordEncoder.encode("password"));
        return userDTO;
    }

    public static UserDTO expectedUserDTO() {
        UserDTO expectedUser = new UserDTO();
        expectedUser.setId(1L);
        expectedUser.setName("test");
        expectedUser.setPassword(passwordEncoder.encode("password"));
        expectedUser.setSystemRole(SystemRoles.SYSTEM_USER);
        return expectedUser;
    }

    public static UserDTO newUserDTO() {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setName("new_user");
        newUserDTO.setPassword(passwordEncoder.encode("new_password"));
        newUserDTO.setSystemRole(SystemRoles.SYSTEM_ADMIN);
        return newUserDTO;
    }

    public static User newUser() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("new_user");
        newUser.setPassword(passwordEncoder.encode("new_password"));
        newUser.setSystemRole(SystemRoles.SYSTEM_ADMIN);
        return newUser;
    }
}