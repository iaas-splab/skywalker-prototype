import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppModelListComponent } from './app-model-list.component';

describe('AppModelListComponent', () => {
  let component: AppModelListComponent;
  let fixture: ComponentFixture<AppModelListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppModelListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppModelListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
