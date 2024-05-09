package com.project.backend.service;

import com.project.backend.model.Room;
import com.project.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        System.out.println("hi");
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        System.out.println("room" +room);

        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            System.out.println("photoBytes" + photoBytes);
            Blob photoBlob = new SerialBlob(photoBytes);
            System.out.println("a");
            room.setPhoto(photoBlob);
            System.out.println("b");
        }
        return  roomRepository.save(room);
    }
}
