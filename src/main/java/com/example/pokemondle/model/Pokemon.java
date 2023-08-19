package com.example.pokemondle.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="Pokemon")
@Getter
@Setter
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name="name")
    private String Name;

    @Column(name="generation")
    private Integer Generation;

    @Column(name="type1")
    private PokemonType Type1;

    @Column(name="type2")
    private PokemonType Type2;

    @Column(name="height")
    private Float Height;

    @Column(name="weight")
    private Float Weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return Name.equals(pokemon.Name);
    }

    @Override
    public int hashCode() {
        return Name.hashCode();
    }
}
