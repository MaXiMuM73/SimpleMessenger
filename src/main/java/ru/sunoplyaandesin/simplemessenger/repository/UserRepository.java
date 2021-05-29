package ru.sunoplyaandesin.simplemessenger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sunoplyaandesin.simplemessenger.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);

    Set<User> findAllById(Long id);

    List<User> findAll();

    Optional<User> findByNameAndPassword(String name, String password);
}