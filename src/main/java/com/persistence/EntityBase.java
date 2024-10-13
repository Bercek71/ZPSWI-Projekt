package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;

@MappedSuperclass
public abstract class EntityBase extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_sequence")
    @SequenceGenerator(
            name = "id_sequence",
            sequenceName = "id_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    private Long id;

    @JsonbProperty("id")
    public Long getId() {
        return id;
    }

    @JsonbTransient
    public void setId(Long id) {
        this.id = id;
    }


}
