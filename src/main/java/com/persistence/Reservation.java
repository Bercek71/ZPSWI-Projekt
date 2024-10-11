package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation extends EntityBase {
    @Column(name = "start_date")
    @JsonbProperty("startDate")
    public LocalDate startDate;

    @Column(name = "end_date")
    @JsonbProperty("endDate")
    public LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonbProperty("roomId")
    public Room room;


    @Enumerated(EnumType.STRING)
    @JsonbProperty("status")
    public ReservationStatus status;

    @Column(name = "price")
    @JsonbProperty("price")
    public int price;

    @Column(name = "paid_at")
    @JsonbProperty("paidAt")
    public LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonbProperty("bookingId")
    public Booking booking;


    public enum ReservationStatus {
        PENDING,
        ACCEPTED,
        CANCELLED,
        PAID,
        PAYMENT_ACCEPTED,
        ENDED,
        EXPIRED,

    }
}
