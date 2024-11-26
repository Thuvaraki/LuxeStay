package com.project.backend.service;

import com.project.backend.exception.InternalServerException;
import com.project.backend.exception.ResourceNotFoundException;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes(); //Blob requires binary data in the form of a byte array.
            Blob photoBlob = new SerialBlob(photoBytes); //Creates a Blob object to represent the binary data in a way that can be stored in a database; SerialBlob is a standard Java implementation of the Blob interface.
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        List<String> RoomTypes = roomRepository.findDistinctRoomTypes();
//        System.out.println(RoomTypes);
        return RoomTypes;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> fetchedRooms = roomRepository.findAll();
        return fetchedRooms;
    }

    @Override
    public void deleteRoomById(Long roomId) {
        Optional<Room> theRoom = roomRepository.findById(roomId);

        if(theRoom.isPresent()){
            roomRepository.deleteById(roomId);
        }
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        if(theRoom.isEmpty()){
            throw new ResourceNotFoundException("Sorry,Room not found!");
        }
        Blob photoBlob = theRoom.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1,(int) photoBlob.length());
        }
        return null;
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = roomRepository.findById(roomId).get();
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServerException("Fail updating room");
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(roomRepository.findById(roomId).get());
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return roomRepository.findAvailableRoomsByDateAndType(checkInDate,checkOutDate,roomType);
    }
}
//getBytes(int pos, int length): This method is used to extract a portion of the blob data.
//In this case: pos = 1: Specifies the position (index) within the blob from where to start reading.
//In many database systems, blob data is indexed starting from 1.
//length = (int) photoBlob.length(): Specifies the length of data to be retrieved. Here, it attempts to read the entire length of the photoBlob.