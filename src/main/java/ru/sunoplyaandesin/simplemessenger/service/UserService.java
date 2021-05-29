package ru.sunoplyaandesin.simplemessenger.service;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sunoplyaandesin.simplemessenger.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean create(User user);

    boolean update(User user);

    boolean delete(String name);

    User findByName(String name);

    List<User> getAllUsers();

    User findByNameAndPassword(String name, String password);

    String authorize(User user);
}