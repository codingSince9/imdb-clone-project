import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { faHeart as faNotFav } from '@fortawesome/free-regular-svg-icons';
import { GlobalVariablesService } from '../shared/global-variables.service';

@Component({
  selector: 'app-watch-list',
  templateUrl: './watch-list.component.html',
  styleUrls: ['./watch-list.component.scss']
})
export class WatchListComponent implements OnInit {
  public movies: any;
  faFav = faNotFav;
  public emptyResultOfSearch: boolean = false;

  constructor(
    private httpClient: HttpClient,
    private globalVariablesService: GlobalVariablesService
  ) { }

  ngOnInit(): void {
    this.getMovies();
  }

  getMovies() {
    this.httpClient.get("http://localhost:8080/movies/popular").subscribe((response: any) => {
      this.movies = response;
    })
  }

  getMoviesBySearch(search: string) {
    if (search == '') {
      this.movies = null;
      this.emptyResultOfSearch = false;
      this.getMovies();
    } else {
      this.httpClient.get("http://localhost:8080/movies/search/popular/" + search).subscribe((response: any) => {
        this.movies = response;
        if (this.movies.length == 0) this.emptyResultOfSearch = true;
        else this.emptyResultOfSearch = false;
      })
    }
  }

  sendDataToDetails() {
    this.globalVariablesService.setData("popular/");
  }

  onFavClick(): void {
  }
}
