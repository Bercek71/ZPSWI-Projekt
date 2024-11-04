package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "city")
public class City extends PanacheEntity {
    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @Column(name = "zip_code")
    @JsonbProperty("zipCode")
    public int zipCode;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    public Country country;
}
