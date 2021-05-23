package ru.sunoplyaandesin.simplemessenger.service;

import org.springframework.stereotype.Service;
import ru.sunoplyaandesin.simplemessenger.domain.Room;
import ru.sunoplyaandesin.simplemessenger.repository.RoomRepository;

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
}
