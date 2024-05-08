package com.project.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name="Booked_Room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Booking_Id")
    private Long bookingId;

    @Column(name="Check_In")
    private LocalDate checkInDate;

    @Column(name="Check_Out")
    private LocalDate checkOutDate;

    @Column(name="Guest_FullName")
    private String guestFullName;

    @Column(name="Guest_Email")
    private String guestEmail;

    @Column(name="Num_Of_Adults")
    private int NumOfAdults;

    @Column(name="Num_Of_Children")
    private int NumOfChildren;

    @Column(name="Total_Guest")
    private int totalNumOfGuest;

    @Column(name="Confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY) //FetchType.LAZY, the associated Room entity will be loaded lazily, meaning it will only be retrieved from the database when it's accessed for the first time
    @JoinColumn(name="Room_Id")
    private Room room;

    public void calculateTotalNumberOfGuest(){
        this.totalNumOfGuest = this.NumOfAdults + this.NumOfChildren;
    }

    public void setNumOfAdults(int numOfAdults) {
        NumOfAdults = numOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        NumOfChildren = numOfChildren;
        calculateTotalNumberOfGuest();
    }
}
