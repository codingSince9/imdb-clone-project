import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { faHeart as faNotFav } from '@fortawesome/free-regular-svg-icons';
import { GlobalVariablesService } from '../shared/global-variables.service';
@Component({
  selector: 'app-movie-tile',
  templateUrl: './movie-tile.component.html',
  styleUrls: ['./movie-tile.component.scss']
})
export class MovieTileComponent implements OnInit {
  @Input() imageUrl: string = '';
  @Input() title: string = '';
  @Input() rating: number = 0;
  @Input() id: number = 0;
  @Input() isFav: boolean = false;
  @Output() favClick = new EventEmitter();

  faFav = faNotFav;

  constructor(
    private globalVariablesService: GlobalVariablesService
  ) { }

  ngOnInit(): void {
    this.faFav = this.isFav ? faHeart : faNotFav;
  }

  ngOnChange(): void {
  }

  onFavClick(): void {
    this.favClick.emit();
  }

  sendDataToDetails() {
    this.globalVariablesService.setData("");
  }

}
