package com.project.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Entity(name="Room")
@Data
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;

    private BigDecimal roomPrice;

    @Lob //used to specify 'photo' is a large object type
//    Blob - Binary Large Object - store large binary data such as images, documents,
    private Blob photo;

    private boolean isBooked = false;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY, cascade = CascadeType.ALL) //'cascade = CascadeType.ALL', it means that all operations like persisting, merging, removing, refreshing, and detaching are performed on the parent entity  should also be applied to its related child entities
    private List<BookedRoom> bookings;

    public Room() {
        this.bookings = new ArrayList<>();
    }

    //    This constructor is automatically called when creating a new Room object using new Room()
//    'this.bookings = new ArrayList<>();' This line creates a new empty ArrayList and assigns it to the bookings field of the Room object.
//    Without this line, bookings would be null when creating a new Room object. If bookings is null and when we try to use it  a NullPointerException will be thrown.

    public void addBooking(BookedRoom booking){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = generateRandomBookingCode(10);
        booking.setBookingConfirmationCode(bookingCode);
    }

    private String generateRandomBookingCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder codeBuilder = new StringBuilder(); // construct the booking code character by character

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            codeBuilder.append(randomChar);
        }
//nextInt(int bound) is a method from Java's Random class.
//It generates a random integer in the range [0, bound), where: 0 is inclusive and bound is exclusive.

        return codeBuilder.toString();
    }
}
