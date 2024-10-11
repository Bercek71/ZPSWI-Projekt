package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EntityBase extends PanacheEntityBase {

    @Getter
    @Id
    @GeneratedValue(generator = "increment")
    @JsonbProperty("id")
    private long id;

}
