package com.project.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name="Booked_Room") //Marks a class as an entity, making it possible for Spring to map it to a database table.
@Data //part of the Lombok library, helps reduce boilerplate code. Automatically generate methods like getters, setters, toString(), equals(), hashCode()
@AllArgsConstructor //part of the Lombok library, automatically generate constructor
@NoArgsConstructor
public class BookedRoom {
    @Id //Marks the field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indicates that the value for the primary key will be automatically generated; strategy attribute specifies the generation strategy to be used.
//    strategy tells the database to automatically generate a value for the primary key column using an auto-increment featur
    @Column(name="Booking_Id")
    private Long bookingId; //achieving encapsulation

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
    private Room room; //creating an association between the current class and the Room entity

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
