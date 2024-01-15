import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { GlobalVariablesService } from '../shared/global-variables.service';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.scss']
})
export class MovieDetailsComponent implements OnInit {

  public movie: any;
  public actorsMap = new Map<string, string>();
  public reviews: any;
  public movieVideos: any;
  public apiLoaded = false;
  public videoId: string | undefined;
  public similarMovies: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private httpClient: HttpClient,
    private globalVariablesService: GlobalVariablesService
  ) {
    route.params.subscribe(val => {
      this.ngOnInit();
    });
  }

  ngOnInit(): void {
    const movieId = this.route.snapshot.paramMap.get('movieId');

    if (!this.apiLoaded) {
      const tag = document.createElement('script');
      tag.src = 'https://www.youtube.com/iframe_api';
      document.body.appendChild(tag);
      this.apiLoaded = true;
    }

    let typeOfMovie;
    this.globalVariablesService.currentData.subscribe(data => {
      typeOfMovie = data;
    })

    this.httpClient.get("http://localhost:8080/movies/" + typeOfMovie + movieId).subscribe((response: any) => {
      this.movie = response;
      this.httpClient.get("http://localhost:8080/movies/reviews/" + movieId).subscribe((response) => {
        this.reviews = response
      });
      this.httpClient.get("http://localhost:8080/movies/movieVideos/" + movieId).subscribe((response) => {
        this.movieVideos = response
        this.videoId = this.movieVideos[0].split("?v=")[1];
      })
      this.httpClient.get("http://localhost:8080/movies/similarMovies/" + movieId).subscribe((response) => {
        this.similarMovies = response;
      })

      this.actorsMap = response.actors;
    })

  }

  sendDataToDetails() {
    this.globalVariablesService.setData("");
  }

}
