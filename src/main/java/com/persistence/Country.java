package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "country")
public class Country extends PanacheEntity {
    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @Column(name = "iso_code")
    @JsonbProperty("isoCode")
    public String isoCode;
}
