package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Message;

import java.util.List;

public interface MessageService {
    boolean create(Message message, String roomTitle, long userId);

    boolean delete(long id);

    boolean update(long id, String text);

    List<Message> findAll(long roomId);
}