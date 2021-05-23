package ru.sunoplyaandesin.simplemessenger.service;

import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean createUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

//    @Override
//    public User findByName(String name) {
//        return userRepository.finByName(name);
//    }
}