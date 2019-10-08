import { Component, OnInit } from '@angular/core';
import {Template} from "../../deployment-model-templates/Template";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-add-deployment-package',
  templateUrl: './add-deployment-package.component.html',
  styleUrls: ['./add-deployment-package.component.css']
})
export class AddDeploymentPackageComponent implements OnInit {
  selectedFile: File = null;
  packageContent: string = null;

  constructor(private snackBar: MatSnackBar) { }

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
    this.openSnackBar('Add feature not working, yet.', 'close', 2000);
    fileReader.readAsText(this.selectedFile);
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    });
  }

}
