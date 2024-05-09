package com.project.backend.service;

import com.project.backend.model.BookedRoom;
import com.project.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookedRoomRepository;
    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
       return bookedRoomRepository.findByRoomId(roomId);
    }
}
