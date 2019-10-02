import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComparisonViewComponent } from './comparison-view.component';

describe('ComparisonViewComponent', () => {
  let component: ComparisonViewComponent;
  let fixture: ComponentFixture<ComparisonViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComparisonViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComparisonViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
