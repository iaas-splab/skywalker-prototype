import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MappingListComponent } from './mapping-list.component';

describe('MappingListComponent', () => {
  let component: MappingListComponent;
  let fixture: ComponentFixture<MappingListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MappingListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MappingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
