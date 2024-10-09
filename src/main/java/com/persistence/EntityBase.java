package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EntityBase extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "increment")
    @JsonbProperty("id")
    private long id;

    public long getId() {
        return id;
    }
}
