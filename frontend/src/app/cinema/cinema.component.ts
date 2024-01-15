import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.scss']
})
export class CinemaComponent implements OnInit {

  public cinemaResponse: any;
  public cinemaList: any = [];

  constructor(
    private router: Router,
    private httpClient: HttpClient
  ) { }

  ngOnInit(): void {
  }

  getCinemas(search: string) {
    this.httpClient.get("https://geocode.search.hereapi.com/v1/geocode?q=" + search + "&apiKey=I--njuwrdJFZEONLqMNrjvq-e2uEl72Xg0dhLeQKGmM").subscribe((response: any) => {
      let latitude = response.items[0].position.lat;
      let longitude = response.items[0].position.lng;

      this.httpClient.get("https://discover.search.hereapi.com/v1/discover?at=" + latitude + "," + longitude + "&limit=5&lang=en&q=Kino&apiKey=I--njuwrdJFZEONLqMNrjvq-e2uEl72Xg0dhLeQKGmM").subscribe((response: any) => {
        this.cinemaResponse = response
        this.cinemaList = this.cinemaResponse.items;

      });
    })
  }


}
