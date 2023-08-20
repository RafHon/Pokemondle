package com.example.pokemondle.controller;

import com.example.pokemondle.model.Feedback;
import com.example.pokemondle.model.Pokemon;
import com.example.pokemondle.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/pokemondle")
public class PokemonController {
    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/id/{id}")
    public Optional<Pokemon> getByName(@PathVariable Long id) {
        return pokemonService.findById(id);
    }

    @GetMapping("/name/{name}")
    public Pokemon getByName(@PathVariable String name) {
        return pokemonService.findByName(name);
    }

    @GetMapping("/{name}/feedback")
    public Feedback getFeedback(@PathVariable String name) {
        return (pokemonService.compare(name));
    }

}
