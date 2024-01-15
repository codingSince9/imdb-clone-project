import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { faHeart as faNotFav } from '@fortawesome/free-regular-svg-icons';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { Observable } from 'rxjs';
import { GlobalVariablesService } from '../shared/global-variables.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-improve-suggestion',
  templateUrl: './facebook-movie.component.html',
  styleUrls: ['./facebook-movie.component.scss']
})
export class FacebookMovieComponent implements OnInit {

  public movies: any;
  public likedMovies: any;
  public notLikedMovies: any;
  faFav = faNotFav;
  isFav: boolean = false;
  public userId: any;
  public fb_auth: any;
  public authToken: any;

  constructor(
    private router: Router,
    private httpClient: HttpClient
  ) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId');
    this.userId = this.userId.replace(/['"]+/g, '');

    this.httpClient.get("http://localhost:8080/movies/getFacebookMovies/" + this.userId).subscribe((response: any) => {
      this.movies = response;
    })
  }

  onFavClick(movie: any): void {
    movie.isLiked = !movie.isLiked;
  }
}
