package com.project.backend.controller;

import com.project.backend.ResponseDTO.RoomResponseDTO;
import com.project.backend.model.Room;
import com.project.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping(value = "/add-new-room", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomResponseDTO> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponseDTO response = new RoomResponseDTO(savedRoom.getId(),savedRoom.getRoomType(),savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getRoomTypes")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }

}