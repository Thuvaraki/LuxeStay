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
    @Column(name="Room_Id")
    private Long id;

    @Column(name="Room_Type")
    private String roomType;

    @Column(name="Room_Price")
    private BigDecimal roomPrice;

    @Column(name="Photo")
    @Lob //used to specify 'photo' is a large object type
//    Blob - Binary Large Object - store large binary data such as images, documents,
    private Blob photo;

    @Column(name="Is_Booked")
    private boolean isBooked = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //'cascade = CascadeType.ALL', it means that all operations like persisting, merging, removing, refreshing, and detaching are performed on the parent entity  should also be applied to its related child entities
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

        StringBuilder codeBuilder = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
