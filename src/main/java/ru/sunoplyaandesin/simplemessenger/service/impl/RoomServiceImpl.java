package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.RoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRoleRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final RoomRoleRepository roomRoleRepository;

    @Override
    public boolean create(Room room, long userId) {
        try {
            User user = userRepository.findById(userId).get();
            room.setCreatedDate(new Date());
            room.setUser(user);
            roomRepository.save(room);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean deleteByTitle(String title) {
        try {
            Room room = roomRepository.findByTitle(title).get();
            roomRepository.deleteByTitle(title);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean rename(String title, String newTitle) {
        if (roomRepository.findByTitle(title).isPresent()) {
            Room room = roomRepository.findByTitle(title).get();
            room.setTitle(newTitle);
            roomRepository.save(room);
            return true;
        }
        return false;
    }

    @Override
    public boolean connectUser(String name, String roomTitle) {
        try {
            Room room = roomRepository.findByTitle(roomTitle).get();
            User user = userRepository.findByName(name).get();

            RoomRole roomRole = new RoomRole();
            roomRole.setRoomRole(RoomRoles.ROOM_USER);
            roomRole.setUser(user);
            roomRole.setRoom(room);
            roomRoleRepository.save(roomRole);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public void connectAll(String title) {
        Room room = roomRepository.findByTitle(title).get();
        List<User> allUsers = userRepository.findAll();
        List<RoomRole> roomRoles = new ArrayList<>();
        List<RoomRole> allByRoomId = roomRoleRepository.findAllByRoomId(room.getId());

        for (User user : allUsers) {
            RoomRole roomRole = new RoomRole();
            roomRole.setRoom(room);
            roomRole.setRoomRole(RoomRoles.ROOM_USER);
            roomRole.setUser(user);
            if (!allByRoomId.contains((roomRole))) {
                roomRoles.add(roomRole);
            }
        }
        roomRoleRepository.saveAll(roomRoles);
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }
}