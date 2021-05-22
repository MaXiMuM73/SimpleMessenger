package ru.sunoplyaandesin.simplemessenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean createUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }
}
