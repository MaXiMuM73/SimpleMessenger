package ru.sunoplyaandesin.simplemessenger.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sunoplyaandesin.simplemessenger.auth.JwtProvider;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.exception.UserNotFoundException;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.mapper.UserMapper;
import ru.sunoplyaandesin.simplemessenger.service.mapper.UserMapperImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private static final UserMapper userMapper = new UserMapperImpl();

    private static final UserRepository userRepository = mock(UserRepository.class);

    private static final JwtProvider jwtProvider = mock(JwtProvider.class);

    private static String token = "token";

    private User givenUser, expectedUser, newUser;

    private UserDTO newUserDTO;

    @BeforeEach
    void init() {
        givenUser = userMapper.toUser(TestData.givenUserDTO());
        expectedUser = userMapper.toUser(TestData.expectedUserDTO());
        newUser = TestData.newUser();
        newUserDTO = TestData.newUserDTO();
    }

    @Test
    void givenUserDTO_whenCreate_thenReturnUserDTO() {
        when(userRepository.save(givenUser))
                .thenReturn(expectedUser);
        UserDTO expectedUserDTO = userMapper.toDto(userRepository.save(givenUser));
        assertThat(expectedUserDTO).isNotNull();
        assertThat(expectedUserDTO.getSystemRole()).isEqualTo(SystemRoles.SYSTEM_USER);
    }

    @Test
    void givenId_whenFind_thenReturnUserDTO() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(expectedUser));
        User expectedUserById = userRepository.findById(1L).get();
        UserDTO expectedUserDTO = userMapper.toDto(expectedUserById);
        assertThat(expectedUserDTO).isNotNull();
    }

    @Test
    void givenNullId_whenFind_thenThrowUserNotFoundException() {
        when(userRepository.findById(null))
                .thenThrow(UserNotFoundException.class);

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userRepository.findById(null));
    }

    @Test
    void givenUserDTO_whenUpdate_thenReturnUpdatedUserDTO() {
        when(userRepository.findByName(newUserDTO.getName()))
                .thenReturn(Optional.of(expectedUser));

        User updateUser = userRepository.findByName(newUserDTO.getName()).get();
        updateUser.setId(expectedUser.getId());
        updateUser.setName(newUserDTO.getName());
        updateUser.setPassword(newUserDTO.getPassword());
        updateUser.setSystemRole(newUserDTO.getSystemRole());

        when(userRepository.save(updateUser))
                .thenReturn(updateUser);

        userRepository.save(updateUser);

        assertThat(updateUser).isNotNull().isEqualTo(newUser);
    }

    @Test
    void givenNothing_whenFindAllUsers_thenReturnListOfUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.add(expectedUser);
        when(userRepository.findAll())
                .thenReturn(allUsers);

        assertThat(allUsers).isNotNull().contains(expectedUser);
    }

    @Test
    void givenUserDTO_whenAuthorize_thenReturnToken() {
        when(jwtProvider.generateToken(givenUser.getName()))
                .thenReturn("token");
        String expectedToken = jwtProvider.generateToken(givenUser.getName());
        assertThat(expectedToken).isNotNull().isEqualTo(token);
    }
}