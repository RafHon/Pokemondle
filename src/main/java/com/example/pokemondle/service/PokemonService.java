package com.example.pokemondle.service;

import com.example.pokemondle.model.Feedback;
import com.example.pokemondle.model.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public interface PokemonService {

    Optional<Pokemon> findById(Long Id);
    Pokemon findByName(String Name);
    List<Pokemon> findAll();
    Pokemon getRandomPokemon();
    Feedback compare(String pokemonName);
    Pokemon readRandomPokemon();

}

