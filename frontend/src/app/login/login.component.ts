import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GlobalVariablesService } from '../shared/global-variables.service';
import { HttpClient } from '@angular/common/http';
import { FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: any;
  public userDetails: any;
  socialUser!: SocialUser;
  isLoggedin?: boolean;
  loginForm!: FormGroup;

  googleLoginOptions = {
    scope: 'profile email'
  }
  formBuilder: any;

  constructor(
    private router: Router,
    private authService: SocialAuthService,
    private googleAuthService: SocialAuthService,
    private globalVariablesService: GlobalVariablesService,
    private httpClient: HttpClient
  ) { }

  ngOnInit() {
    this.globalVariablesService.loggedInFlag = false;
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.globalVariablesService.loggedInFlag = (user != null);
    });
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
    this.googleAuthService.authState.subscribe((user: any) => {
      this.socialUser = user;
      this.isLoggedin = user != null;
    });
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then((data) => {
      localStorage.setItem('fb_auth', JSON.stringify(data));

      let storage = localStorage.getItem('fb_auth');

      if (storage) {
        this.userDetails = JSON.parse(storage);
        localStorage.setItem('authToken', this.userDetails.authToken);
        localStorage.setItem('userId', JSON.stringify(this.userDetails.id));
        this.httpClient.post("http://localhost:8080/users/saveUser", this.userDetails).subscribe((response) => {
        })
      } else {
        this.signOut();
      }
      this.router.navigateByUrl('/movies/all').then();
    });
  }

  signOut() {
    localStorage.removeItem('google_auth');
    localStorage.removeItem('fb_auth')
    this.router.navigateByUrl('/login').then();
    this.globalVariablesService.loggedInFlag = false;
  }

  signInWithGoogle() {
    this.googleAuthService.initState.subscribe((user: any) => {
      this.socialUser = user;
      this.isLoggedin = user != null;
    });
    this.googleAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(response => {
    });
  }

  logOut(): void {
    this.googleAuthService.signOut();
  }

}
