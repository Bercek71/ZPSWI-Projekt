package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
@FilterDef(name = "Room.findOverlappingReservations", parameters = {
    @ParamDef(name = "start", type = LocalDate.class),
    @ParamDef(name = "end", type = LocalDate.class),
    @ParamDef(name = "roomId", type = long.class),
})
@NamedQuery(
    name = "Room.findOverlappingReservations",
    query = "select r from Reservation r " +
        "where r.room.id = :roomId " +
        "and (" +
        "(:start >= r.startDate and :start < r.endDate) or (:end > r.startDate and :end <= r.endDate)" +
        " or " +
        "(:start < r.startDate and :end > r.endDate)" +
        ")"
)
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

    public boolean isReservable(LocalDate start, LocalDate end) {
        return find("#Room.findOverlappingReservations",
            Parameters.with("start", start)
                      .and("end", end)
                      .and("roomId", this.id)).count() == 0;
    }
}
