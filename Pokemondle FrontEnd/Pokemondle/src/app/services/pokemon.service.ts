import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Pokemon } from '../pokemondle/pokemon';
import { Feedback } from '../feedback/feedback';


@Injectable({
  providedIn: 'root'
})
export class PokemonService {

  private baseUrl = `http://localhost:8080/api/pokemondle`;

  private gameIsStarted = false;
  private gameIsStartedSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.gameIsStarted);


  constructor(private httpClient: HttpClient) { }

  startOrStopGame(): Observable<boolean> {
    return this.gameIsStartedSubject.asObservable();
  }

  getAllPokemons(): Observable<Pokemon[]> {
    return this.httpClient.get<Pokemon[]>(this.baseUrl + '/all');
  }

  getById(id: number): Observable<Pokemon> {
    return this.httpClient.get<Pokemon>(this.baseUrl + '/id/'+id);
  }

  getByName(name: string): Observable<Pokemon> {
    return this.httpClient.get<Pokemon>(this.baseUrl + '/name/'+name);
  }

  getFeedback(name: string): Observable<Feedback>{
    return this.httpClient.get<Feedback>(this.baseUrl + '/feedback/'+name);
  }

  getRandom(): Observable<Pokemon>{
    return this.httpClient.get<Pokemon>(this.baseUrl + '/random');
  }
  readRandomPokemon():Observable<Pokemon>{
    return this.httpClient.get<Pokemon>(this.baseUrl + '/read');
  }


}
