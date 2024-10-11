package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "room")
public class Room extends EntityBase {
    @Column(name = "room_number")
    public int roomNumber;

    @Column(name = "type")
    public String type;

    @Column(name = "price_per_night")
    public int pricePerNight;

    @Column(name = "is_available")
    public boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "id")
    public Hotel hotel;
}
