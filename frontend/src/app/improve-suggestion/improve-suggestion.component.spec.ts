import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImproveSuggestionComponent } from './improve-suggestion.component';

describe('ImproveSuggestionComponent', () => {
  let component: ImproveSuggestionComponent;
  let fixture: ComponentFixture<ImproveSuggestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImproveSuggestionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImproveSuggestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
