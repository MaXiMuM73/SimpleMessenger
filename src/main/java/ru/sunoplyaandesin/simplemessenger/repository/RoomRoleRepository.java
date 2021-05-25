package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sunoplyaandesin.simplemessenger.domain.RoomRole;

public interface RoomRoleRepository extends CrudRepository<RoomRole, Long> {
}