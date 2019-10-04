import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyRepoTableComponent } from './property-repo-table.component';

describe('PropertyRepoTableComponent', () => {
  let component: PropertyRepoTableComponent;
  let fixture: ComponentFixture<PropertyRepoTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PropertyRepoTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertyRepoTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
