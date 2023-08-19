package com.example.pokemondle.controller;

import com.example.pokemondle.model.Feedback;
import com.example.pokemondle.model.Pokemon;
import com.example.pokemondle.service.PokemonService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/pokemondle")
public class PokemonController {
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{id}")
    public Optional<Pokemon> getByName(Long id) {
        return pokemonService.findById(id);
    }

    @GetMapping("/{name}")
    public Pokemon getByName(String name) {
        return pokemonService.findByName(name);
    }

    @GetMapping("/{name}/feedback")
    public Feedback getFeedback(String name) {
        return (pokemonService.compare(name));
    }

}
