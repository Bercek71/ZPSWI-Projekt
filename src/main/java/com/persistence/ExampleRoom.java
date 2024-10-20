package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;


/**
     * Example JPA entity defined as a Panache Entity.
     * An ID field of Long type is provided, if you want to define your own ID field extends <code>PanacheEntityBase</code> instead.
     *
     * This uses the active record pattern, you can also use the repository pattern instead:
     * .
     *
     * Usage (more example on the documentation)
     *
     * {@code
     *     public void doSomething() {
     *         MyEntity entity1 = new MyEntity();
     *         entity1.field = "field-1";
     *         entity1.persist();
     *
     *         List<MyEntity> entities = MyEntity.listAll();
     *     }
     * }
     */
    @Table(name="example_room")
    @Entity
    public class ExampleRoom extends PanacheEntity {
        @Column(name = "name")
        @JsonbProperty("name")
        public String name;

        @Column(name = "location")
        @JsonbProperty("location")
        @Getter
        private String location;

        @Column(name = "price")
        @JsonbProperty("price")
        private int price;

        @Column(name = "capacity")
        @JsonbProperty("capacity")
        private int capacity;
    }
