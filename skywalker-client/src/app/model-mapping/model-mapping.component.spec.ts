import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModelMappingComponent } from './model-mapping.component';

describe('ModelMappingComponent', () => {
  let component: ModelMappingComponent;
  let fixture: ComponentFixture<ModelMappingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModelMappingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModelMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
