package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
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
    public AppUser appUser;

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonbProperty("reservations")
    public List<Reservation> reservations = new ArrayList<>();
}
