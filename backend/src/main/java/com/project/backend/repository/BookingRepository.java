package com.project.backend.repository;

import com.project.backend.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookedRoom,Long> {


    List<BookedRoom> findByRoomId(Long roomId);

    Optional<Object> findByBookingConfirmationCode(String confirmationCode);
}
