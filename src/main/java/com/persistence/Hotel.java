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
    @JoinColumn(name = "address_id")
    public Address address;

    @Transient
    @JsonbProperty("addressId")
    public Long addressId;

    // NamedQuery usage in your repository or service class
    public static List<Hotel> findAvailableHotels(LocalDate checkIn, LocalDate checkOut, Integer guests, Long cityId) {
        return find("#Hotel.findAvailableHotels",
                Parameters.with("checkIn", checkIn)
                        .and("checkOut", checkOut)
                        .and("guests", guests)
                        .and("cityId", cityId))
                .list();
    }
}