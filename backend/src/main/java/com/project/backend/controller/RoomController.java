package com.project.backend.controller;

import com.project.backend.Response.BookingResponse;
import com.project.backend.Response.RoomResponse;
import com.project.backend.exception.PhotoRetrievalException;
import com.project.backend.model.BookedRoom;
import com.project.backend.model.Room;
import com.project.backend.service.BookingService;
import com.project.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/add-new-room", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(),savedRoom.getRoomType(),savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);

    }


    @GetMapping(value = "/getRoomTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getRoomTypes() {
        List<String> RoomTypes = roomService.getAllRoomTypes();
        return ResponseEntity.ok(RoomTypes);
    }

    @GetMapping("/getAllRooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> fetchedRooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : fetchedRooms) {
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponses.add(roomResponse);
            }
        return ResponseEntity.ok(roomResponses);
        }

    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
        List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking->new BookingResponse(booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode()))
                .toList();
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new RoomResponse(room.getId(),
                        room.getRoomType(),
                        room.getRoomPrice(),
                        photoBytes,
                        room.isBooked(),
                        bookingInfo);
    }

    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable("roomId") Long roomId){
        roomService.deleteRoomById(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}