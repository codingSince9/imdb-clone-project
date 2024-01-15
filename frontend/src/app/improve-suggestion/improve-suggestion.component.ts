import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { faHeart as faNotFav } from '@fortawesome/free-regular-svg-icons';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { Observable } from 'rxjs';
import { GlobalVariablesService } from '../shared/global-variables.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-improve-suggestion',
  templateUrl: './improve-suggestion.component.html',
  styleUrls: ['./improve-suggestion.component.scss']
})
export class ImproveSuggestionComponent implements OnInit {

  public movies: any;
  public likedMovies: any;
  public notLikedMovies: any;
  faFav = faNotFav;
  isFav: boolean = false;
  public userId: any;
  public emptyResultOfSearch: boolean = false;

  constructor(
    private router: Router,
    private httpClient: HttpClient
  ) { }

  ngOnInit(): void {
    this.getMovies();
  }

  getMovies() {
    this.userId = localStorage.getItem('userId');
    this.faFav = this.isFav ? faHeart : faNotFav;

    this.httpClient.get("http://localhost:8080/movies/all").subscribe((response: any) => {
      this.movies = response;
      this.likedMovies = this.movies.filter((movie: any) => movie.isLiked);
      this.notLikedMovies = this.movies.filter((movie: any) => !movie.isLiked);
    })
  }

  onFavClick(movie: any): void {
    movie.isLiked = !movie.isLiked;
    this.updateMovie(movie).subscribe((updatedMovie) => {
      if (updatedMovie.isLiked) {
        this.likedMovies.push(movie);
        this.notLikedMovies = this.notLikedMovies.filter((movie: any) => movie.movieId !== updatedMovie.movieId);
      } else {
        this.notLikedMovies.push(movie);
        this.likedMovies = this.likedMovies.filter((movie: any) => movie.movieId !== updatedMovie.movieId);
      }
    });
  }

  updateMovie(movie: any): Observable<any> {
    this.httpClient.post("http://localhost:8080/movies/updateSuggestions/" + this.userId, movie).subscribe();
    return this.httpClient.put<any>("http://localhost:8080/movies/update/" + movie.movieId, movie);
  }

  getSuggestedMovies() {
    this.router.navigateByUrl('/suggestedMovies');
  }

  getMoviesBySearch(search: string) {
    if (search == '') {
      this.movies = null;
      this.emptyResultOfSearch = false;
      this.getMovies();
    } else {
      this.httpClient.get("http://localhost:8080/movies/search/" + search).subscribe((response: any) => {
        this.notLikedMovies = response;
        if (this.movies.length == 0) this.emptyResultOfSearch = true;
        else this.emptyResultOfSearch = false;
      })
    }
  }

}
