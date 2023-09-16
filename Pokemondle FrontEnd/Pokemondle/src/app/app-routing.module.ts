import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PokemondleComponent } from './pokemondle/pokemondle.component';


const routes: Routes = [
  { path: 'pokemondle', component: PokemondleComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
