package ru.sunoplyaandesin.simplemessenger.service.impl;

import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.repository.MessageRepository;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRoleRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.MessageService;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private RoomRepository roomRepository;

    private RoomRoleRepository roomRoleRepository;

    public MessageServiceImpl(MessageRepository messageRepository,
                              UserRepository userRepository,
                              RoomRepository roomRepository,
                              RoomRoleRepository roomRoleRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.roomRoleRepository = roomRoleRepository;
    }

    @Override
    public boolean create(Message message, String roomTitle, long userId) {
        try {
            Room room = roomRepository.findByTitle(roomTitle).get();

            User user = userRepository.findById(userId).get();

            message.setCreatedDate(new Date());
            message.setRoom(room);
            message.setUser(user);
            messageRepository.save(message);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            Message message = messageRepository.findById(id).get();
            messageRepository.delete(message);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean update(long id, String text) {
        try {
            Message message = messageRepository.findById(id).get();
            message.setText(text);
            message.setCreatedDate(new Date());
            messageRepository.save(message);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public List<Message> findAll(long roomId) {
        return messageRepository.findAllByRoomId(roomId);
    }
}