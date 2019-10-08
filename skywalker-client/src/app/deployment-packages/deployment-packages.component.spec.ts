import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeploymentPackagesComponent } from './deployment-packages.component';

describe('DeploymentPackagesComponent', () => {
  let component: DeploymentPackagesComponent;
  let fixture: ComponentFixture<DeploymentPackagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeploymentPackagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeploymentPackagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
