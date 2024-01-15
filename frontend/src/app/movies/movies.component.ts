import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalVariablesService } from '../shared/global-variables.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  public movies: any;

  constructor(
    private httpClient: HttpClient,
  ) { }

  ngOnInit(): void {
    this.httpClient.get("http://localhost:8080/movies/all").subscribe((response: any) => {
      this.movies = response;
    })
  }

}
