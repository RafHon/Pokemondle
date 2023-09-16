package com.example.pokemondle.service;

import com.example.pokemondle.model.Pokemon;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class PokemonFinder implements Callable<Pokemon> {
    private static final AtomicLong THREAD_COUNTER = new AtomicLong(0);
    private final List<Pokemon> subList;
    private final String name;
    private final Logger logger;
    private final AtomicBoolean shutdownFlag;
    private final long id;

    public PokemonFinder(List<Pokemon> subList, String name, Logger logger, AtomicBoolean shutdownFlag) {
        this.subList = subList;
        this.name = name;
        this.logger = logger;
        this.shutdownFlag = shutdownFlag;
        this.id = THREAD_COUNTER.incrementAndGet();
    }

    @Override
    public Pokemon call() throws Exception {
        //int randomDelay = ThreadLocalRandom.current().nextInt(500, 1001); // Losowa liczba od 1s do 3s
        //Thread.sleep(randomDelay);

        if (shutdownFlag.get()) {
            logger.info("Exiting thread " + " " + id);
            return null;
        }

        logger.info("Thread " + id + " started, searching in a subList...");
        for (Pokemon pokemon : subList) {
            if (pokemon.getName().equalsIgnoreCase(name)) {
                logger.info("Thread " + id + " found matching Pokemon: {}", pokemon.getName());
                shutdownFlag.set(true);
                return pokemon;
            }
        }

        return null;
    }
}
