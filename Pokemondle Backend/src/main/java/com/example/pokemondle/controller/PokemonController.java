package com.example.pokemondle.controller;

import com.example.pokemondle.model.Feedback;
import com.example.pokemondle.model.Pokemon;
import com.example.pokemondle.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/pokemondle")
public class PokemonController {
    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/id/{id}")
    public Optional<Pokemon> getById(@PathVariable Long id) {
        return pokemonService.findById(id);
    }

    @GetMapping("/name/{name}")
    public Pokemon getByName(@PathVariable String name) {
        return pokemonService.findByName(name);
    }

    @GetMapping("/feedback/{name}")
    public Feedback getFeedback(@PathVariable String name) {
        return pokemonService.compare(name);
    }

    @GetMapping("/all")
    public List<Pokemon> getAll() {
        return pokemonService.findAll();
    }

    @GetMapping("/random")
    public Pokemon getRandom(){
        return pokemonService.getRandomPokemon();
    }

    @GetMapping("/read")
    public Pokemon readRandomPokemon(){
        return pokemonService.readRandomPokemon();
    }
}
