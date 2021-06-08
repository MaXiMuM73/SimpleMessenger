package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.auth.JwtProvider;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;
import ru.sunoplyaandesin.simplemessenger.dto.UserDTO;
import ru.sunoplyaandesin.simplemessenger.exception.UserNotFoundException;
import ru.sunoplyaandesin.simplemessenger.exception.WrongPasswordException;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.UserService;
import ru.sunoplyaandesin.simplemessenger.service.mapper.RoomMapper;
import ru.sunoplyaandesin.simplemessenger.service.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setSystemRole(SystemRoles.SYSTEM_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO find(long id) {
        User user = findById(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO find(String name) {
        User user = findByName(name);
        return userMapper.toDto(user);
    }

    @Override
    public User findUser(long id) {
        return findById(id);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User updateUser = findByName(userDTO.getName());
        updateUser.setName(userDTO.getName());
        updateUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        updateUser.setSystemRole(userDTO.getSystemRole());
        userRepository.save(updateUser);
        return userMapper.toDto(updateUser);
    }

    @Override
    public void delete(String name) {
        User user = findByName(name);
        userRepository.delete(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String authorize(UserDTO userDTO) {
        User foundUser = userRepository.findByName(userDTO.getName())
                .orElseThrow(() -> new UsernameNotFoundException(userDTO.getName()));

        if (!passwordEncoder.matches(userDTO.getPassword(), foundUser.getPassword())) {
            throw new WrongPasswordException();
        }

        return jwtProvider.generateToken(userDTO.getName());
    }

    @Override
    public String setRoomRole(String userName, String roomRole, long roomId, long id) {
        User user = findById(id);

        Room room = roomRepository.findById(roomId).get();

        UserRoomRole userOwner = user.getUserRoomRoles().stream()
                .filter(userRoomRole -> userRoomRole.getRoom().equals(room))
                .findAny().orElse(null);

        if (userOwner != null & userOwner.getRoomRole().equals(RoomRoles.ROOM_OWNER)) {
            User userUpdate = findByName(userName);
            UserRoomRole userUpdateRoomRole = userUpdate.getUserRoomRoles().stream()
                    .filter(userRoomRole -> userRoomRole.getRoom().equals(room))
                    .findAny().orElse(null);

            if (userUpdateRoomRole != null) {
                userUpdateRoomRole.setRoomRole(RoomRoles.valueOf(roomRole));
                userRepository.save(userUpdate);
            }
        }
        return roomRole;
    }

    private User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }
}