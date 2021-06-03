package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Message;

import java.util.List;

public interface MessageService {

    Message create(String text, long roomId, long userId);

    Message find(long id);

    void delete(long id);

    void update(long id, String text);

    List<Message> findAll(long roomId);

    void deleteAll(long roomId);
}