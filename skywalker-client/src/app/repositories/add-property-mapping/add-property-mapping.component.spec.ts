import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPropertyMappingComponent } from './add-property-mapping.component';

describe('AddPropertyMappingComponent', () => {
  let component: AddPropertyMappingComponent;
  let fixture: ComponentFixture<AddPropertyMappingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPropertyMappingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPropertyMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
