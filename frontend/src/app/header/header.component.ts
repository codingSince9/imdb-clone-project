import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalVariablesService } from '../shared/global-variables.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public ifExist: boolean | undefined;

  constructor(
    private router: Router,
    public globalVariablesService: GlobalVariablesService,
    private httpClient: HttpClient
  ) { }

  ngOnInit(): void {}

  navigate(url: string, params?: any) {
    this.router.navigateByUrl('/movies/all', {skipLocationChange: true}).then(() => {
      const commands = params ? [url, params] : [url];
      this.router.navigate(commands);
    })
  }

  isActive(id: number): boolean {
    switch(id) {
      case 1:
        return true
      case 2:
        return true
      case 3:
        return true
      default:
        return false;
    }
  }

  signOut() {
    localStorage.removeItem('google_auth');
    localStorage.removeItem('fb_auth')
    this.router.navigateByUrl('/login').then();
    this.globalVariablesService.loggedInFlag = false;
  }

}
