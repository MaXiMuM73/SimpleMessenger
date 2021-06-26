package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;
import ru.sunoplyaandesin.simplemessenger.domain.roles.RoomRoles;

public interface UserRoomRoleRepository extends CrudRepository<UserRoomRole, Long> {

//    UserRoomRole findUserRoomRoleByRoomRoleAndRoom(RoomRoles roomRole, Room room);

    UserRoomRole findUserRoomRoleByUserIdAndRoomId(long userId, long roomId);

}
