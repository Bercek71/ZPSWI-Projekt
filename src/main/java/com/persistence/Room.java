package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room extends PanacheEntity {
    @Column(name = "room_number")
    @JsonbProperty("roomNumber")
    public int roomNumber;

    @Column(name = "max_guests")
    @JsonbProperty("maxGuests")
    public int maxGuests;

    @Column(name = "type")
    @JsonbProperty("type")
    public String type;

    @Column(name = "price_per_night")
    @JsonbProperty("pricePerNight")
    public int pricePerNight;

    @Column(name = "is_available")
    @JsonbProperty("isAvailable")
    public boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    public Hotel hotel;

    @Transient
    @JsonbProperty("hotelId")
    public Long hotelId;

    @ManyToMany
    @JoinTable(
            name = "room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    public Set<Amenity> amenities = new HashSet<>();
}
