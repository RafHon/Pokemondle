package com.example.pokemondle.service;

import com.example.pokemondle.dao.PokemonRepository;
import com.example.pokemondle.model.Answer;
import com.example.pokemondle.model.Feedback;
import com.example.pokemondle.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

@Service
public class PokemonServiceImpl implements PokemonService {

    List<Pokemon> pokemonList;
    Pokemon randomPokemon;


    private final PokemonRepository pokemonRepository;

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
    public Pokemon findByName(String name) {
        return pokemonRepository.findByName(name);
    }

    @Override
    public List<Pokemon> findAll() {    //wywołanie po stronie frontu
        return pokemonRepository.findAll();
    }

    @Override
    public Pokemon getRandomPokemon() {
        Random random = new Random();
        int randomIdx = random.nextInt(pokemonList.size());

        return pokemonList.get(randomIdx);
    }

    public List<List<Pokemon>> splitIntoSublists(List<Pokemon> inputList, int sublistSize) {
        if (inputList.size() <= sublistSize) {
            List<List<Pokemon>> singleList = new ArrayList<>();
            singleList.add(inputList);
            return singleList;
        }

        List<List<Pokemon>> sublists = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i += sublistSize) {
            int endIndex = Math.min(i + sublistSize, inputList.size());
            sublists.add(inputList.subList(i, endIndex));
        }
        return sublists;
    }


    @Override
    public Pokemon splitOnThreads(String name) {
        List<List<Pokemon>> sublists = splitIntoSublists(this.pokemonList, 100);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Callable<Pokemon>> tasks = new ArrayList<>();

        for (List<Pokemon> list : sublists) {
            tasks.add(new ComparisonThread(list, name));
        }
        Pokemon result = null;

        try {
            List<Future<Pokemon>> futures = executor.invokeAll(tasks);
            for (Future<Pokemon> future : futures) {
                if (future.get() != null) {
                    result = future.get();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
        return result;

    }

    @Override
    public Feedback compare(String pokemonName) {

        Pokemon pokemon = splitOnThreads(pokemonName);
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
            else if (pokemon.getGeneration() < this.randomPokemon.getGeneration()) {
                feedback.setCheckGeneration(Answer.Lower);
            } else if (pokemon.getGeneration() > this.randomPokemon.getGeneration()) {
                feedback.setCheckGeneration(Answer.Greater);
            }
            if (this.randomPokemon.getHeight() == pokemon.getHeight()) feedback.setCheckHeight(Answer.Equal);
            else if (pokemon.getHeight() < this.randomPokemon.getHeight()) {
                feedback.setCheckHeight(Answer.Lower);
            } else if (pokemon.getHeight() > this.randomPokemon.getHeight()) {
                feedback.setCheckHeight(Answer.Greater);
            }
            if (this.randomPokemon.getWeight() == pokemon.getWeight()) feedback.setCheckWeight(Answer.Equal);
            else if (pokemon.getWeight() < this.randomPokemon.getWeight()) {
                feedback.setCheckWeight(Answer.Lower);
            } else if (pokemon.getWeight() > this.randomPokemon.getWeight()) {
                feedback.setCheckWeight(Answer.Greater);
            }
        }
        return feedback;
    }

}
