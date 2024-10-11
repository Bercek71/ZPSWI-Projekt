package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation extends EntityBase {
    @Column(name = "start_date")
    public LocalDate startDate;

    @Column(name = "end_date")
    public LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room room;

    @ManyToOne
    @JoinColumn(name = "status_id")
    public Status status;

    @Column(name = "price")
    public int price;

    @Column(name = "paid_at")
    public LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "id")
    public Booking booking;
}
