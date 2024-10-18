package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel extends PanacheEntity {
    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    public Country country;

    @Transient
    @JsonbProperty("countryId")
    public Long countryId;
}
