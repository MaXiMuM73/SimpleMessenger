package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sunoplyaandesin.simplemessenger.domain.UserRoomRole;

import java.util.Set;

@Repository
public interface UserRoomRoleRepository extends CrudRepository<UserRoomRole, Long> {

    Set<UserRoomRole> findAllByRoomId(Long id);
}