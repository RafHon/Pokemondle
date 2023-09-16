import { Component, OnInit } from '@angular/core';
import { Pokemon } from './pokemon';
import { Answer, Feedback } from '../feedback/feedback';
import { PokemonService } from '../services/pokemon.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pokemondle',
  templateUrl: './pokemondle.component.html',
  styleUrls: ['./pokemondle.component.css']
})
export class PokemondleComponent implements OnInit {

  searchResults: Feedback[] = [];
  searchinputs: string[] = [];
  randomPokemon?: Pokemon;
  feedback?: Feedback;
  i: number = 0;
  areYouWinningSon: boolean = false;
  isStarted: boolean = false;
  enum: typeof Answer = Answer;


  constructor(
    private pokemonService: PokemonService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.pokemonService.getRandom().subscribe((randomPokemon) => {
      this.randomPokemon = randomPokemon;
      console.log(randomPokemon);
    });

  }

  search(text: string) {
    this.pokemonService.getFeedback(text).subscribe(data => {
      if(data !== null){
        this.feedback = data;
        this.searchResults.push(data);
        this.searchinputs.push(text);
      }
    });
  }

  testMe(): void{
    this.pokemonService.getRandom().subscribe((randomPokemon) => {
      this.randomPokemon = randomPokemon;
    });
  }
}
