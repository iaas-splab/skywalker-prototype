import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeploymentPackagesListComponent } from './deployment-packages-list.component';

describe('DeploymentPackagesListComponent', () => {
  let component: DeploymentPackagesListComponent;
  let fixture: ComponentFixture<DeploymentPackagesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeploymentPackagesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeploymentPackagesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
