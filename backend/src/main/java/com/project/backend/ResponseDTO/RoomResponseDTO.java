package com.project.backend.ResponseDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponseDTO {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String photo; // String representation can be easily managed using standard JSON or other serialization techniques.
    private boolean isBooked;
    private List<BookedRoomResponseDTO> bookings;

    public RoomResponseDTO(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponseDTO(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes, boolean isBooked, List<BookedRoomResponseDTO> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.photo = photoBytes != null ? Base64.getEncoder().encodeToString(photoBytes) : null;
        this.isBooked = isBooked;
        this.bookings = bookings;
    }
}
