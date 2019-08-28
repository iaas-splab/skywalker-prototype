import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RetrievalComponent } from './retrieval.component';

describe('RetrievalComponent', () => {
  let component: RetrievalComponent;
  let fixture: ComponentFixture<RetrievalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RetrievalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RetrievalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
