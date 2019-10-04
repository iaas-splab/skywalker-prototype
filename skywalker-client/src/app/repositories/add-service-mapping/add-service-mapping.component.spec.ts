import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddServiceMappingComponent } from './add-service-mapping.component';

describe('AddServiceMappingComponent', () => {
  let component: AddServiceMappingComponent;
  let fixture: ComponentFixture<AddServiceMappingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddServiceMappingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddServiceMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
