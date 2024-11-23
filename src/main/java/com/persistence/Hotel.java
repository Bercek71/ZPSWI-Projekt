package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "hotel")
@FilterDef(name = "Hotel.findAvailableHotels", parameters = {
        @ParamDef(name = "checkIn", type = LocalDate.class),
        @ParamDef(name = "checkOut", type = LocalDate.class),
        @ParamDef(name = "guests", type = Integer.class),
        @ParamDef(name = "cityId", type = Long.class)
})
@Filter(name = "Hotel.findAvailableHotels", condition = "r.max_guests >= :guests and h.address.city_id = :cityId")
@NamedQuery(
        name = "Hotel.findAvailableHotels",
        query = "select distinct h from Hotel h join Room r on h.id = r.hotel.id " +
                "where r.maxGuests >= :guests and h.address.city.id = :cityId " +
                "and not exists (" +
                "    select res from Reservation res " +
                "    where res.room.id = r.id " +
                "    and (res.startDate < :checkOut and res.endDate > :checkIn)" +
                ")"
)
public class Hotel extends PanacheEntity {

    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    public Address address;

    @Transient
    @JsonbProperty("addressId")
    public Long addressId;


    @ManyToMany
    @JoinTable(
            name = "user_hotel", // Name of the join table
            joinColumns = @JoinColumn(name = "hotel_id"), // Foreign key for Hotel
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key for User
    )
    List<AppUser> receptionists;

    // NamedQuery usage in your repository or service class
    public static List<Hotel> findAvailableHotels(LocalDate checkIn, LocalDate checkOut, Integer guests, Long cityId) {
        return find("#Hotel.findAvailableHotels",
                Parameters.with("checkIn", checkIn)
                        .and("checkOut", checkOut)
                        .and("guests", guests)
                        .and("cityId", cityId))
                .list();
    }

    public static List<Room> findAllRooms(Long hotelId){
        List<Object[]> results = getEntityManager()
                .createQuery("select r.id, r.isAvailable, r.maxGuests, r.pricePerNight, r.roomNumber, r.type " +
                        "from Room r where r.hotel.id = :hotelId", Object[].class)
                .setParameter("hotelId", hotelId)
                .getResultList();

        return results.stream().map(row -> {
            Room room = new Room();
            room.id = (Long) row[0];
            room.isAvailable = (Boolean) row[1];
            room.maxGuests = (Integer) row[2];
            room.pricePerNight = (Integer) row[3];
            room.roomNumber = (Integer) row[4];
            room.type = (String) row[5];
            return room;
        }).toList();
    }
}