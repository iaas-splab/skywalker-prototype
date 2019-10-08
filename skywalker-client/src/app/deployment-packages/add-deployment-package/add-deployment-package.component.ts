import { Component, OnInit } from '@angular/core';
import {Template} from "../../deployment-model-templates/Template";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-add-deployment-package',
  templateUrl: './add-deployment-package.component.html',
  styleUrls: ['./add-deployment-package.component.css']
})
export class AddDeploymentPackageComponent implements OnInit {
  selectedFile: File = null;
  packageContent: string = null;

  constructor(private snackBarService: SnackbarService,) { }

  ngOnInit() {
  }

  onUpload() {
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      this.packageContent = (<string>fileReader.result);
      const template: Template = new Template(this.selectedFile.name, this.packageContent);
      // this.extractionService.upload(template).subscribe(response => {
      //   console.log(response);
      // });
    };
    this.snackBarService.openSnackBar('Add feature not working, yet.', 'close', 2000);
    fileReader.readAsText(this.selectedFile);
  }
}
