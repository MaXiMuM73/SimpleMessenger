package ru.sunoplyaandesin.simplemessenger.service;

import ru.sunoplyaandesin.simplemessenger.domain.Message;

public interface MessageService {
    boolean createMessage(Message message);
}
