package com.example.pokemondle.service;

import com.example.pokemondle.dao.PokemonRepository;
import com.example.pokemondle.model.Answer;
import com.example.pokemondle.model.Feedback;
import com.example.pokemondle.model.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class PokemonServiceImpl implements PokemonService {

    List<Pokemon> pokemonList;
    Pokemon randomPokemon;
    private final PokemonRepository pokemonRepository;
    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonList = findAll();
        this.randomPokemon = getRandomPokemon();
    }

    @Override
    public Optional<Pokemon> findById(Long id) {
        return pokemonRepository.findById(id);
    }

    @Override
    public List<Pokemon> findAll() {    //wywołanie po stronie frontu
        return pokemonRepository.findAll();
    }

    @Override
    public Pokemon getRandomPokemon() {
        Random random = new Random();
        int randomIdx = random.nextInt(pokemonList.size());
        this.randomPokemon = pokemonList.get(randomIdx);
        return this.randomPokemon;
    }

    @Override
    public Pokemon readRandomPokemon() {
        return randomPokemon;
    }
    public Pokemon findByName(String name) {
        logger.info("Searching for Pokemon with name: {}", name);
        List<Pokemon> allPokemons = pokemonRepository.findAll();

        int numThreads = Runtime.getRuntime().availableProcessors(); // Ilość dostępnych procesorów
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<Future<Pokemon>> futures = new ArrayList<>();

        // Podziel listę na podlisty
        List<List<Pokemon>> subLists = partitionList(allPokemons, numThreads);

        AtomicBoolean shutdownFlag = new AtomicBoolean(false);

        for (List<Pokemon> subList : subLists) {
            PokemonFinder pokemonFinder = new PokemonFinder(subList, name, logger, shutdownFlag);
            Future<Pokemon> future = executorService.submit(pokemonFinder);
            futures.add(future);
        }
        executorService.shutdown();

        try {
            for (Future<Pokemon> future : futures) {
                Pokemon pokemon = future.get();
                if (pokemon != null) {
                    return pokemon;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null; // Zwraca null, jeśli nie znaleziono pasującego pokemona
    }


    private List<List<Pokemon>> partitionList(List<Pokemon> pokemons, int numPartitions) {
        List<List<Pokemon>> partitions = new ArrayList<>();
        int partitionSize = (int) Math.ceil((double) pokemons.size() / numPartitions);
        for (int i = 0; i < pokemons.size(); i += partitionSize) {
            partitions.add(pokemons.subList(i, Math.min(i + partitionSize, pokemons.size())));
        }
        return partitions;
    }

    @Override
    public Feedback compare(String pokemonName) {

        Pokemon pokemon = findByName(pokemonName);
        //Pokemon pokemon = pokemonRepository.findByName(pokemonName);
        if (pokemon == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        if (this.randomPokemon.getName().equals(pokemon.getName())) {
            feedback.setCheckName(true);
            feedback.setCheckType1(true);
            feedback.setCheckType2(true);
            feedback.setCheckGeneration(Answer.Equal);
            feedback.setCheckHeight(Answer.Equal);
            feedback.setCheckWeight(Answer.Equal);
        } else {
            feedback.setCheckName(false);
            feedback.setCheckType1(this.randomPokemon.getType1().equals(pokemon.getType1()));
            feedback.setCheckType2(this.randomPokemon.getType2().equals(pokemon.getType2()));

            if (this.randomPokemon.getGeneration() == pokemon.getGeneration())
                feedback.setCheckGeneration(Answer.Equal);
            else if (pokemon.getGeneration() > this.randomPokemon.getGeneration()) {
                feedback.setCheckGeneration(Answer.Lower);
            } else if (pokemon.getGeneration() < this.randomPokemon.getGeneration()) {
                feedback.setCheckGeneration(Answer.Greater);
            }
            if (this.randomPokemon.getHeight() == pokemon.getHeight()) feedback.setCheckHeight(Answer.Equal);
            else if (pokemon.getHeight() > this.randomPokemon.getHeight()) {
                feedback.setCheckHeight(Answer.Lower);
            } else if (pokemon.getHeight() < this.randomPokemon.getHeight()) {
                feedback.setCheckHeight(Answer.Greater);
            }
            if (this.randomPokemon.getWeight() == pokemon.getWeight()) feedback.setCheckWeight(Answer.Equal);
            else if (pokemon.getWeight() > this.randomPokemon.getWeight()) {
                feedback.setCheckWeight(Answer.Lower);
            } else if (pokemon.getWeight() < this.randomPokemon.getWeight()) {
                feedback.setCheckWeight(Answer.Greater);
            }
        }
        return feedback;
    }

}
