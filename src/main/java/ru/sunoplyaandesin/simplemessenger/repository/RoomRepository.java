package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sunoplyaandesin.simplemessenger.domain.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    void deleteByTitle(String title);

    Optional<Room> findByTitle(String title);
}