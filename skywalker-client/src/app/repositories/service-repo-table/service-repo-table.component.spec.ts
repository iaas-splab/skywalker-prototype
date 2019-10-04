import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRepoTableComponent } from './service-repo-table.component';

describe('ServiceRepoTableComponent', () => {
  let component: ServiceRepoTableComponent;
  let fixture: ComponentFixture<ServiceRepoTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceRepoTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceRepoTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
