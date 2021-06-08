package ru.sunoplyaandesin.simplemessenger.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Message;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.domain.User;
import ru.sunoplyaandesin.simplemessenger.dto.MessageDTO;
import ru.sunoplyaandesin.simplemessenger.exception.MessageNotFoundException;
import ru.sunoplyaandesin.simplemessenger.exception.RoomNotFoundException;
import ru.sunoplyaandesin.simplemessenger.exception.UserNotFoundException;
import ru.sunoplyaandesin.simplemessenger.repository.MessageRepository;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;
import ru.sunoplyaandesin.simplemessenger.repository.UserRepository;
import ru.sunoplyaandesin.simplemessenger.service.MessageService;
import ru.sunoplyaandesin.simplemessenger.service.mapper.MessageMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final MessageMapper messageMapper;

    @Override
    public MessageDTO create(String text, long roomId, long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Message message = Message.builder()
                .createdDate(new Date())
                .room(room)
                .user(user)
                .text(text)
                .build();
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public MessageDTO find(long id) {
        return messageMapper.toDto(findById(id));
    }

    @Override
    public void delete(long id) {
        Message message = findById(id);
        messageRepository.delete(message);
    }

    @Override
    public void update(long id, String text) {
        Message message = findById(id);
        message.setText(text);
        message.setCreatedDate(new Date());
        messageRepository.save(message);
    }

    @Override
    public List<MessageDTO> findAll(long roomId) {
        List<Message> allByRoomId = messageRepository.findAllByRoomId(roomId);
        return allByRoomId.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAll(long roomId) {
        List<Message> allMessagesInRoom = messageRepository.findAllByRoomId(roomId);
        messageRepository.deleteAll(allMessagesInRoom);
    }

    private Message findById(long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }
}