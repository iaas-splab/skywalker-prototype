import { Component, OnInit } from '@angular/core';
import {DeploymentPackageService} from "../deployment-package.service";
import {DeploymentPackage} from "../DeploymentPackage";
import {Template} from "../../deployment-model-templates/Template";
import {TemplateService} from "../../deployment-model-templates/template.service";
import {Router} from "@angular/router";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-deployment-packages-list',
  templateUrl: './deployment-packages-list.component.html',
  styleUrls: ['./deployment-packages-list.component.css']
})
export class DeploymentPackagesListComponent implements OnInit {
  packages: Array<DeploymentPackage>;

  constructor(
    private deploymentPackageService: DeploymentPackageService,
    private extractionService: TemplateService,
    private snackBarService: SnackbarService,
    public router: Router
  ) { }

  ngOnInit() {
    this.deploymentPackageService.getAll().subscribe(data => {
      this.packages = data;
      console.log(data);
    });
  }

  log(item: any) {
    console.log(item);
    return item;
  }

  analyzeFunctions(deploymentPackage: DeploymentPackage) {
    if (deploymentPackage.analyzed == false) {
      this.deploymentPackageService.analyzeFunctions(deploymentPackage).subscribe(data => {
        this.packages = data;
        console.log(data);
        this.snackBarService.openSnackBar("Functions analyzed", 'close', 1000);
        this.ngOnInit();
      });
    } else {
      this.snackBarService.openSnackBar("This package has already been analyzed", 'close', 2000);
    }

  }

  addDeploymentModelToRepository(deploymentPackage: DeploymentPackage) {
    let model = new Template(deploymentPackage.id, deploymentPackage.deploymentModel);
    this.extractionService.upload(model).subscribe(response => {
      console.log(response);
      this.snackBarService.openSnackBar("Added to deployment models", 'close', 1000);
      this.router.navigate(['template-list']);
    });
  }

  resetAll() {
    this.deploymentPackageService.resetAll().subscribe(data => {
      console.log(data);
      this.ngOnInit();
      this.snackBarService.openSnackBar("Reset all deployment packages",
        'close',
        1000);
    });
  }
}
