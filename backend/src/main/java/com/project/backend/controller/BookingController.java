package com.project.backend.controller;

import com.project.backend.Response.BookingResponse;
import com.project.backend.Response.RoomResponse;
import com.project.backend.exception.InvalidBookingRequestException;
import com.project.backend.exception.ResourceNotFoundException;
import com.project.backend.model.BookedRoom;
import com.project.backend.model.Room;
import com.project.backend.service.BookingService;
import com.project.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "http://localhost:3000")
 public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/getAllBookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return  ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try {
            BookedRoom booking = bookingService.getBookingByConfirmationCode(confirmationCode);
            BookingResponse  bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }
        catch(ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/saveBooking/{roomId}")
    public ResponseEntity<?> saveBooking(@PathVariable(name = "roomId") Long roomId,
                                         @RequestBody BookedRoom bookingRequest){
        try{
            System.out.println("roomId"+roomId);
            System.out.println("bookingRequest"+bookingRequest);
            String confirmationCode = bookingService.saveBooking(roomId,bookingRequest);
            return ResponseEntity.ok("Room booked successfully! Your booking confirmation code is : "+confirmationCode);
        }
        catch(InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancelBooking/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(),booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(), room);
            }
}
