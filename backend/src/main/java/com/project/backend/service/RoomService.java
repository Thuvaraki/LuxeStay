package com.project.backend.service;

import com.project.backend.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface RoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    void deleteRoomById(Long roomId);

//    byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;
}
