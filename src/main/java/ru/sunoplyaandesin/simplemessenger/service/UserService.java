package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.User;

import java.util.List;


public interface UserService {

    User create(User user);

    User find(long id);

    User findByName(String name);

    void update(User user);

    void delete(String name);

    List<User> findAll();

    User findByNameAndPassword(String name, String password);

    String authorize(User user);
}