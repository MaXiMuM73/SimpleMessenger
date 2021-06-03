package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;
import ru.sunoplyaandesin.simplemessenger.exception.RoomNotFoundException;
import ru.sunoplyaandesin.simplemessenger.exception.UserNotFoundException;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRoomRoleRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final UserRoomRoleRepository userRoomRoleRepository;

    @Override
    public Room create(Room room, long userId) {
        User user = userRepository.findById(userId).get();
        room.setCreatedDate(new Date());
        room.setUser(user);
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room find(long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    @Override
    public void delete(long id) {
        Room room = find(id);
        roomRepository.delete(room);
    }

    @Override
    public void rename(long id, String newTitle) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        room.setTitle(newTitle);
        roomRepository.save(room);
    }

    @Override
    public void connect(long userId, long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        UserRoomRole userRoomRole = UserRoomRole.builder()
                .user(user)
                .room(room)
                .roomRole(RoomRoles.ROOM_USER)
                .build();

        userRoomRoleRepository.save(userRoomRole);
    }

    @Override
    public void connectAll(long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        List<User> allUsers = userRepository.findAll();

        Set<UserRoomRole> allUsersInRoom = userRoomRoleRepository.findAllByRoomId(room.getId());

        Set<UserRoomRole> newUsersToAdd = new HashSet<>();

        for (User user : allUsers) {
            UserRoomRole userRoomRole = UserRoomRole.builder()
                    .room(room)
                    .user(user)
                    .roomRole(RoomRoles.ROOM_USER)
                    .build();
            if (!allUsersInRoom.contains(userRoomRole)) {
                newUsersToAdd.add(userRoomRole);
            }
        }
        userRoomRoleRepository.saveAll(newUsersToAdd);
    }

    @Override
    public List<Room> findAll(long userId) {
        return roomRepository.findAllByUserId(userId);
    }
}