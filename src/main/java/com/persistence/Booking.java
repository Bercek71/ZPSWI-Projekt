package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking extends PanacheEntity {
    @Column(name = "price_total")
    @JsonbProperty("priceTotal")
    public int priceTotal;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonbProperty("appUserId")
    public AppUser appUser;

    @Transient
    @JsonbProperty("userId")
    public Long userId;

    @Transient
    @JsonbProperty("reservations")
    public List<Reservation> reservations = new ArrayList<>();
}
