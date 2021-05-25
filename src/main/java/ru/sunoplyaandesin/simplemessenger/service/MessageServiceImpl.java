package ru.sunoplyaandesin.simplemessenger.service;

import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

    private MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public boolean createMessage(Message message) {
        try {
            messageRepository.save(message);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }
}
