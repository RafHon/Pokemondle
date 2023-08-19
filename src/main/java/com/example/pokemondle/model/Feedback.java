package com.example.pokemondle.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Feedback {
    boolean checkName;
    Answer checkGeneration;
    boolean checkType1;
    boolean checkType2;
    Answer checkHeight;
    Answer checkWeight;
}
