import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuggestedMoviesComponent } from './suggested-movies.component';

describe('SuggestedMoviesComponent', () => {
  let component: SuggestedMoviesComponent;
  let fixture: ComponentFixture<SuggestedMoviesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuggestedMoviesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuggestedMoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
