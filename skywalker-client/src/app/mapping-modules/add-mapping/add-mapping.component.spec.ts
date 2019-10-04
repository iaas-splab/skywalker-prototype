import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMappingComponent } from './add-mapping.component';

describe('AddMappingComponent', () => {
  let component: AddMappingComponent;
  let fixture: ComponentFixture<AddMappingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddMappingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
