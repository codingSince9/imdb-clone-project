import { Component } from '@angular/core';
import { GlobalVariablesService } from './shared/global-variables.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'lab1DM';
  user: any;

  constructor(
    public globalVariablesService: GlobalVariablesService
  ) { }
}
