package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;
import ru.sunoplyaandesin.simplemessenger.exception.RoomNotFoundException;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserService userService;

    @Override
    public Room create(Room room, long userId) {
        User user = userService.find(userId);

        room.setCreatedDate(new Date());
        room.setUser(user);

        room.getUserRoomRoles()
                .add(UserRoomRole.builder()
                        .room(room)
                        .user(user)
                        .roomRole(RoomRoles.ROOM_OWNER)
                        .build()
        );

        return roomRepository.save(room);
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
        User user = userService.find(userId);

        Room room = find(roomId);

        room.getUserRoomRoles()
                .add(UserRoomRole.builder()
                        .room(room)
                        .user(user)
                        .roomRole(RoomRoles.ROOM_USER)
                        .build()
                );

        roomRepository.save(room);
    }

    @Override
    public void connectAll(long roomId) {
        List<User> allUsers = userService.findAll();
        for (User user : allUsers) {
            connect(user.getId(), roomId);
        }
    }

    @Override
    public List<Room> findAll(long userId) {
        return roomRepository.findAllByUserId(userId);
    }
}