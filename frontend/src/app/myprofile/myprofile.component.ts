import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalVariablesService } from '../shared/global-variables.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css']
})
export class MyprofileComponent implements OnInit {

  public userDetails: any;
  public picture: any;

  constructor(
    private router: Router,
    private httpClient: HttpClient,
    private globalVariableService: GlobalVariablesService
  ) { }

  ngOnInit(): void {
    let storage = localStorage.getItem('fb_auth');
    if(storage) {
      let storageInJson = JSON.parse(storage);
      this.picture = storageInJson.response.picture.data.url;
    }
    
    this.httpClient.get("http://localhost:8080/users/myprofile").subscribe((response: any) => {
      this.userDetails = response;
    })
  }

  signOut() {
    localStorage.removeItem('google_auth');
    localStorage.removeItem('fb_auth')
    this.router.navigateByUrl('/login').then();
  }

}
