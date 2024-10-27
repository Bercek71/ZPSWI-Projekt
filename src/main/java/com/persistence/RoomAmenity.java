package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "room_amenity")
public class RoomAmenity extends PanacheEntity {

    @Column(name = "value")
    @JsonbProperty("value")
    public int value = 0;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    public Room room;

    @ManyToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    public Amenity amenity;

    public static List<Amenity> findAllAmenitiesForRoom(Long roomId) {
        return find("select ra.amenity from RoomAmenity ra where ra.room.id = ?1", roomId).list();
    }
}
