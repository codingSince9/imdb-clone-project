import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacebookMovieComponent } from './facebook-movie.component';

describe('FacebookMovieComponent', () => {
  let component: FacebookMovieComponent;
  let fixture: ComponentFixture<FacebookMovieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FacebookMovieComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FacebookMovieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
