package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sunoplyaandesin.simplemessenger.domain.RoomRole;

import java.util.List;

@Repository
public interface RoomRoleRepository extends CrudRepository<RoomRole, Long> {

    List<RoomRole> findAllByRoomId(Long id);
}