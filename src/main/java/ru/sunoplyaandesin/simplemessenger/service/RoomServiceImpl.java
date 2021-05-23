package ru.sunoplyaandesin.simplemessenger.service;

import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public boolean create(Room room) {
        try {
            roomRepository.save(room);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean deleteByTitle(String title) {
        try {
            roomRepository.deleteByTitle(title);
            return true;
        } catch (RuntimeException runtimeException) {
            return false;
        }
    }

    @Override
    public Optional<Room> findByTitle(String title) {
        return roomRepository.findByTitle(title);
    }

    @Override
    public boolean renameRoom(String title, String newTitle) {
        if (roomRepository.findByTitle(title).isPresent()) {
            Room room = roomRepository.findByTitle(title).get();
            room.setTitle(newTitle);
            roomRepository.save(room);
            return true;
        }
        return false;
    }
}