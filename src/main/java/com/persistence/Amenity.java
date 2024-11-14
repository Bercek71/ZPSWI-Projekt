package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "amenity")
public class Amenity extends PanacheEntity {

    @Column(name = "name")
    @JsonbProperty("name")
    public String name;
}
