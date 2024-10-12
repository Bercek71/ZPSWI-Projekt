package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import lombok.Getter;

@MappedSuperclass
public abstract class EntityBase extends PanacheEntityBase {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("id")
    private long id;


}
