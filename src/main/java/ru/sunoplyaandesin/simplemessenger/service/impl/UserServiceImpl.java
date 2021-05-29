package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.auth.JwtProvider;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.domain.roles.SystemRoles;
import ru.sunoplyaandesin.simplemessenger.exception.WrongPasswordException;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Override
    public boolean create(User user) {
        try {
            user.setSystemRole(SystemRoles.SYSTEM_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            User updateUser = userRepository.findByName(user.getName()).get();
            updateUser.setName(user.getName());
            updateUser.setPassword(user.getPassword());
            updateUser.setSystemRole(user.getSystemRole());
            userRepository.save(updateUser);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean delete(String name) {
        try {
            User user = userRepository.findByName(name).get();
            userRepository.delete(user);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public User findByName(String name) {

        return userRepository.findByName(name).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        return userRepository.findByNameAndPassword(name, password).orElseThrow(
                () -> new UsernameNotFoundException(name));
    }

    @Override
    public String authorize(User user) {
        User foundUser = userRepository.findByName(user.getName()).get();
        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new WrongPasswordException();
        }
        String token = jwtProvider.generateToken(user.getName());
        return token;
    }
}