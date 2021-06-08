package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    MessageDTO create(String text, long roomId, long userId);

    MessageDTO find(long id);

    void delete(long id);

    void update(long id, String text);

    List<MessageDTO> findAll(long roomId);

    void deleteAll(long roomId);
}