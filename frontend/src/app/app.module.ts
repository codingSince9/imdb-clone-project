import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { MoviesComponent } from './movies/movies.component';
import { MyprofileComponent } from './myprofile/myprofile.component';
import { MovieDetailsComponent } from './movie-details/movie-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SuggestedMoviesComponent } from './suggested-movies/suggested-movies.component';
import { ImproveSuggestionComponent } from './improve-suggestion/improve-suggestion.component';
import { YouTubePlayerModule } from '@angular/youtube-player';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { WatchListComponent } from './watch-list/watch-list.component';
import { MovieTileComponent } from './movie-tile/movie-tile.component';
import { GlobalVariablesService } from './shared/global-variables.service';
import { FacebookMovieComponent } from './facebook-movies/facebook-movie.component';
import { CinemaComponent } from './cinema/cinema.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MoviesComponent,
    MyprofileComponent,
    MovieTileComponent,
    WatchListComponent,
    MovieDetailsComponent,
    FacebookMovieComponent,
    SuggestedMoviesComponent,
    ImproveSuggestionComponent,
    CinemaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SocialLoginModule,
    HttpClientModule,
    BrowserAnimationsModule,
    YouTubePlayerModule,
    FontAwesomeModule,
    SocialLoginModule,
  ],
  providers: [
    GlobalVariablesService,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider('973344250618-jvv0skr2puh2jqfpqgdv39u4kgc7fjkv.apps.googleusercontent.comw')
          }
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('945860969726246')
          }
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
