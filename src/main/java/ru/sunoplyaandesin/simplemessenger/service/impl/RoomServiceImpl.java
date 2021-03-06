package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;
import ru.sunoplyaandesin.simplemessenger.dto.RoomDTO;
import ru.sunoplyaandesin.simplemessenger.exception.UserNotFoundException;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.mapper.RoomMapper;
import ru.sunoplyaandesin.simplemessenger.exception.RoomNotFoundException;
import ru.sunoplyaandesin.simplemessenger.exception.UserRoomRoleNotFoundException;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRoomRoleRepository;
import ru.sunoplyaandesin.simplemessenger.service.RoomService;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final RoomMapper roomMapper;

    private final UserRoomRoleRepository userRoomRoleRepository;

    @Override
    public RoomDTO create(RoomDTO roomDTO, long userId) {
        User user = userService.findUser(userId);

        Room room = roomMapper.toRoom(roomDTO);

        room.setCreatedDate(new Date());
        room.setUser(user);

        room.getUserRoomRoles()
                .add(UserRoomRole.builder()
                        .room(room)
                        .user(user)
                        .roomRole(RoomRoles.ROOM_OWNER)
                        .availableLoginTime(new Date())
                        .build()
                );
        roomRepository.save(room);

        return roomMapper.toDto(room);
    }

    @Override
    public RoomDTO find(long id) {
        return roomMapper.toDto(findById(id));
    }

    @Override
    public RoomDTO find(String title) {
        return roomMapper.toDto(findByTitle(title));
    }

    @Override
    public boolean delete(long id, long userId) {
        User user = userService.findUser(userId);

        Room room = findById(id);

        UserRoomRole userRoomRole = getUserRoomRole(user, room);

        if (userRoomRole.getRoomRole().equals(RoomRoles.ROOM_OWNER) |
                userRoomRole.getRoomRole().equals(RoomRoles.ROOM_ADMINISTRATOR)) {
            roomRepository.delete(room);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(String roomTitle, long userId) {
        User user = userService.findUser(userId);

        Room room = findByTitle(roomTitle);

        UserRoomRole userRoomRole = getUserRoomRole(user, room);

        if (userRoomRole.getRoomRole().equals(RoomRoles.ROOM_OWNER) |
                userRoomRole.getRoomRole().equals(RoomRoles.ROOM_ADMINISTRATOR)) {
            roomRepository.delete(room);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean rename(long id, String newTitle, long userId) {
        Room room = findById(id);

        User user = userService.findUser(userId);

        UserRoomRole userRoomRole = getUserRoomRole(user, room);

        if (userRoomRole.getRoomRole().equals(RoomRoles.ROOM_ADMINISTRATOR) |
                userRoomRole.getRoomRole().equals(RoomRoles.ROOM_OWNER)) {
            room.setTitle(newTitle);
            roomRepository.save(room);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean rename(long userId, String roomTitle, String newTitle) {
        Room room = findByTitle(roomTitle);

        User user = userService.findUser(userId);

        UserRoomRole userRoomRole = getUserRoomRole(user, room);

        if (userRoomRole.getRoomRole().equals(RoomRoles.ROOM_ADMINISTRATOR) |
                userRoomRole.getRoomRole().equals(RoomRoles.ROOM_OWNER)) {
            room.setTitle(newTitle);
            roomRepository.save(room);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean connect(long userIdToConnect, long roomId) {
        User userToConnect = userService.findUser(userIdToConnect);

        Room room = findById(roomId);

        UserRoomRole userRoomRole = userToConnect.getUserRoomRoles()
                .stream()
                .filter((role -> role.getRoom().equals(room)))
                .findAny()
                .orElse(null);

        if (userRoomRole != null) {
            Date banTime = userRoomRole.getAvailableLoginTime();
            Date now = new Date();
            if (now.compareTo(banTime) < 0) {
                return false;
            }
        }

        room.getUserRoomRoles()
                .add(UserRoomRole.builder()
                        .room(room)
                        .user(userToConnect)
                        .roomRole(RoomRoles.ROOM_USER)
                        .availableLoginTime(new Date())
                        .build());


        roomRepository.save(room);
        return true;
    }

    @Override
    public boolean connect(long userIdToConnect, String roomTitle) {
        User userToConnect = userService.findUser(userIdToConnect);

        Room room = findByTitle(roomTitle);

//        UserRoomRole userRoomRole = getUserRoomRole(userToConnect, room);

        UserRoomRole userRoomRole = userToConnect.getUserRoomRoles()
                .stream()
                .filter((role -> role.getRoom().equals(room)))
                .findAny()
                .orElse(null);

        if (userRoomRole != null) {
            Date banTime = userRoomRole.getAvailableLoginTime();
            Date now = new Date();
            if (now.compareTo(banTime) < 0) {
                return false;
            }
        }

        room.getUserRoomRoles()
                .add(UserRoomRole.builder()
                        .room(room)
                        .user(userToConnect)
                        .roomRole(RoomRoles.ROOM_USER)
                        .availableLoginTime(new Date())
                        .build());

        roomRepository.save(room);
        return true;
    }

    @Override
    public boolean connect(String userName, String roomTitle) {
        User userToConnect = userService.findUser(userName);

        Room room = findByTitle(roomTitle);

        UserRoomRole userToConnectRoomRole = userToConnect.getUserRoomRoles()
                .stream()
                .filter((role -> role.getRoom().equals(room)))
                .findAny()
                .orElse(null);

        if (userToConnectRoomRole != null) {
            Date banTime = userToConnectRoomRole.getAvailableLoginTime();
            Date now = new Date();
            if (now.compareTo(banTime) < 0) {
                return false;
            }
        }

        room.getUserRoomRoles()
                .add(UserRoomRole.builder()
                        .room(room)
                        .user(userToConnect)
                        .roomRole(RoomRoles.ROOM_USER)
                        .availableLoginTime(new Date())
                        .build());

        roomRepository.save(room);
        return true;
    }

    @Override
    public void connectAll(long roomId, long userId) {
        List<User> allUsers = userService.findAllUsers();

        for (User user : allUsers) {
            connect(user.getId(), roomId);
        }
    }

    @Override
    public boolean disconnect(long roomId, long userId) {
        Room room = findById(roomId);

        User user = userService.findUser(userId);

        UserRoomRole userRoomRole = user.getUserRoomRoles()
                .stream().filter(role -> role.getRoom().equals(room))
                .findAny().orElse(null);

        if (userRoomRole == null) {
            return false;
        }

        user.getUserRoomRoles().remove(userRoomRole);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean disconnect(String roomTitle, String userNameToDisconnect,
                              long banTime, long userId) {
        Room room = findByTitle(roomTitle);

        User userToDisconnect = userService.findUser(userNameToDisconnect);

        User user = userService.findUser(userId);

        UserRoomRole userRoomRole = getUserRoomRole(user, room);

        if ((userRoomRole.getRoomRole().equals(RoomRoles.ROOM_USER)) |
                (userRoomRole.getRoomRole().equals(RoomRoles.ROOM_MODERATOR))
        ) return false;

        if (banTime != 0) {
            Date ban = DateUtils.addSeconds(new Date(), (int) banTime * 60);
            UserRoomRole roleUserToBan = room.getUserRoomRoles().stream()
                    .filter(u -> u.getUser().equals(userToDisconnect))
                    .findAny()
                    .orElseThrow(() -> new UserNotFoundException(userNameToDisconnect));
            roleUserToBan.setRoomRole(RoomRoles.ROOM_BLOCKED_USER);
            roleUserToBan.setAvailableLoginTime(ban);
            room.getUserRoomRoles().add(roleUserToBan);
            roomRepository.save(room);
        } else {
            room.getUserRoomRoles()
                    .remove(UserRoomRole.builder()
                            .room(room)
                            .user(userToDisconnect)
                            .build());
            roomRepository.save(room);
            //TODO
            userToDisconnect.getUserRoomRoles()
                    .remove(UserRoomRole.builder()
                            .room(room)
                            .user(userToDisconnect)
                            .build());
            userRepository.save(userToDisconnect);
        }
        return true;
    }

    @Override
    public List<RoomDTO> findAll(long userId) {
        return roomRepository.findAllByUserId(userId)
                .stream().map(
                        roomMapper::toDto).collect(Collectors.toList());
    }

    private UserRoomRole getUserRoomRole(User user, Room room) {
        return user.getUserRoomRoles()
                .stream().filter(role -> role.getRoom().equals(room))
                .findAny().orElse(null);
    }

    private Room findById(long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    private Room findByTitle(String title) {
        return roomRepository.findByTitle(title)
                .orElseThrow(() -> new RoomNotFoundException(title));
    }
}