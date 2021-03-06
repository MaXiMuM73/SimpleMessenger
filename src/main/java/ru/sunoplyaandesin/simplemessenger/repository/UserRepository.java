package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sunoplyaandesin.simplemessenger.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);

    List<User> findAll();
}