package com.example.pokemondle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Struct;


@Entity
@Table(name = "Pokemon")
@Getter
@Setter
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "generation")
    private int generation;

    @Column(name = "type1")
    private String type1;

    @Column(name = "type2")
    private String type2;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return name.equals(pokemon.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getType2() {
        return type2 != null ? type2 : "";
    }
}
