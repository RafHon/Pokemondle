package com.example.pokemondle.service;

import com.example.pokemondle.model.Pokemon;
import java.util.List;
import java.util.concurrent.Callable;

public class ComparisonThread implements Callable<Pokemon> {

    List<Pokemon> pokemonList;
    String name;

    private static Object lock = new Object(); // Wspólny zamek dla wszystkich wątków
    private static volatile boolean stopThreads = false; // Flaga do zatrzymywania wątków

    public ComparisonThread(List<Pokemon> pokemonList, String name) {
        this.pokemonList = pokemonList;
        this.name = name;
    }

    @Override
    public Pokemon call() {
        synchronized (lock) {
            if (!stopThreads) {
                for (Pokemon pokemon : this.pokemonList) {
                    if (pokemon.getName().equals(this.name)) {
                        stopThreads = true; // Zatrzymujemy pozostałe wątki
                        return pokemon;
                    }
                }
            }
        }
        return null;
    }
}
