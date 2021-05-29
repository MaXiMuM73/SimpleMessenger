package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sunoplyaandesin.simplemessenger.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAllByRoomId(Long id);
}