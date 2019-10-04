import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAppModelComponent } from './add-app-model.component';

describe('AddAppModelComponent', () => {
  let component: AddAppModelComponent;
  let fixture: ComponentFixture<AddAppModelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAppModelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAppModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
