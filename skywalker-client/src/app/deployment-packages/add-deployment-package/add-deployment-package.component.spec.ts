import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDeploymentPackageComponent } from './add-deployment-package.component';

describe('AddDeploymentPackageComponent', () => {
  let component: AddDeploymentPackageComponent;
  let fixture: ComponentFixture<AddDeploymentPackageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDeploymentPackageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDeploymentPackageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
