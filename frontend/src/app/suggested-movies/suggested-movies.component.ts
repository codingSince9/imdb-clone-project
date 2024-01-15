import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalVariablesService } from '../shared/global-variables.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-suggested-movies',
  templateUrl: './suggested-movies.component.html',
  styleUrls: ['./suggested-movies.component.scss']
})
export class SuggestedMoviesComponent implements OnInit {

  public suggestedMovies: any;

  constructor(
    private router: Router,
    private httpClient: HttpClient,
    private globalVariablesService: GlobalVariablesService
  ) { }

  ngOnInit(): void {
    this.httpClient.get("http://localhost:8080/movies/getSuggestions/" + localStorage.getItem('userId')).subscribe((response) => {
      this.suggestedMovies = response;
    });
  }

  chooseFilms() {
    this.router.navigateByUrl("/suggestions");
  }

  sendDataToDetails() {
    this.globalVariablesService.setData("");
  }

}
