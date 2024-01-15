import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImproveSuggestionComponent } from './improve-suggestion/improve-suggestion.component';
import { LoginComponent } from './login/login.component';
import { MovieDetailsComponent } from './movie-details/movie-details.component';
import { WatchListComponent } from './watch-list/watch-list.component';
import { MoviesComponent } from './movies/movies.component';
import { MyprofileComponent } from './myprofile/myprofile.component';
import { AuthGuard } from './shared/AuthGuard';
import { SuggestedMoviesComponent } from './suggested-movies/suggested-movies.component';
import { FacebookMovieComponent } from './facebook-movies/facebook-movie.component';
import { CinemaComponent } from './cinema/cinema.component';

const routes: Routes = [
  { path: '', redirectTo: 'movies/all', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'movies/all', component: WatchListComponent, canActivate: [AuthGuard] },
  { path: 'movies/facebook', component: FacebookMovieComponent, canActivate: [AuthGuard] },
  { path: 'myprofile', component: MyprofileComponent, canActivate: [AuthGuard] },
  { path: 'movies/:movieId', component: MovieDetailsComponent, canActivate: [AuthGuard] },
  { path: 'suggestedMovies', component: SuggestedMoviesComponent, canActivate: [AuthGuard] },
  { path: 'improveSuggestion', component: ImproveSuggestionComponent, canActivate: [AuthGuard] },
  { path: 'suggestions', component: ImproveSuggestionComponent, canActivate: [AuthGuard] },
  { path: 'cinema', component: CinemaComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload'
  }), RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
