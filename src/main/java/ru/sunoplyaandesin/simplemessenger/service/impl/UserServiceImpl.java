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
import ru.sunoplyaandesin.simplemessenger.exception.UserNotFoundException;
import ru.sunoplyaandesin.simplemessenger.exception.WrongPasswordException;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Override
    public User create(User user) {
        user.setSystemRole(SystemRoles.SYSTEM_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User find(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }

    @Override
    public void update(User user) {
        User updateUser = findByName(user.getName());
        updateUser.setName(user.getName());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.setSystemRole(user.getSystemRole());
        userRepository.save(updateUser);
    }

    @Override
    public void delete(String name) {
        User user = findByName(name);
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        return userRepository.findByNameAndPassword(name, password).orElseThrow(
                () -> new UsernameNotFoundException(name));
    }

    @Override
    public String authorize(User user) {
        User foundUser = userRepository.findByName(user.getName())
                .orElseThrow(() -> new UsernameNotFoundException(user.getName()));

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new WrongPasswordException();
        }

        return jwtProvider.generateToken(user.getName());
    }

    @Override
    public String setRoomRole(String userName, String roomRole, long roomId, long id) {
        User user = find(id);

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
}